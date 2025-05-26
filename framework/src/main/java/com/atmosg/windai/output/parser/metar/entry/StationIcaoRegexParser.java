package org.windai.domain.policy.parser.metar.entry;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.regex.StationIcaoRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.ReportFieldType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StationIcaoRegexParser extends ReportRegexParser<String> {

  private final ReportFieldType fieldType = ReportFieldType.STATION_ICAO;
  private final String STATION_REGEX = StationIcaoRegexes.fullPattern();

  @Override
  public String parse(String rawText) {
    Matcher matcher = getMatcher(rawText, STATION_REGEX);
    
    if (!check(matcher)) {
      throw new GenericPolicyException("Station not found in report: " + rawText);
    }

    String station = matcher.group(StationIcaoRegexes.STATION.getGroupName());

    return station;
  }

  @Override
  public ReportFieldType getFieldType() {
    return fieldType;
  }
  
}
