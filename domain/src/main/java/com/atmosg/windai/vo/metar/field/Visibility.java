package com.atmosg.windai.vo.metar.field;

import com.atmosg.windai.unit.LengthUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Visibility {

  private final double value;
  private final LengthUnit unit;

  public static Visibility of(double visibility, LengthUnit unit) {
    return Visibility.builder()
        .value(visibility)
        .unit(unit)
        .build();
  }

  public boolean isAtLeast(double threshold, LengthUnit targetUnit) {
    return this.unit.convertTo(value, targetUnit) >= threshold;
  }

  public boolean isAtMost(double threshold, LengthUnit targetUnit) {
    return this.unit.convertTo(value, targetUnit) <= threshold;
  }

}
