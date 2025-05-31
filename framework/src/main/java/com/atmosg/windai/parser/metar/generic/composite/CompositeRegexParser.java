package com.atmosg.windai.parser.metar.generic.composite;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atmosg.windai.parser.metar.generic.entry.ObservationTimeRegexParser;
import com.atmosg.windai.parser.shared.ReportParser;
import com.atmosg.windai.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.type.MetarField;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompositeRegexParser implements ReportParser<Map<MetarField, Object>> {

  private final List<ReportRegexParser<?>> entires = new ArrayList<>();

  @Override
  public Map<MetarField, Object> parse(String rawText) {
    Map<MetarField, Object> parsingResultMap = new HashMap<>();

    for (ReportRegexParser<?> entryParser : entires) {
      parsingResultMap.put(entryParser.getFieldType(), entryParser.parse(rawText));
    }

    return parsingResultMap;
  }

  public void add(ReportRegexParser<?> entryParser) {
    this.entires.add(entryParser);
  }
  
  public void setYearMonth(YearMonth yearMonth) {
    for (int i=0; i<entires.size(); i++) {
      if (entires.get(i) instanceof ObservationTimeRegexParser otp) {
        entires.set(i, otp.withYearMonth(yearMonth));
      }
    }
  }

  public YearMonth getYearMonth() {
    return entires.stream()
      .filter(ObservationTimeRegexParser.class::isInstance)
      .map(ObservationTimeRegexParser.class::cast)
      .map(ObservationTimeRegexParser::getYearMonth)
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("ObservationTimeRegexParser not found in entries"));
  }

}
