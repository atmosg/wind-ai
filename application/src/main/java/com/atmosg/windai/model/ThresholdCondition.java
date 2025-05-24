package com.atmosg.windai.model;

import com.atmosg.windai.unit.Unit;

public record ThresholdCondition(
  MetarField        field,
  Comparison        comparison,
  double            threshold,
  Unit              unit
) {}
