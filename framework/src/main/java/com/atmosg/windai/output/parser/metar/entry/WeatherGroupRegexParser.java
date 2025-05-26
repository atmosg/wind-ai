package org.windai.domain.policy.parser.metar.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.windai.domain.policy.parser.metar.regex.WeatherRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.ReportFieldType;
import org.windai.domain.vo.Weather;
import org.windai.domain.vo.WeatherGroup;

public class WeatherGroupRegexParser extends ReportRegexParser<WeatherGroup> {

  private static final ReportFieldType FIELD_TYPE= ReportFieldType.WEATHER_GROUP;
  private static final String WEATHER_REGEX = WeatherRegexes.fullPattern();

  @Override
  public WeatherGroup parse(String rawText) {
    Matcher matcher = getMatcher(rawText, WEATHER_REGEX);
    
    WeatherRegexParser weatherParser = new WeatherRegexParser();
    
    List<Weather> weatherList = new ArrayList<>();
    while (matcher.find()) {
      String matchedWeatherText = matcher.group(0);
      Weather weather = weatherParser.parse(matchedWeatherText);
      weatherList.add(weather);
    }

    return WeatherGroup.builder()
        .weatherList(weatherList)
        .build();
  }

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }
  
}
