package com.atmosg.windai.policy.crosswind;

import java.util.List;
import java.util.stream.Stream;

import com.atmosg.windai.exception.GenericPolicyException;
import com.atmosg.windai.vo.metar.field.Runway;
import com.atmosg.windai.vo.metar.field.RunwayEnd;
import com.atmosg.windai.vo.metar.field.Wind;

public class SingleRunwayMinimumCrosswindPolicy implements MinimumCrosswindPolicy {

  @Override
  public double calculate(Wind wind, List<Runway> runways) {
    if (runways.size() != 2) {
      throw new GenericPolicyException("Only one runway is allowed for this policy.");
    }

    return runways.stream()
      .flatMap(runway -> Stream.of(runway.getEndA(), runway.getEndB()))
      .filter(RunwayEnd::isAvailable)
      .map(end -> wind.calculateCrosswind(end.getHeading()))
      .mapToDouble(Wind::getPeakSpeed)
      .min()
      .orElseThrow(() -> new GenericPolicyException("No available runway ends for crosswind calculation."));
  }
  
}