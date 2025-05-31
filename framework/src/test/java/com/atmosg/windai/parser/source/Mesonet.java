package com.atmosg.windai.parser.source;

import static org.junit.Assert.assertEquals;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import com.atmosg.windai.parser.metar.generic.factory.MetarParser;
import com.atmosg.windai.parser.metar.mesonet.IssuedTimeRegexParser;
import com.atmosg.windai.parser.shared.ReportParser;
import com.atmosg.windai.vo.metar.Metar;

public class Mesonet {

  ReportParser<ZonedDateTime> parser = new IssuedTimeRegexParser(); 
  
  @Test
  public void 연월_파싱() {
    String rawText = "RKSI,2019-01-01 00:00,RKSI 010000Z 07001KT CAVOK M06/M11 Q1034 NOSIG";
    
    ZonedDateTime expected = ZonedDateTime.of(
      2019, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
    ZonedDateTime actual = parser.parse(rawText);

    assertEquals(expected, actual);
  }

}


