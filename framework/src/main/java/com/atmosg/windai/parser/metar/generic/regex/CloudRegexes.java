package com.atmosg.windai.parser.metar.generic.regex;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.atmosg.windai.vo.metar.type.CloudCoverage;
import com.atmosg.windai.vo.metar.type.CloudType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CloudRegexes {

  COVERAGE("coverage", getCoverageRegex()),
  TYPE("type", getTypeRegex()),
  ALTITUDE("altitude", getAltitudeRegex());

  private final String groupName;
  private final String regex;

  public String getRegex() {
    return regex;
  }

  public String getGroupName() {
    return groupName;
  }

  public static String fullPattern() {
    return String.format("(%s)(%s)?(%s)?",
        getCoverageRegex(),
        getAltitudeRegex(),
        getTypeRegex());
  }

  private static String getCoverageRegex() {
    return Arrays.stream(CloudCoverage.values())
        .map(Enum::name)
        .collect(Collectors.joining("|", "(?<coverage>", ")"));
  }

  private static String getTypeRegex() {
    return Arrays.stream(CloudType.values())
        .map(Enum::name)
        .collect(Collectors.joining("|", "(?<type>", ")"));
  }

  private static String getAltitudeRegex() {
    return "(?<altitude>\\d{2,3})";
  }

}
