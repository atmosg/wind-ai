package com.atmosg.windai.dto.statistic;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.model.PredicateCondition;

public record PreciateStatisticQuery(
  String icao,
  MetarRetrievalPeriod period,
  PredicateCondition condition
) {}
