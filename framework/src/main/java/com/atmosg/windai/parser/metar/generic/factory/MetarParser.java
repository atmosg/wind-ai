package com.atmosg.windai.parser.metar.generic.factory;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Map;

import com.atmosg.windai.exception.MetarParseException;
import com.atmosg.windai.parser.metar.generic.composite.CompositeRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.AltimeterRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.CloudGroupRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.MetarReportTypeRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.ObservationTimeRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.RemarkRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.ReportTimeRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.StationIcaoRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.TemperaturePairRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.VisibilityRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.WeatherGroupRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.WindRegexParser;
import com.atmosg.windai.vo.metar.Metar;
import com.atmosg.windai.vo.metar.field.CloudGroup;
import com.atmosg.windai.vo.metar.field.Pressure;
import com.atmosg.windai.vo.metar.field.TemperaturePair;
import com.atmosg.windai.vo.metar.field.Visibility;
import com.atmosg.windai.vo.metar.field.WeatherGroup;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.type.MetarField;
import com.atmosg.windai.vo.metar.type.MetarReportType;

import static com.atmosg.windai.vo.metar.type.MetarField.*;

public class MetarParser {

  private final CompositeRegexParser parser;

  public MetarParser(YearMonth yearMonth) {
    this.parser = new CompositeRegexParser();
    init(yearMonth);
  }

  public Metar parse(String rawText) {
    Map<MetarField, Object> map = parser.parse(rawText);
    try {
      return Metar.builder()
          .rawText(rawText)
          .stationIcao((String) require(map, STATION_ICAO))
          .reportType((MetarReportType) require(map, REPORT_TYPE))
          .observationTime((ZonedDateTime) require(map, OBSERVATION_TIME))
          .reportTime((ZonedDateTime) require(map, REPORT_TIME))
          .wind((Wind) require(map, WIND))
          .visibility((Visibility) require(map, VISIBILITY))
          .temperaturePair((TemperaturePair) require(map, TEMPERATURE_PAIR))
          .altimeter((Pressure) require(map, ALTIMETER))
          .weatherGroup((WeatherGroup) map.getOrDefault(WEATHER_GROUP, WeatherGroup.ofEmpty()))
          .cloudGroup((CloudGroup) map.getOrDefault(CLOUD_GROUP, CloudGroup.ofEmpty()))
          .remarks((String) map.getOrDefault(REMARKS, ""))
          .build();
    } 
    catch (ClassCastException | NullPointerException e) {
      throw new MetarParseException("Failed to build Metar from raw: " + rawText, e);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> T require(Map<MetarField, Object> m, MetarField field) {
    Object v = m.get(field);
    if (v == null) {
      throw new MetarParseException("Missing required field: " + field);
    }
    return (T) v;
  }

  public void setYearMonth(YearMonth ym) {
    parser.setYearMonth(ym);
  }

  /** 현재 사용 중인 연/월을 조회 */
  public YearMonth getYearMonth() {
    return parser.getYearMonth();
  }

  private void init(YearMonth yearMonth) {
    var stationIcaoRegexParser = new StationIcaoRegexParser();
    var metarReportTypeRegexParser = new MetarReportTypeRegexParser();
    var observationTimeRegexParser = new ObservationTimeRegexParser(yearMonth);
    var reportTimeRegexParser = new ReportTimeRegexParser(yearMonth);
    var windRegexParser = new WindRegexParser();
    var visibilityRegexParser = new VisibilityRegexParser();
    var temperaturePairRegexParser = new TemperaturePairRegexParser();
    var altimeterRegexParser = new AltimeterRegexParser();
    var weatherGroupRegexParser = new WeatherGroupRegexParser();
    var cloudGroupRegexParser = new CloudGroupRegexParser();
    var remarkRegexParser = new RemarkRegexParser();

    parser.add(stationIcaoRegexParser);
    parser.add(metarReportTypeRegexParser);
    parser.add(observationTimeRegexParser);
    parser.add(reportTimeRegexParser);
    parser.add(windRegexParser);
    parser.add(visibilityRegexParser);
    parser.add(temperaturePairRegexParser);
    parser.add(altimeterRegexParser);
    parser.add(weatherGroupRegexParser);
    parser.add(cloudGroupRegexParser);
    parser.add(remarkRegexParser);
  }

}
