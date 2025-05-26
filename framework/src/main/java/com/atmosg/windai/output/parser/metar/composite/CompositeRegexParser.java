package org.windai.domain.policy.parser.metar.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.windai.domain.policy.parser.shared.ReportParser;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.ReportFieldType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompositeRegexParser implements ReportParser<Map<ReportFieldType, Object>> {

  private final List<ReportRegexParser<?>> entires = new ArrayList<>();

  @Override
  public Map<ReportFieldType, Object> parse(String rawText) {
    Map<ReportFieldType, Object> parsingResultMap = new HashMap<>();

    for (ReportRegexParser<?> entryParser : entires) {
      parsingResultMap.put(entryParser.getFieldType(), entryParser.parse(rawText));
    }

    return parsingResultMap;
  }

  public void add(ReportRegexParser<?> entryParser) {
    this.entires.add(entryParser);
  }

}
