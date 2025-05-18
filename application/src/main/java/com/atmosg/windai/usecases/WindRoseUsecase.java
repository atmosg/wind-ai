package com.atmosg.windai.usecases;

import java.time.Month;
import java.util.List;
import java.util.Map;

import com.atmosg.windai.dto.DirectionBin;
import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.dto.SpeedBin;
import com.atmosg.windai.dto.WindRose;

public interface WindRoseUsecase {
  
  public Map<Month, WindRose> generateMonthlyWindRose(MetarRetrievalPeriod period, List<SpeedBin> speedBins, List<DirectionBin> directionBins);

}
