package com.atmosg.windai.unit;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LengthUnit {
  METERS(1.0),
  FEET(3.28084),
  MILE(0.000621371);

  private final double toMeterFactor;

  public double convertTo(double length, LengthUnit targetUnit) {
    if (this == targetUnit) return length;
    double lengthInMeters = length / toMeterFactor;
        
    return lengthInMeters * targetUnit.toMeterFactor;
  }
}
