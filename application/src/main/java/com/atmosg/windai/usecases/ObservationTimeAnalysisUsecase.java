package com.atmosg.windai.usecases;

import java.time.ZonedDateTime;
import java.util.List;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.unit.PressureUnit;
import com.atmosg.windai.unit.SpeedUnit;
import com.atmosg.windai.unit.TemperatureUnit;

public interface ObservationTimeAnalysisUsecase {
  
  List<ZonedDateTime> findTimeWhenVisibilityAtMost(MetarRetrievalPeriod period, int threshold, LengthUnit unit);

  List<ZonedDateTime> findTimeWhenAltimeterAtMost(MetarRetrievalPeriod period, int threshold, PressureUnit unit);

  List<ZonedDateTime> findTimeWhenAltimeterAtLeast(MetarRetrievalPeriod period, int threshold, PressureUnit unit);

  List<ZonedDateTime> findTimeWhenTemperatureAtLeast(MetarRetrievalPeriod period, int threshold, TemperatureUnit unit);

  List<ZonedDateTime> findTimeWhenTemperatureAtMost(MetarRetrievalPeriod period, int threshold, TemperatureUnit unit);

  List<ZonedDateTime> findTimeWhenPeakSpeedAtLeast(MetarRetrievalPeriod period, int threshold, SpeedUnit unit);

  List<ZonedDateTime> findTimeWhenCrosswindAtLeast(MetarRetrievalPeriod period, int runwayHeading, int threshold, SpeedUnit unit);

  List<ZonedDateTime> findTimeWhenPhenomenonObserved(MetarRetrievalPeriod period, String phenomena);

}
