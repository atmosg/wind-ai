package com.atmosg.windai.vo.metar.field;

import com.atmosg.windai.unit.TemperatureUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Temperature {

  private final double value;
  private final TemperatureUnit unit;

  public static Temperature of(double value, TemperatureUnit unit) {
    return Temperature.builder()
        .value(value)
        .unit(unit)
        .build();
  }

  public boolean isAtLeast(double threshold, TemperatureUnit targetUnit) {
    return this.unit.convertTo(value, targetUnit) >= threshold;
  }

  public boolean isAtMost(double threshold, TemperatureUnit targetUnit) {
    return this.unit.convertTo(value, targetUnit) <= threshold;
  }

}
