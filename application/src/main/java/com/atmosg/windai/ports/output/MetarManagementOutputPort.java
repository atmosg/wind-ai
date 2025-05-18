package com.atmosg.windai.ports.output;

import java.time.ZonedDateTime;
import java.util.List;

import com.atmosg.windai.dto.MetarRetrievalPeriod;
import com.atmosg.windai.vo.metar.Metar;

public interface MetarManagementOutputPort {
  
  Metar retrieveMetar(String icao, ZonedDateTime time);

  List<Metar> retrieveMetars(MetarRetrievalPeriod period);

  void persistMetarRawText(String icao, ZonedDateTime time, String rawText);

}
