package com.atmosg.windai.output.parser.metar.entry;

import java.util.regex.Matcher;

import com.atmosg.windai.exception.GenericPolicyException;
import com.atmosg.windai.output.parser.metar.regex.CloudRegexes;
import com.atmosg.windai.output.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.field.Cloud;
import com.atmosg.windai.vo.metar.type.CloudCoverage;
import com.atmosg.windai.vo.metar.type.CloudType;
import com.atmosg.windai.vo.metar.type.MetarField;

public class CloudRegexParser extends ReportRegexParser<Cloud> {

  private static final MetarField FIELD_TYPE = MetarField.CLOUD;
  private static final String CLOUD_REGEX = CloudRegexes.fullPattern();

  @Override
  public Cloud parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);
    
    if (!check(matcher)) {
      return null;
    }

    String coverageMatch = matcher.group(CloudRegexes.COVERAGE.getGroupName());
    String altitudeMatch = matcher.group(CloudRegexes.ALTITUDE.getGroupName());
    String typeMatch = matcher.group(CloudRegexes.TYPE.getGroupName());

    CloudCoverage coverage = CloudCoverage.valueOf(coverageMatch);
    CloudType type = typeMatch != null 
      ? CloudType.valueOf(typeMatch)
      : CloudType.NONE;

    Integer altitude = altitudeMatch != null 
      ? Integer.parseInt(altitudeMatch)*100 
      : null;

    if (coverage.requiresAltitude()) {
      if (altitude == null) {
        throw new GenericPolicyException("Altitude not found in report: " + rawText);
      }

      return Cloud.builder()
        .coverage(coverage)
        .altitude(altitude)
        .type(type)
        .build();
    } 
    
    return Cloud.builder()
      .coverage(coverage)
      .type(type)
      .build();
  }

  @Override
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }
  
}
