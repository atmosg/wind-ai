package com.atmosg.windai.model;

import java.util.List;

import com.atmosg.windai.vo.metar.type.WeatherPhenomenon;

public record PredicateCondition(
  MetarBooleanField field,
  List<WeatherPhenomenon> targeList
) {}
