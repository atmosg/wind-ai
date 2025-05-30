package com.atmosg.windai;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.atmosg.windai.unit.LengthUnit.*;
import static com.atmosg.windai.unit.SpeedUnit.*;
import static com.atmosg.windai.unit.PressureUnit.*;
import static com.atmosg.windai.unit.TemperatureUnit.*;

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

import static com.atmosg.windai.vo.metar.type.MetarReportType.*;
import static com.atmosg.windai.vo.metar.field.CloudGroup.*;
import static com.atmosg.windai.vo.metar.field.Pressure.*;
import static com.atmosg.windai.vo.metar.field.TemperaturePair.*;
import static com.atmosg.windai.vo.metar.field.Visibility.*;
import static com.atmosg.windai.vo.metar.field.WeatherGroup.*;
import static com.atmosg.windai.vo.metar.field.Wind.*;

public class TestMetarData {

  protected List<Metar> testData = List.of(
    Metar.builder()
      .rawText("KSFO 030953Z 29008KT 10SM FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122=")
      .stationIcao("KSFO")
      .reportType(METAR)
      .observationTime(generateTime("030953"))
      .wind(generateWind(290,8,0))
      .visibility(Visibility.of(10, MILE))
      .temperaturePair(TemperaturePair.of(
        Temperature.of(18, CELSIUS),
        Temperature.of(12, CELSIUS)
      ))
      .altimeter(Pressure.of(29.95, INHG))
      .weatherGroup(WeatherGroup.of(List.of()))
      .cloudGroup(CloudGroup.of(
        List.of(
          Cloud.of(CloudCoverage.FEW, 2500, CloudType.NONE),
          Cloud.of(CloudCoverage.SCT, 25000, CloudType.NONE)
        )
      ))
      .remarks("AO2 SLP142 T01780122=")
      .build()
  );

  private static ZonedDateTime generateTime(String ddhhmm) {
    return ZonedDateTime.of(
      2025,
       5,
       Integer.valueOf(ddhhmm.substring(0, 2)),
       Integer.valueOf(ddhhmm.substring(2, 4)),
       Integer.valueOf(ddhhmm.substring(4, 6)),
       0,
       0,
       ZoneId.of("UTC"));
  }

  private static Wind generateWind(double dir, double speed, double gust) {
    return Wind.of(WindDirection.fixed(dir), speed, gust, KT);
  }
}


  // "KSFO 030953Z 29008KT 10SM FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122=",
  // "KDEN 281539Z 33012G20KT 10SM FEW030 10/M02 A3002 RMK AO2 SLP200 T01000022=",
  // "KJFK 031752Z 12009KT 3SM -RA BR OVC011 M17/16 A3012 RMK AO2 SLP197 P0004 T01670156=",
  // "KJFK 051830Z 18012G23KT 10SM FEW035 SCT120 M22/M14 A3002 RMK AO2 SLP157 T02220139=",
  // "KORD 230030Z 18007KT 5SM TSRA SCT025CB BKN050 28/18 A2994 RMK AO2 T02830183=",
  // "KJFK 071530Z 09010KT 7SM OVC015 08/06 A2992 RMK AO2",
  // "KJFK 060023Z 22005KT 15SM FEW040 BKN200 22/15 A3002 RMK AO2 SLP161 T02220150=",
  // "METAR RKSI 030300Z 29008KT 260V320 9999 -RA BKN006 OVC045 11/10 Q1009 NOSIG=",
  // "METAR RKSS 030300Z 28007KT 250V310 9000 FEW009 SCT025 OVC070 11/11 Q1008 NOSIG=",
  // "METAR RKPC 030300Z 22021G39KT 9999 SCT025 BKN160 22/16 Q1006 WS R07 R25 NOSIG=",
  // "METAR RKJB 030300Z 35009KT 9999 -RA BKN025 OVC080 12/11 Q1006 NOSIG=",
  // "METAR RKPU 030300Z 01010KT 8000 -RA BKN030 OVC080 12/09 Q1007 NOSIG=",
  // "METAR RKNY 030300Z 26007KT 220V280 9999 BKN050 BKN080 12/07 Q1005 NOSIG=",
  // "METAR RKJY 030300Z 30004KT 9999 -RA BKN035 OVC080 14/10 Q1006 NOSIG=",
  // "METAR RKPK 030300Z 21008KT 9999 -RA SCT015 BKN070 OVC100 16/12 Q1007 RMK CIG070 SLP076 8/72/ 9/35/=",
  // "METAR RKTU 030300Z 04002KT 1200 RASN BR BKN010 OVC030 08/08 Q1008 RMK CIG010 SLP085 8/7// 9/8//=",
  // "METAR RKTU 030300Z 04002KT 4800 -RA BR BKN010 OVC030 08/08 Q1008 RMK CIG010 SLP085 8/7// 9/8//=",
  // "METAR RKTN 030300Z 13001KT 6000 RA FEW010 BKN030 OVC060 10/09 Q1008 RMK CIG030 SLP082 8/7// 9/8//=",
  // "METAR RKJJ 030300Z 02009KT 6000 -RA OVC030 11/10 Q1006 RMK CIG030 SLP066 8/7// 9/8//=",
  // "METAR RKTH 030300Z 06005KT 6000 RA FEW010 SCT020 OVC070 11/08 Q1008 RMK CIG070 SLP082 8/62/ 9/44/",
  // "METAR RKNW 030300Z 23003KT 8000 -RA SCT015 OVC035 10/08 Q1008 RMK CIG035 SLP087 8/5// 9/8//=",
  // "SPECI RKJK 030213Z 04008KT 6SM BR FEW007 OVC038 10/09 A2975 RMK AO2A RAE0155RAB0159E13 SLPNO $=",
  // "METAR RKTU 030300Z 04002KT 1200 -TSSNRA -PLSN BR BKN010 OVC030 08/08 Q1008 RMK CIG010 SLP085 8/7// 9/8//=",
  // "KSFO 030953Z 29008KT P6SM FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122="