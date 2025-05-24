package com.atmosg.windai.model;

import java.util.List;
import java.util.function.BiPredicate;

import com.atmosg.windai.vo.metar.Metar;
import com.atmosg.windai.vo.metar.type.WeatherPhenomenon;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MetarBooleanField {

  HAS_PHENOMENA((m,list) -> m.getWeatherGroup().containsPhenomena(list));
  
  private final BiPredicate<Metar, List<WeatherPhenomenon>> tester;

  public boolean test(Metar metar, List<WeatherPhenomenon> phenomena) {
    return tester.test(metar, phenomena);
  }

}
