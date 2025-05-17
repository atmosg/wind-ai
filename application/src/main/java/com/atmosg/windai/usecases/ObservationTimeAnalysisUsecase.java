package com.atmosg.windai.usecases;

import java.time.ZonedDateTime;
import java.util.List;

import com.atmosg.windai.unit.LengthUnit;
import com.atmosg.windai.unit.SpeedUnit;
import com.atmosg.windai.vo.metar.Metar;

public interface ObservationTimeAnalysisUsecase {
  
  List<ZonedDateTime> findTimeWhenVisibilityAtMost(List<Metar> metar, int threshold, LengthUnit unit);

  List<ZonedDateTime> findTimeWhenPeakSpeedAtLeast(List<Metar> metar, int threshold, SpeedUnit unit);

  List<ZonedDateTime> findTimeWhenCrosswindAtLeast(List<Metar> metar, int threshold, SpeedUnit unit);

  List<ZonedDateTime> findTimeWhenPhenomenonObserved(List<Metar> metar, String phenomena);

}
