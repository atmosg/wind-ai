package com.atmosg.windai.unit;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PressureUnit {
  
  HPA(1.0),
  INHG(0.02953);

  private final double toHpaFactor;

  public double convertTo(double value, PressureUnit targetUnit) {
    if (this == targetUnit) return value;

    double valueInHpa = value / toHpaFactor;
    return valueInHpa * targetUnit.toHpaFactor;
  }
  
}
