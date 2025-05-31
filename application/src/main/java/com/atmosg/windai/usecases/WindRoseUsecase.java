package com.atmosg.windai.usecases;

import java.time.Month;
import java.util.List;
import java.util.Map;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.dto.windrose.DirectionBin;
import com.atmosg.windai.dto.windrose.SpeedBin;
import com.atmosg.windai.dto.windrose.WindRose;

public interface WindRoseUsecase {
  
  public Map<Month, WindRose> generateMonthlyWindRose(String icao, MetarRetrievalPeriod period, List<SpeedBin> speedBins, List<DirectionBin> directionBins);

}
