package com.atmosg.windai.output.parser.metar.entry;

import java.util.regex.Matcher;

import com.atmosg.windai.output.parser.metar.regex.StationIcaoRegexes;
import com.atmosg.windai.output.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.type.MetarField;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StationIcaoRegexParser extends ReportRegexParser<String> {

  private final MetarField fieldType = MetarField.STATION_ICAO;
  private final String STATION_REGEX = StationIcaoRegexes.fullPattern();

  @Override
  public String parse(String rawText) {
    Matcher matcher = getMatcher(rawText, STATION_REGEX);
    
    if (!check(matcher)) {
      throw new IllegalArgumentException("Station not found in report: " + rawText);
    }

    String station = matcher.group(StationIcaoRegexes.STATION.getGroupName());

    return station;
  }

  @Override
  public MetarField getFieldType() {
    return fieldType;
  }
  
}
