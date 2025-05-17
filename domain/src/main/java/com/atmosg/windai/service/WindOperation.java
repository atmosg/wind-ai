package com.atmosg.windai.service;

import java.math.RoundingMode;
import java.util.List;

import com.atmosg.windai.policy.crosswind.MultiRunwayMinimumCrosswindPolicy;
import com.atmosg.windai.policy.crosswind.SingleRunwayMinimumCrosswindPolicy;
import com.atmosg.windai.policy.rounding.RoundingPolicy;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.vo.metar.field.Runway;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.type.RunwayType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindOperation {

  private static final RoundingPolicy ROUNDING_POLICY = RoundingPolicy.of(1, RoundingMode.HALF_UP);

  private double runwayThreshold;
  private LengthUnit unit;

  public double minimumCrosswind(Wind wind, List<Runway> runways, RunwayType runwayType) {
    switch (runwayType) {
      case MULTI:
        return ROUNDING_POLICY.apply(
          new MultiRunwayMinimumCrosswindPolicy(runwayThreshold, unit)
            .calculate(wind, runways)
        );  
      case SINGLE:
        return ROUNDING_POLICY.apply(
          new SingleRunwayMinimumCrosswindPolicy()
            .calculate(wind, runways)
        );
      default:
        throw new IllegalArgumentException("Invalid runway type: " + runwayType);
    }
  }

  public double minimumCrosswind(Wind wind, List<Runway> runways, RunwayType runwayType, RoundingPolicy roundingPolicy) {
    switch (runwayType) {
      case MULTI:
        return roundingPolicy.apply(
          new MultiRunwayMinimumCrosswindPolicy(runwayThreshold, unit)
            .calculate(wind, runways)
        );  
      case SINGLE:
        return roundingPolicy.apply(
          new SingleRunwayMinimumCrosswindPolicy()
            .calculate(wind, runways)
        );
      default:
        throw new IllegalArgumentException("Invalid runway type: " + runwayType);
    }
  }

}