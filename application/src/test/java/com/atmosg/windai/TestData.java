package com.atmosg.windai;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.ports.output.MetarManagementOutputPort;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.unit.PressureUnit;
import com.atmosg.windai.unit.SpeedUnit;
import com.atmosg.windai.unit.TemperatureUnit;
import com.atmosg.windai.vo.metar.Metar;
import com.atmosg.windai.vo.metar.field.Cloud;
import com.atmosg.windai.vo.metar.field.CloudGroup;
import com.atmosg.windai.vo.metar.field.Pressure;
import com.atmosg.windai.vo.metar.field.Temperature;
import com.atmosg.windai.vo.metar.field.TemperaturePair;
import com.atmosg.windai.vo.metar.field.Visibility;
import com.atmosg.windai.vo.metar.field.WeatherGroup;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.field.WindDirection;
import com.atmosg.windai.vo.metar.type.CloudCoverage;
import com.atmosg.windai.vo.metar.type.CloudType;
import com.atmosg.windai.vo.metar.type.MetarReportType;

public class TestData {

  @Mock
  protected MetarManagementOutputPort metarOutputPort;

  protected MetarRetrievalPeriod period;
  protected List<Metar> metarList = new ArrayList<>();

  public void loadData() {
    this.period = new MetarRetrievalPeriod("KSFO", ZonedDateTime.of(LocalDateTime.of(
        2025, 1, 1, 0, 0), ZoneId.of("UTC")), ZonedDateTime.of(LocalDateTime.of(
        2026, 1, 1, 0, 0), ZoneId.of("UTC")));

    String rawText = "KSFO 030953Z 29008KT 1SM FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122=";

    ZonedDateTime etime = ZonedDateTime.of(LocalDateTime.of(
        2025, 5, 3, 9, 53), ZoneId.of("UTC"));

    Wind ew = Wind.builder()
        .direction(WindDirection.fixed(290))
        .speed(8)
        .unit(SpeedUnit.KT)
        .build();

    Visibility ev = Visibility.builder()
        .value(10)
        .unit(LengthUnit.MILE)
        .build();

    TemperaturePair et = TemperaturePair.builder()
        .temperature(Temperature.builder().value(18).unit(TemperatureUnit.CELSIUS).build())
        .dewPoint(Temperature.builder().value(12).unit(TemperatureUnit.CELSIUS).build())
        .build();

    Pressure ep = Pressure.builder()
        .value(29.95)
        .unit(PressureUnit.INHG)
        .build();

    CloudGroup ecg = CloudGroup.builder()
        .clouds(List.of(
            Cloud.builder().coverage(CloudCoverage.FEW).altitude(2500).type(CloudType.NONE).build(),
            Cloud.builder().coverage(CloudCoverage.SCT).altitude(25000).type(CloudType.NONE).build()))
        .build();

    WeatherGroup ewg = WeatherGroup.builder()
        .weatherList(List.of())
        .build();

    Metar metar = Metar.builder()
        .rawText(rawText)
        .stationIcao("KSFO")
        .reportType(MetarReportType.METAR)
        .observationTime(etime)
        .wind(ew)
        .visibility(ev)
        .temperaturePair(et)
        .altimeter(ep)
        .weatherGroup(ewg)
        .cloudGroup(ecg)
        .remarks("AO2 SLP142 T01780122=")
        .build();

    metarList.add(metar);
  }

}
