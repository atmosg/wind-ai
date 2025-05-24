package com.atmosg.windai.unit;

public interface Unit {
  
  double toBase(double value);
  double fromBase(double baseValue);

  default double convertTo(double value, Unit targetUnit) {
    double base = targetUnit.toBase(value);
    return fromBase(base);
  }

}
