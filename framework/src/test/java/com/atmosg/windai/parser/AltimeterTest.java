package com.atmosg.windai.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.atmosg.windai.parser.metar.generic.entry.AltimeterRegexParser;
import com.atmosg.windai.parser.shared.ReportParser;
import com.atmosg.windai.unit.PressureUnit;
import com.atmosg.windai.vo.metar.field.Pressure;

public class AltimeterTest {

  ReportParser<Pressure> parser = new AltimeterRegexParser();

  @Test
  public void 헥토파스칼_단위의_기압정보를_정상적으로_파싱한다() {
    String rawText = "METAR RKSI 030300Z 29008KT 260V320 9999 -RA BKN006 OVC045 11/10 Q1009 NOSIG=";
    Pressure actual = parser.parse(rawText);

    Pressure expected = Pressure.builder()
        .value(1009)
        .unit(PressureUnit.HPA)
        .build();

    assertEquals(expected, actual);
  }

  @Test
  public void 수은_단위의_기압정보를_헥토파스칼_단위로_정상_파싱한다() {
    String rawText = "KDEN 281539Z 33012G20KT 10SM FEW030 10/M02 A3002 RMK AO2 SLP200 T01000022=";
    Pressure actual = parser.parse(rawText);

    Pressure expected = Pressure.builder()
        .value(30.02)
        .unit(PressureUnit.INHG)
        .build();

    assertEquals(expected, actual);
  }

}
