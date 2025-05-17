package com.atmosg.windai.vo.metar.field;

import com.atmosg.windai.unit.SpeedUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Wind {

  private final WindDirection direction;
  private final double speed;
  private final double gusts;
  private final SpeedUnit unit;

  public static Wind of(WindDirection direction, double speed, double gusts, SpeedUnit unit) {
    return Wind.builder()
        .direction(direction)
        .speed(speed)
        .gusts(gusts)
        .unit(unit)
        .build();
  }

  public Wind calculateCrosswind(double runwayHeading) {
    double degree = direction.getDegreeOrThrow();

    double radians = Math.toRadians(degree - 10 * runwayHeading);
    double factor = Math.abs(Math.sin(radians));
    return Wind.of(direction, speed * factor, gusts * factor, unit);
  }

  public boolean isSpeedAtMost(double threshold, SpeedUnit targetUnit) {
    return this.unit.convertTo(speed, targetUnit) <= threshold;
  }

  public boolean isSpeedAtLeast(double threshold, SpeedUnit targetUnit) {
    return this.unit.convertTo(speed, targetUnit) >= threshold;
  }

  public boolean isPeakSpeedAtMost(double threshold, SpeedUnit targetUnit) {
    return this.unit.convertTo(getPeakSpeed(), targetUnit) <= threshold;
  }

  public boolean isPeakSpeedAtLeast(double threshold, SpeedUnit targetUnit) {
    return this.unit.convertTo(getPeakSpeed(), targetUnit) >= threshold;
  }

  public double getPeakSpeed() {
    return Math.max(speed, gusts);
  }

}
