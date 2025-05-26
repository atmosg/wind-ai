package org.windai.domain.policy.parser.metar.entry;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.regex.CloudRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudCoverage;
import org.windai.domain.vo.CloudType;
import org.windai.domain.vo.ReportFieldType;

public class CloudRegexParser extends ReportRegexParser<Cloud> {

  private static final ReportFieldType FIELD_TYPE = ReportFieldType.CLOUD;
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
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }
  
}
