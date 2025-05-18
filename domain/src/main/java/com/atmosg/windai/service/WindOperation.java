package com.atmosg.windai.service;

import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.atmosg.windai.policy.crosswind.MinimumCrosswindPolicy;
import com.atmosg.windai.policy.crosswind.MinimumCrosswindPolicyType;
import com.atmosg.windai.policy.crosswind.MultiRunwayMinimumCrosswindPolicy;
import com.atmosg.windai.policy.crosswind.SingleRunwayMinimumCrosswindPolicy;
import com.atmosg.windai.policy.rounding.RoundingPolicy;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.vo.metar.field.Runway;
import com.atmosg.windai.vo.metar.field.Wind;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WindOperation {

  private RoundingPolicy roundingPolicy;
  private Map<MinimumCrosswindPolicyType, MinimumCrosswindPolicy> policyMap;

  public WindOperation(double runwayThreshold, LengthUnit unit) {
    this.roundingPolicy = RoundingPolicy.of(1, RoundingMode.HALF_UP);
    this.policyMap = Map.of(
      MinimumCrosswindPolicyType.MULTI_RUNWAY, new MultiRunwayMinimumCrosswindPolicy(runwayThreshold, unit),
      MinimumCrosswindPolicyType.SINGLE_RUNWAY, new SingleRunwayMinimumCrosswindPolicy()
    );
  }

  public double minimumCrosswind(Wind wind, List<Runway> runways, MinimumCrosswindPolicyType policyType) {
    if(policyType == null) {
      throw new IllegalArgumentException("Unsupported policy type: " + policyType);
    }
    
    return roundingPolicy.apply(policyMap.get(policyType).calculate(wind, runways));
  }

}