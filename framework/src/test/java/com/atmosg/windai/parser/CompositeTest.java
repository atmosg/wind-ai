package com.atmosg.windai.parser;

import static org.junit.Assert.assertEquals;

import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import com.atmosg.windai.parser.metar.generic.composite.CompositeRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.AltimeterRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.CloudGroupRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.MetarReportTypeRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.ObservationTimeRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.RemarkRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.StationIcaoRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.TemperaturePairRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.VisibilityRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.WeatherGroupRegexParser;
import com.atmosg.windai.parser.metar.generic.entry.WindRegexParser;

public class CompositeTest {

  CompositeRegexParser parser = new CompositeRegexParser();
  
  public CompositeTest() {
    init();
  }

  @Test
  public void 컴포지트_파서의_파서리스트_항목을_교체할_수_있어야한다() {
    YearMonth yearMonth = YearMonth.of(2025, 6);
    parser.setYearMonth(yearMonth);

    Month actual = parser.getYearMonth().getMonth();
    Month expected = Month.JUNE;
    
    assertEquals(expected, actual);
  }

  private void init() {
    YearMonth yearMonth = YearMonth.of(2025, 5);
    var stationIcaoRegexParser = new StationIcaoRegexParser();
    var metarReportTypeRegexParser = new MetarReportTypeRegexParser();
    var observationTimeRegexParser = new ObservationTimeRegexParser(yearMonth);
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
    parser.add(windRegexParser);
    parser.add(visibilityRegexParser);
    parser.add(temperaturePairRegexParser);
    parser.add(altimeterRegexParser);
    parser.add(weatherGroupRegexParser);
    parser.add(cloudGroupRegexParser);
    parser.add(remarkRegexParser);
  }

}
