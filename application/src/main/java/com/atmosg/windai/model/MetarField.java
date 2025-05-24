package com.atmosg.windai.model;

import java.util.function.BiFunction;

import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.unit.Unit;
import com.atmosg.windai.vo.metar.Metar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MetarField {
  
  VISIBILITY((m,u) -> u.convertTo(m.getVisibility().getValue(), m.getVisibility().getUnit())),
  CEILING((m,u) -> u.convertTo(m.getCloudGroup().getLowestCeiling(), LengthUnit.FEET)),
  PEAK_WIND((m,u) -> u.convertTo(m.getWind().getPeakSpeed(), m.getWind().getUnit())),
  WIND_SPEED((m,u) -> u.convertTo(m.getWind().getSpeed(), m.getWind().getUnit())),
  ALTIMETER((m,u) -> u.convertTo(m.getAltimeter().getValue(), m.getAltimeter().getUnit()));
  
  private final BiFunction<Metar, Unit, Double> extractor;

  public double extract(Metar m, Unit targetUnit) {
    return extractor.apply(m, targetUnit);
  }

}
