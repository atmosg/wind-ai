package org.windai.domain.policy.parser.metar.entry;

import java.util.regex.Matcher;

import org.windai.domain.policy.parser.metar.regex.RemarkRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.ReportFieldType;

public class RemarkRegexParser extends ReportRegexParser<String> {

  private static final ReportFieldType FIELD_TYPE= ReportFieldType.REMARKS;
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
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }
  
}
