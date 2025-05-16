package com.atmosg.windai.vo.metar;

import java.time.ZonedDateTime;

import com.atmosg.windai.vo.metar.field.CloudGroup;
import com.atmosg.windai.vo.metar.field.Pressure;
import com.atmosg.windai.vo.metar.field.TemperaturePair;
import com.atmosg.windai.vo.metar.field.Visibility;
import com.atmosg.windai.vo.metar.field.WeatherGroup;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.type.MetarReportType;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Metar {

  private final String rawText;
  
  // required fields (ICAO Annex 3)
  private final String stationIcao;
  private final MetarReportType reportType;
  private final ZonedDateTime observationTime;
  private final Wind wind;
  private final Visibility visibility;
  private final TemperaturePair temperaturePair;
  private final Pressure altimeter;
  
  // optional fields (ICAO Annex 3)
  private final WeatherGroup weatherGroup;
  private final CloudGroup cloudGroup;
  private final String remarks;


}