package com.atmosg.windai.output.parser.metar.entry;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;

import com.atmosg.windai.output.parser.metar.regex.ObservationTimeRegexes;
import com.atmosg.windai.output.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.type.MetarField;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObservationTimeRegexParser extends ReportRegexParser<ZonedDateTime> {

  private static final MetarField FIELD_TYPE= MetarField.OBSERVATION_TIME;
  private static final String TIME_REGEX = ObservationTimeRegexes.fullPattern();
  
  @Getter
  private final YearMonth yearMonth;

  @Override
  public ZonedDateTime parse(String rawText) {
    Matcher matcher = getMatcher(rawText, TIME_REGEX);
    
    if (!check(matcher)) {
      throw new IllegalArgumentException("Observation time not found in report:  " + rawText);
    }

    int day = Integer.parseInt(matcher.group(ObservationTimeRegexes.DAY.getGroupName()));
    int hour = Integer.parseInt(matcher.group(ObservationTimeRegexes.HOUR.getGroupName()));
    int minute = Integer.parseInt(matcher.group(ObservationTimeRegexes.MINUTE.getGroupName()));

    LocalDateTime local = LocalDateTime.of(
      yearMonth.getYear(),
      yearMonth.getMonthValue(),
      day,
      hour,
      minute
    );

    return ZonedDateTime.of(local, ZoneId.of("UTC"));    
  }

  public ObservationTimeRegexParser withYearMonth(YearMonth yearMonth) {
    return new ObservationTimeRegexParser(yearMonth);
  }

  @Override
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }

}
