package com.atmosg.windai.parser;

import static org.junit.Assert.assertEquals;

import java.time.YearMonth;

import org.junit.Test;

import com.atmosg.windai.TestMetarData;
import com.atmosg.windai.parser.metar.generic.factory.MetarParser;
import com.atmosg.windai.vo.metar.Metar;

public class MetarParserTest extends TestMetarData {
  
  private MetarParser parser = new MetarParser(YearMonth.of(2025, 5));

  @Test
  public void 메타_파싱에_성공해야한다() {
    Metar expected = testData.get(0);
    String rawText = expected.getRawText();

    Metar acutal = parser.parse(rawText);

    assertEquals(expected, acutal);  
  }

}
