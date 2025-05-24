package com.atmosg.windai;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.LongStream;

import org.mockito.Mock;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.dto.windrose.DirectionBin;
import com.atmosg.windai.dto.windrose.SpeedBin;
import com.atmosg.windai.ports.output.MetarManagementOutputPort;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.unit.PressureUnit;
import com.atmosg.windai.unit.SpeedUnit;
import com.atmosg.windai.unit.TemperatureUnit;
import com.atmosg.windai.usecases.PredicateStatisticUseCase;
import com.atmosg.windai.usecases.ThresholdStatisticUseCase;
import com.atmosg.windai.usecases.WindRoseUsecase;
import com.atmosg.windai.vo.metar.Metar;
import com.atmosg.windai.vo.metar.field.Cloud;
import com.atmosg.windai.vo.metar.field.CloudGroup;
import com.atmosg.windai.vo.metar.field.Pressure;
import com.atmosg.windai.vo.metar.field.Temperature;
import com.atmosg.windai.vo.metar.field.TemperaturePair;
import com.atmosg.windai.vo.metar.field.Visibility;
import com.atmosg.windai.vo.metar.field.Weather;
import com.atmosg.windai.vo.metar.field.WeatherGroup;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.field.WindDirection;
import com.atmosg.windai.vo.metar.type.CloudCoverage;
import com.atmosg.windai.vo.metar.type.CloudType;
import com.atmosg.windai.vo.metar.type.MetarReportType;
import com.atmosg.windai.vo.metar.type.WeatherInensity;
import com.atmosg.windai.vo.metar.type.WeatherPhenomenon;

public class TestData {

  @Mock
  protected MetarManagementOutputPort metarOutputPort;

  protected MetarRetrievalPeriod period;
  protected List<Metar> metarDatabase = generate("RKSI", 2015, 2024, 100);
  protected List<Metar> metarQuery;

  protected WindRoseUsecase windRoseUsecase;
  protected PredicateStatisticUseCase predicateStatisticUseCase;
  protected ThresholdStatisticUseCase thresholdStatisticUseCase;

  protected List<SpeedBin> speedBins;
  protected List<DirectionBin> directionBins;

  public void loadData(int year, int plusYear) {
    ZonedDateTime startUtc = ZonedDateTime.of(
      LocalDate.of(year, 1, 1),
      LocalTime.MIDNIGHT,
      ZoneOffset.UTC);

    ZonedDateTime endUtc = startUtc.plusYears(plusYear);
    this.period = new MetarRetrievalPeriod("RKSI", startUtc, endUtc);

    metarQuery = metarDatabase.stream()
      .filter(m -> {
        ZonedDateTime obs = m.getObservationTime();
        return !obs.isBefore(startUtc) && !obs.isAfter(endUtc);
      })
      .toList();

    speedBins = List.of(
      new SpeedBin(0, 1, SpeedUnit.KT, "0–1 KT"),
      new SpeedBin(1, 5, SpeedUnit.KT,"1–5 KT"),
      new SpeedBin(5, 10,SpeedUnit.KT, "5–10 KT"),
      new SpeedBin(10, 15,SpeedUnit.KT, "10–15 KT"),
      new SpeedBin(15, 20,SpeedUnit.KT, "15–20 KT"),
      new SpeedBin(20, 25,SpeedUnit.KT, "20–25 KT"),
      new SpeedBin(25, Integer.MAX_VALUE, SpeedUnit.KT, "≥ 25 KT")
    );

    directionBins = DirectionBin.of16Directions();
  }

  private static List<Metar> generate(String station, int stYear, int edYear ,long seed) {
    ZonedDateTime startUtc = ZonedDateTime.of(
      LocalDate.of(stYear, 1, 1),
      LocalTime.MIDNIGHT,
      ZoneOffset.UTC);

    ZonedDateTime endUtc = startUtc.plusYears(edYear-stYear);        // (exclusive)
    long hours = ChronoUnit.HOURS.between(startUtc, endUtc);

    Random rnd = new Random(seed);

    DoubleUnaryOperator windKt = h -> {
      double dayCycle = 5 * Math.sin(2 * Math.PI * (h % 24) / 24.0);           // -5~+5
      double season   = 5 * Math.cos(2 * Math.PI * h / (24 * 365.25));          // -5~+5
      return 10 + dayCycle + season + rnd.nextGaussian();                       // 평균 10
    };

    // ② 풍향(°): 시계방향 매일 360/365°씩 이동 + ±20° 난조
    DoubleUnaryOperator windDeg = h -> (360.0 * h / (24 * 365.0) + rnd.nextGaussian() * 20 + 360) % 360;

    // ③ 기온(°C): 연평균 15, 계절 ±10, 일교차 ±5
    DoubleUnaryOperator tempC = h -> {
      double season = 10 * Math.cos(2 * Math.PI * h / (24 * 365.25));          // -10~+10
      double diurnal = 5 * Math.sin(2 * Math.PI * (h % 24) / 24.0 - Math.PI/2);// -5~+5 (15시 최고)
      return 15 + season + diurnal + rnd.nextGaussian();
    };

    // ④ 시정(SM): 기본 10, 비·안개 조건에서 0.5~5
    DoubleUnaryOperator visSm = h -> 10;   // placeholder, 현상에 맞춰 아래서 조정

    /* ==== 공통 Cloud & Pressure ==== */
    CloudGroup baseCloud = CloudGroup.of(List.of(
        Cloud.of(CloudCoverage.SCT, 3000, CloudType.NONE),
        Cloud.of(CloudCoverage.BKN, 15000, CloudType.NONE)));
    Pressure alt = Pressure.of(29.92, PressureUnit.INHG);       // 고정

    DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("ddHHmm");
    
    return LongStream.range(0, hours).mapToObj(i -> {
      ZonedDateTime obs = startUtc.plusHours(i);

      /* --- 풍향·풍속 --- */
      int speed = (int) Math.max(0, Math.round(windKt.applyAsDouble(i)));
      int gust  = (speed >= 15) ? speed + 10 + rnd.nextInt(6) : 0;
      Wind wind = Wind.builder()
          .direction(WindDirection.fixed((int) Math.round(windDeg.applyAsDouble(i))))
          .speed(speed)
          .gusts(gust)
          .unit(SpeedUnit.KT)
          .build();

      /* --- 기온 & 이슬점 --- */
      double tC = tempC.applyAsDouble(i);
      double tdC = tC - (1 + rnd.nextDouble() * 3);                  // 항상 약간 낮음
      TemperaturePair tp = TemperaturePair.of(
          Temperature.of((int) Math.round(tC), TemperatureUnit.CELSIUS),
          Temperature.of((int) Math.round(tdC), TemperatureUnit.CELSIUS));

      /* --- 현상 & 시정 결정 --- */
      List<WeatherPhenomenon> phenomena = new ArrayList<>();
      double vis = 10;   // 기본 10 SM
      if (speed < 3 && rnd.nextDouble() < 0.05) {                    // 5% 확률 짙은 안개 새벽
        phenomena.add(WeatherPhenomenon.FG);
        vis = 0.5 + rnd.nextDouble();                                // 0.5~1.5 SM
      } else if (seasonalRain(obs) && rnd.nextDouble() < 0.2) {      // 겨울철 20% 비·시정↓
        phenomena.add(rnd.nextBoolean() ? WeatherPhenomenon.RA
                                         : WeatherPhenomenon.RA);
        vis = 3 + rnd.nextDouble() * 2;                              // 3~5 SM
      }
      Visibility visObj = Visibility.of(vis, LengthUnit.MILE);
      WeatherGroup wx = WeatherGroup.of(phenomena.isEmpty()
                                        ? List.of()
                                        : List.of(Weather.of(WeatherInensity.MODERATE, null, phenomena)));

      /* --- Cloud 가변성: 비·안개면 하저층 OVC --- */
      CloudGroup cloud = wx.size() == 0 ? baseCloud
          : CloudGroup.of(List.of(
              Cloud.of(CloudCoverage.OVC, wx.containsPhenomena(List.of(WeatherPhenomenon.FG)) ? 300 : 1000,
                       CloudType.NONE)));

      /* --- RAW TEXT (간략) --- */
      String raw = "%s %sZ %03d%02dG%02dKT %sSM %s".formatted(
          station,
          obs.format(dtFmt),
          ((int) wind.getDirection().getDegreeOrThrow()/10)*10,
          speed,
          gust,
          vis % 1 == 0 ? String.valueOf((int) vis) : String.format("%.1f", vis),
          cloud.getClouds().get(0).getCoverage()
      );

      return Metar.builder()
          .rawText(raw + " (TEST)")             // 식별용
          .stationIcao(station)
          .reportType(MetarReportType.METAR)
          .observationTime(obs)
          .wind(wind)
          .visibility(visObj)
          .temperaturePair(tp)
          .altimeter(alt)
          .cloudGroup(cloud)
          .weatherGroup(wx)
          .remarks("AUTO TEST DATA")
          .build();
    }).toList();
  }

  private static boolean seasonalRain(ZonedDateTime t) {
    int m = t.getMonthValue();
    return m == 11 || m == 12 || m <= 3;
  }

}
