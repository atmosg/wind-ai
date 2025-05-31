package com.atmosg.windai.parser.metar.generic.entry;

import java.util.regex.Matcher;

import com.atmosg.windai.parser.metar.generic.regex.MetarReportTypeRegexes;
import com.atmosg.windai.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.type.MetarField;
import com.atmosg.windai.vo.metar.type.MetarReportType;

public class MetarReportTypeRegexParser extends ReportRegexParser<MetarReportType> {

  private static final MetarField FIELD_TYPE= MetarField.REPORT_TYPE;
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
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }
  
}
