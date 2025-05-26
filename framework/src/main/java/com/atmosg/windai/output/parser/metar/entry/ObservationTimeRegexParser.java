package org.windai.domain.policy.parser.metar.entry;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.regex.ObservationTimeRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.ReportFieldType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObservationTimeRegexParser extends ReportRegexParser<ZonedDateTime> {

  private static final ReportFieldType FIELD_TYPE= ReportFieldType.OBSERVATION_TIME;
  private static final String TIME_REGEX = ObservationTimeRegexes.fullPattern();
  
  private final YearMonth yearMonth;

  @Override
  public ZonedDateTime parse(String rawText) {
    Matcher matcher = getMatcher(rawText, TIME_REGEX);
    
    if (!check(matcher)) {
      throw new GenericPolicyException("Observation time not found in report:  " + rawText);
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

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }

}
