package com.atmosg.windai.vo.metar.field;

import java.util.List;

import com.atmosg.windai.vo.metar.type.WeatherPhenomenon;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class WeatherGroup {

  private final List<Weather> weatherList;

  @Builder
  public WeatherGroup(List<Weather> weatherList) {
    this.weatherList = List.copyOf(weatherList);
  }

  public static WeatherGroup of(List<Weather> weatherList) {
    return WeatherGroup.builder()
      .weatherList(weatherList)
      .build();
  }

  public static WeatherGroup ofEmpty() {
    return WeatherGroup.builder()
      .weatherList(List.of())
      .build();
  }
  
  public boolean containsPhenomena(String target) {
    return weatherList.stream()
    .anyMatch(weather -> weather.containsPhenomena(target));
  }
  
  public boolean containsPhenomena(List<WeatherPhenomenon> targetList) {
    return weatherList.stream()
    .anyMatch(weather -> weather.containsPhenomena(targetList));
  }
  
  public int size() {
    return weatherList.size();
  }

}
