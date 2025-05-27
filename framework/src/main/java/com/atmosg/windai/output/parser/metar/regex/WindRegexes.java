package com.atmosg.windai.output.parser.metar.regex;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.atmosg.windai.unit.SpeedUnit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WindRegexes {
  DIRECTION("direction", getDirectionRegex()),
  SPEED("speed", getSpeedRegex()),
  GUSTS("gusts", getGustsRegex()),
  UNIT("unit", getUnitRegex());

  private final String groupName;
  private final String regex;

  public String getGroupName() {
    return groupName;
  }

  public String getRegex() {
    return regex;
  }

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)(%s)(%s)?(%s)(?=(?:\\s|$))", 
      getDirectionRegex(),
      getSpeedRegex(),
      getGustsRegex(),
      getUnitRegex()
    );
  }

  private static String getDirectionRegex() {
    return "(?<direction>\\d{3}|VRB)";
  }

  private static String getSpeedRegex() {
    return "(?<speed>\\d{2})";
  }

  private static String getGustsRegex() {
    return "G(?<gusts>\\d{2})";
  }

  private static String getUnitRegex() {
    return Arrays.stream(SpeedUnit.values())
      .map(Enum::name)
      .collect(Collectors.joining("|", "(?<unit>", ")"));
  }

}
