package com.atmosg.windai.parser.metar.generic.entry;

import java.util.regex.Matcher;

import com.atmosg.windai.parser.metar.generic.regex.RemarkRegexes;
import com.atmosg.windai.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.type.MetarField;

public class RemarkRegexParser extends ReportRegexParser<String> {

  private static final MetarField FIELD_TYPE= MetarField.REMARKS;
  private static final String REMARK_REGEX = RemarkRegexes.fullPattern();

  @Override
  public String parse(String rawText) {
    Matcher matcher = getMatcher(rawText, REMARK_REGEX);

    if (!check(matcher)) {
      return "";
    }

    for (RemarkRegexes type: RemarkRegexes.values()) {
      String match = matcher.group(type.getGroupName());

      if (match == null || match.isEmpty()) {
        continue;
      }

      return match.trim();
    }

    return "";
  }

  @Override
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }
  
}
