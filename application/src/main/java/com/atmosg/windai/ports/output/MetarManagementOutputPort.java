package com.atmosg.windai.ports.output;

import java.time.ZonedDateTime;
import java.util.List;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.vo.metar.Metar;

public interface MetarManagementOutputPort {
  
  void persistMetar(Metar metar, ZonedDateTime issuedTime);
  
  Metar retrieveMetar(String icao, ZonedDateTime time);

  List<Metar> retrieveMetars(String icao, MetarRetrievalPeriod period);


}
