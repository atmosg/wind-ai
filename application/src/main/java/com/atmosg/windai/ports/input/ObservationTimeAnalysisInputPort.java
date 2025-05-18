package com.atmosg.windai.ports.input;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.ports.output.MetarManagementOutputPort;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.unit.PressureUnit;
import com.atmosg.windai.unit.SpeedUnit;
import com.atmosg.windai.unit.TemperatureUnit;
import com.atmosg.windai.usecases.ObservationTimeAnalysisUsecase;
import com.atmosg.windai.vo.metar.Metar;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObservationTimeAnalysisInputPort implements ObservationTimeAnalysisUsecase {

  private MetarManagementOutputPort metarManagementOutputPort;

  @Override
  public List<ZonedDateTime> findTimeWhenVisibilityAtMost(MetarRetrievalPeriod period, int threshold, LengthUnit unit) {
    return analyze(period, (metar) -> metar.getVisibility().isAtMost(threshold, unit));
  }

  @Override
  public List<ZonedDateTime> findTimeWhenAltimeterAtMost(MetarRetrievalPeriod period, int threshold, PressureUnit unit) {
    return analyze(period, metar -> metar.getAltimeter().isAtMost(threshold, unit));
  }

  @Override
  public List<ZonedDateTime> findTimeWhenAltimeterAtLeast(MetarRetrievalPeriod period, int threshold, PressureUnit unit) {
    return analyze(period, metar -> metar.getAltimeter().isAtLeast(threshold, unit));
  }

  @Override
  public List<ZonedDateTime> findTimeWhenTemperatureAtLeast(MetarRetrievalPeriod period, int threshold, TemperatureUnit unit) {
    return analyze(period, metar -> metar.getTemperaturePair().getTemperature().isAtLeast(threshold, unit));
  }

  @Override
  public List<ZonedDateTime> findTimeWhenTemperatureAtMost(MetarRetrievalPeriod period, int threshold, TemperatureUnit unit) {
    return analyze(period, metar -> metar.getTemperaturePair().getTemperature().isAtMost(threshold, unit));
  }

  @Override
  public List<ZonedDateTime> findTimeWhenPeakSpeedAtLeast(MetarRetrievalPeriod period, int threshold, SpeedUnit unit) {
    return analyze(period, metar -> metar.getWind().isPeakSpeedAtLeast(threshold, unit));
  }

  @Override
  public List<ZonedDateTime> findTimeWhenCrosswindAtLeast(MetarRetrievalPeriod period, int runwayHeading, int threshold,
      SpeedUnit unit) {
    return analyze(period, metar -> metar.getWind().calculateCrosswind(runwayHeading)
        .isPeakSpeedAtLeast(threshold, unit));
  }

  @Override
  public List<ZonedDateTime> findTimeWhenPhenomenonObserved(MetarRetrievalPeriod period, String phenomena) {
    return analyze(period, metar -> metar.getWeatherGroup().containsPhenomena(phenomena));
  }

  private List<ZonedDateTime> analyze(MetarRetrievalPeriod period, Predicate<Metar> p) {
    return metarManagementOutputPort.retrieveMetars(period).stream()
        .filter(p)
        .map(Metar::getObservationTime)
        .collect(Collectors.toList());
  }

}
