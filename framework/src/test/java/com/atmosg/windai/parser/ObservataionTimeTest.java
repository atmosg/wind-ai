package com.atmosg.windai.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import com.atmosg.windai.parser.metar.generic.entry.ObservationTimeRegexParser;
import com.atmosg.windai.parser.shared.ReportParser;

public class ObservataionTimeTest {

  ReportParser<ZonedDateTime> parser = new ObservationTimeRegexParser(YearMonth.of(2025, 5));

  @Test
  public void 관측시간_파싱_성공() {
    String metar = "RKSI 010300Z 17008KT 4000 -RA SCT006 BKN025 OVC070 13/13 Q1007 NOSIG";

    ZonedDateTime result = new ObservationTimeRegexParser(YearMonth.of(2025, 5))
        .parse(metar);

    assertEquals(result, ZonedDateTime.of(
        LocalDateTime.of(2025, 5, 1, 3, 0),
        ZoneId.of("UTC")));
  }

}
