package com.atmosg.windai.output.parser.metar.regex;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StationIcaoRegexes {
  
  STATION("station", getStationRegex());

  private final String groupName;
  private final String regex;

  public String getGroupName() {
    return groupName;
  }

  public String getRegex() {
    return regex;
  }

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)\\s(%s)", 
      getStationRegex(),
      ObservationTimeRegexes.fullPattern()
    );
  }

  private static String getStationRegex() {
    return "(?<station>[A-Z]{4})";
  }

}
