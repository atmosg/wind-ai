package com.atmosg.windai.output.parser.metar.regex;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TemperaturePairRegexes {
  
  TEMPERATURE("temperature", getTemperatureRegex()),
  DEW_POINT("dewPoint", getDewpointRegex());

  private final String groupName;
  private final String regex;

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)\\/(%s)(?=(?:\\s|$))",
      getTemperatureRegex(),
      getDewpointRegex()
    );
  }

  public String getGroupName() {
    return groupName;
  }

  public String getRegex() {
    return regex;
  }

  private static String getTemperatureRegex() {
    return "(?<temperature>M?\\d{2})";
  }

  private static String getDewpointRegex() {
    return "(?<dewPoint>M?\\d{2})";
  }

  public double toCelsius(String strValue) {
    switch (this) {
      case TEMPERATURE:
      case DEW_POINT:
        return strValue.startsWith("M")
          ? -Double.parseDouble(strValue.substring(1))
          : Double.parseDouble(strValue);
      default:
        throw new IllegalArgumentException("Invalid temperature type: " + this);
    }
  }

}
