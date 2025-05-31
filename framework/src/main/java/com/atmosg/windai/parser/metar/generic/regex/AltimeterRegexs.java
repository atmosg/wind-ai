package com.atmosg.windai.parser.metar.generic.regex;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.atmosg.windai.policy.rounding.RoundingPolicy;
import com.atmosg.windai.unit.PressureUnit;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AltimeterRegexs {
  
  HECTO_PASCAL("hPa", getHectoPascalRegex()),
  INCH_OF_MERCURY("inHg", getInchOfMercuryRegex());

  private final String groupName;
  private final String regex;

  public String getRegex() {
    return regex;
  }

  public String getGroupName() {
    return groupName;
  }

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)(?=(?:\\s|$))",
      Arrays.stream(AltimeterRegexs.values())
        .map(AltimeterRegexs::getRegex)
        .collect(Collectors.joining("|"))
      );
  }

  private static String getHectoPascalRegex() {
    return "Q(?<hPa>\\d{4})";
  }

  private static String getInchOfMercuryRegex() {
    return "A(?<inHg>\\d{4})";
  }

  public double toValue(String strValue) {
    switch (this) {
      case HECTO_PASCAL:
        return Double.parseDouble(strValue);
      case INCH_OF_MERCURY: {
        return Double.parseDouble(strValue)/100;
      }
      default:
        throw new IllegalArgumentException("Invalid altimeter type: " + this);
    }
  }

  public double toHectoPascal(String strValue, RoundingPolicy policy) {
    switch (this) {
      case HECTO_PASCAL:
        return Double.parseDouble(strValue);
      case INCH_OF_MERCURY: {
        double hPa = PressureUnit.INHG.convertTo(Double.parseDouble(strValue)/100, PressureUnit.HPA);
        return policy.apply(hPa);
      }
      default:
        throw new IllegalArgumentException("Invalid altimeter type: " + this);
    }
  }

  public PressureUnit getUnit() {
    switch (this) {
      case HECTO_PASCAL:
        return PressureUnit.HPA;
      case INCH_OF_MERCURY:
        return PressureUnit.INHG;
      default:
        throw new IllegalArgumentException("Invalid pressure unit: " + this);
    }
  }

}
