package com.atmosg.windai.unit;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SpeedUnit {

  MPS(1.0, "MPS"),
  KT(1.94384, "KT"),
  KPH(3.6, "KPH");

  private final double toMpsFactor;
  private final String symbol;

  public String getSymbol() {
    return symbol;
  }

  public double convertTo(double value, SpeedUnit targetUnit) {
    if (this == targetUnit) return value;

    double valueInMps = value / toMpsFactor;
    return valueInMps * targetUnit.toMpsFactor;
  }

}
