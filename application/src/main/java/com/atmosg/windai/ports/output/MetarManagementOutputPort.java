package com.atmosg.windai.ports.output;

import java.time.ZonedDateTime;
import java.util.List;

import com.atmosg.windai.vo.metar.Metar;

public interface MetarManagementOutputPort {
  
  Metar retrieveMetar(String icao, ZonedDateTime time);

  List<Metar> retrieveMetars(String icao, ZonedDateTime st, ZonedDateTime ed);

  void persistRawText(String icao, ZonedDateTime time);

}
