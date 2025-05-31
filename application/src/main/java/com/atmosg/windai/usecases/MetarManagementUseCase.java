package com.atmosg.windai.usecases;

import java.time.ZonedDateTime;
import java.util.List;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.vo.metar.Metar;

public interface MetarManagementUseCase {
  
  void persistMetar(Metar metar, ZonedDateTime issuedTime);

  Metar retrieveMetar(String icao, ZonedDateTime time);

  List<Metar> retrieveMetars(String icao, MetarRetrievalPeriod period);

}
