package com.atmosg.windai.parser.metar.generic.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.atmosg.windai.parser.metar.generic.regex.WeatherRegexes;
import com.atmosg.windai.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.field.Weather;
import com.atmosg.windai.vo.metar.type.MetarField;
import com.atmosg.windai.vo.metar.type.WeatherDescriptor;
import com.atmosg.windai.vo.metar.type.WeatherInensity;
import com.atmosg.windai.vo.metar.type.WeatherPhenomenon;

public class WeatherRegexParser extends ReportRegexParser<Weather> {
  
  private static final MetarField FIELD_TYPE = MetarField.WEATHER;
  private static final String WEATHER_REGEX = WeatherRegexes.fullPattern();
  private static final String PHENOMENON_REGEX = WeatherRegexes.PHENOMENON.getRegex();

  @Override
  public Weather parse(String rawText) {
    Matcher matcher = getMatcher(rawText, WEATHER_REGEX);

    if (!check(matcher)) {
      return null;
    }
    
    String intensityMatch = matcher.group(1);
    String descriptorMatch = matcher.group(2);
    String phenomenonMatch = matcher.group(3);

    WeatherInensity intensity = intensityMatch != null 
      ? WeatherInensity.fromSymbol(intensityMatch) 
      : WeatherInensity.MODERATE;

    WeatherDescriptor descriptor = descriptorMatch != null
      ? WeatherDescriptor.valueOf(descriptorMatch)
      : null;
    
    List<WeatherPhenomenon> phenomena = parsePhenomena(phenomenonMatch, PHENOMENON_REGEX);

    if (descriptor == null && phenomena.isEmpty()) {
      return null;
    }

    return Weather.builder()
      .intensity(intensity)
      .descriptor(descriptor)
      .phenomena(phenomena)
      .build();
      
  }

  private List<WeatherPhenomenon> parsePhenomena(String phenomenonMatch, String phenomenonRegex) {
    List<WeatherPhenomenon> phenomena = new ArrayList<>();
    
    Matcher matcher = getMatcher(phenomenonMatch, phenomenonRegex);
    
    while (matcher.find()) {
      String matched = matcher.group(0);
      WeatherPhenomenon phenomenon = WeatherPhenomenon.valueOf(matched);
      phenomena.add(phenomenon);
    }

    return phenomena;
  }

  @Override
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }
  
}
