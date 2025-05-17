package com.atmosg.windai.service;

import java.util.List;

import com.atmosg.windai.policy.crosswind.MultiRunwayMinimumCrosswindPolicy;
import com.atmosg.windai.policy.crosswind.SingleRunwayMinimumCrosswindPolicy;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.vo.metar.field.Runway;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.type.RunwayType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindOperation {

  private double runwayThreshold;
  private LengthUnit unit;

  public double minimumCrosswind(Wind wind, List<Runway> runways, RunwayType runwayType) {
    switch (runwayType) {
      case MULTI:
        return new MultiRunwayMinimumCrosswindPolicy(runwayThreshold, unit)
            .calculate(wind, runways);
      case SINGLE:
        return new SingleRunwayMinimumCrosswindPolicy()
            .calculate(wind, runways);
      default:
        throw new IllegalArgumentException("Invalid runway type: " + runwayType);
    }
  }

}