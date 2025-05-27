package com.atmosg.windai.output.parser.metar.regex;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.atmosg.windai.vo.metar.type.WeatherDescriptor;
import com.atmosg.windai.vo.metar.type.WeatherInensity;
import com.atmosg.windai.vo.metar.type.WeatherPhenomenon;

public enum WeatherRegexes {
  
  INTENSITY(getIntensityRegex()),
  DESCRIPTOR(genDescriptorRegex()),
  PHENOMENON(genPhenomenonRegex());

  private final String regex;

  WeatherRegexes(String regex) {
    this.regex = regex;
  }

  public String getRegex() {
    return regex;
  }

  public static String fullPattern() {
    return String.format("(?:^|\\s)(%s)?(%s)?((?:%s){1,3})(?=(?:\\s|$))",
      getIntensityRegex(),
      genDescriptorRegex(),
      genPhenomenonRegex()
    );
  }

  private static String getIntensityRegex() {
    return  Arrays.stream(WeatherInensity.values())
      .map(WeatherInensity::getSymbol)
      .filter(symbol -> !symbol.isEmpty())
      .map(Pattern::quote)
      .collect(Collectors.joining("|"));
  }

  private static String genDescriptorRegex() {
    return Arrays.stream(WeatherDescriptor.values())
      .map(Enum::name)
      .collect(Collectors.joining("|"));
  }

  private static String genPhenomenonRegex() {
    return Arrays.stream(WeatherPhenomenon.values())
      .map(Enum::name)
      .collect(Collectors.joining("|"));
  }

}
