package com.atmosg.windai.vo.metar.field;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TemperaturePair {

  private final Temperature temperature;
  private final Temperature dewPoint;

  public static TemperaturePair of(Temperature temperature, Temperature dewPoint) {
    return TemperaturePair.builder()
        .temperature(temperature)
        .dewPoint(dewPoint)
        .build();
  }

  public TemperaturePair(Temperature temperature, Temperature dewPoint) {
    if (temperature.getUnit() != dewPoint.getUnit()) {
      throw new IllegalArgumentException("Temperature and Dew Point must be in the same unit.");
    }
    this.temperature = temperature;
    this.dewPoint = dewPoint;
  }

}
