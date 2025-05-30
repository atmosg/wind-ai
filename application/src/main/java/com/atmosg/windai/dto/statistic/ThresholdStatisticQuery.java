package com.atmosg.windai.dto.statistic;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.model.ThresholdCondition;

public record ThresholdStatisticQuery(
  String icao,
  MetarRetrievalPeriod period,
  ThresholdCondition condition
) {}
