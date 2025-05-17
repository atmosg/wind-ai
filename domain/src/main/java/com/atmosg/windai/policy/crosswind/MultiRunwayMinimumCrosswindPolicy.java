package com.atmosg.windai.policy.crosswind;

import java.util.List;
import java.util.stream.Stream;

import com.atmosg.windai.exception.GenericPolicyException;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.vo.metar.field.Runway;
import com.atmosg.windai.vo.metar.field.RunwayEnd;
import com.atmosg.windai.vo.metar.field.Wind;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MultiRunwayMinimumCrosswindPolicy implements MinimumCrosswindPolicy {

  private final double runwayThreshold;
  private final LengthUnit unit;

  @Override
  public double calculate(Wind wind, List<Runway> runways) {
    if (runways.size() < 2) {
      throw new GenericPolicyException("At least two runway is required.");
    }

    boolean everyRunwayLessThan = isEveryRunwayLessThan(runways);

    return runways.stream()
      .filter(runway -> everyRunwayLessThan || runway.isAtLeast(runwayThreshold, unit))
      .flatMap(runway -> Stream.of(runway.getEndA(), runway.getEndB()))
      .filter(RunwayEnd::isAvailable)
      .map(end -> wind.calculateCrosswind(end.getHeading()))
      .mapToDouble(Wind::getPeakSpeed)
      .min()
      .orElseThrow(() -> new GenericPolicyException("No available runway ends for crosswind calculation."));
  }

  private boolean isEveryRunwayLessThan(List<Runway> runways) {
    return runways.stream()
      .allMatch(runway -> runway.isLessThan(runwayThreshold, unit));
  }
  
}
