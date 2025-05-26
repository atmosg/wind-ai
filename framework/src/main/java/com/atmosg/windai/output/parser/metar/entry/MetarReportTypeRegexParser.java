package org.windai.domain.policy.parser.metar.entry;

import java.util.regex.Matcher;

import org.windai.domain.policy.parser.metar.regex.MetarReportTypeRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.MetarReportType;
import org.windai.domain.vo.ReportFieldType;

public class MetarReportTypeRegexParser extends ReportRegexParser<MetarReportType> {

  private static final ReportFieldType FIELD_TYPE= ReportFieldType.REPORT_TYPE;
  private static final String REPORT_TYPE_REGEX = MetarReportTypeRegexes.fullPattern();
    
  @Override
  public MetarReportType parse(String rawText) {
    Matcher matcher = getMatcher(rawText, REPORT_TYPE_REGEX);
    
    if (!check(matcher)) {
      return MetarReportType.METAR;
    }

    String reportType = matcher.group(MetarReportTypeRegexes.TYPE.getGroupName());

    return MetarReportType.valueOf(reportType);
  }

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }
  
}
