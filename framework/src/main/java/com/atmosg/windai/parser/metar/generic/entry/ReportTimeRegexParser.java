package com.atmosg.windai.parser.metar.generic.entry;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;

import com.atmosg.windai.parser.metar.generic.regex.ObservationTimeRegexes;
import com.atmosg.windai.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.type.MetarField;
import com.atmosg.windai.vo.metar.type.MetarReportType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ReportTimeRegexParser extends ReportRegexParser<ZonedDateTime> {

  private final MetarField FIELD_TYPE = MetarField.REPORT_TIME;

  @Getter
  private final YearMonth yearMonth;
  private final MetarReportTypeRegexParser reportTypeParser;
  private final ObservationTimeRegexParser obsTimeParser;

  public ReportTimeRegexParser(YearMonth yearMonth) {
    this.yearMonth = yearMonth;
    this.reportTypeParser = new MetarReportTypeRegexParser();
    this.obsTimeParser = new ObservationTimeRegexParser(yearMonth);
  }

  @Override
  public ZonedDateTime parse(String rawText) {
    MetarReportType reportType = reportTypeParser.parse(rawText);
    ZonedDateTime obsTime = obsTimeParser.parse(rawText);

    if (reportType == MetarReportType.METAR) {
      return roundTo(obsTime);
    }
    
    return obsTime;
  }

  private ZonedDateTime roundTo(ZonedDateTime obsTime) {
    ZonedDateTime utc = obsTime.withZoneSameInstant(ZoneId.of("UTC"));
    ZonedDateTime baseHour = utc.truncatedTo(ChronoUnit.HOURS);

    int minute = utc.getMinute();
    int second = utc.getSecond();
    int totalSeconds = minute * 60 + second;

    if (totalSeconds < 15 * 60) return baseHour;
    else if (totalSeconds < 45 * 60) return baseHour.plusMinutes(30);
    else return baseHour.plusHours(1);
  }

  public ReportTimeRegexParser withYearMonth(YearMonth newYm) {
    return new ReportTimeRegexParser(newYm);
  }

  @Override
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }

}
