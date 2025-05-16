package com.atmosg.windai.vo.metar.field;

import com.atmosg.windai.unit.PressureUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Pressure {

  private final double value;
  private final PressureUnit unit;

  public static Pressure of(double value, PressureUnit unit) {
    return Pressure.builder()
        .value(value)
        .unit(unit)
        .build();
  }

  public boolean isAtMost(double threshold, PressureUnit targetUnit) {
    return this.unit.convertTo(value, targetUnit) <= threshold;
  }

  public boolean isAtLeast(double threshold, PressureUnit targetUnit) {
    return this.unit.convertTo(value, targetUnit) >= threshold;
  }

}
