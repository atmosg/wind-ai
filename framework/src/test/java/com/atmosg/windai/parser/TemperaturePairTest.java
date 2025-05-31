package com.atmosg.windai.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import com.atmosg.windai.parser.metar.generic.entry.TemperaturePairRegexParser;
import com.atmosg.windai.parser.shared.ReportParser;
import com.atmosg.windai.unit.TemperatureUnit;
import com.atmosg.windai.vo.metar.field.Temperature;
import com.atmosg.windai.vo.metar.field.TemperaturePair;

public class TemperaturePairTest {

  ReportParser<TemperaturePair> parser = new TemperaturePairRegexParser();

  @Test
  public void 양의_온도쌍을_가진_메타_파싱에_성공해야한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 18/12 A2995 RMK AO2 SLP142 T01780122=";

    TemperaturePair pair = parser.parse(rawText);

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(18)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(12)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  public void 양의_온도와_음의_이슬점을_갖는_메타_파싱에_성공해야한다() {
    String rawText = "KDEN 281539Z 33012G20KT 10SM FEW030 10/M02 A3002 RMK AO2 SLP200 T01000022=";

    TemperaturePair pair = parser.parse(rawText);

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(10)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-2)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  public void 음의_온도와_양의_이슬점을_갖는_메타_파싱에_성공해야한다() {
    String rawText = "KJFK 031752Z 12009KT 3SM -RA BR OVC011 M17/16 A3012 RMK AO2 SLP197 P0004 T01670156=";

    TemperaturePair pair = parser.parse(rawText);

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-17)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(16)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  public void 음의_온도쌍을_갖는_메타_파싱에_성공해야한다() {
    String rawText = "KJFK 051830Z 18012G23KT 10SM FEW035 SCT120 M22/M14 A3002 RMK AO2 SLP157 T02220139=";

    TemperaturePair pair = parser.parse(rawText);

    Temperature expectedT = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-22)
        .build();

    Temperature expectedDewt = Temperature.builder()
        .unit(TemperatureUnit.CELSIUS)
        .value(-14)
        .build();

    TemperaturePair expected = TemperaturePair.builder()
        .temperature(expectedT)
        .dewPoint(expectedDewt)
        .build();

    assertEquals(expected, pair);
  }

  @Test
  public void 온도정보가_누락되면_예외가_발생한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 /12 A2995 RMK AO2 SLP142 T01780122=";

    assertThrows(IllegalArgumentException.class, () -> parser.parse(rawText));
  }

  @Test
  public void 노점정보가_누락되면_예외가_발생한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 16/ A2995 RMK AO2 SLP142 T01780122=";

    assertThrows(IllegalArgumentException.class, () -> parser.parse(rawText));
  }

  @Test
  public void 온도쌍_정보가_누락되면_예외가_발생한다() {
    String rawText = "KSFO 030953Z 29008KT 10SM FEW025 SCT250 A2995 RMK AO2 SLP142 T01780122=";

    assertThrows(IllegalArgumentException.class, () -> parser.parse(rawText));
  }

}
