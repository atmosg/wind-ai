package com.atmosg.windai.output.parser.metar.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atmosg.windai.output.parser.shared.ReportParser;
import com.atmosg.windai.output.parser.shared.ReportRegexParser;
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

  public void remove(ReportRegexParser<?> entryParser) {
    this.entires.remove(entryParser);
  }

  public void changeParser(ReportRegexParser<?> oldParser, ReportRegexParser<?> newParser) {
    int index = this.entires.indexOf(oldParser);
    if (index != -1) {
      this.entires.set(index, newParser);
    }
  }

}
