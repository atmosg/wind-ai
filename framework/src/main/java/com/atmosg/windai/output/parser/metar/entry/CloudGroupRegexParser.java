package org.windai.domain.policy.parser.metar.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.windai.domain.policy.parser.metar.regex.CloudRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.vo.Cloud;
import org.windai.domain.vo.CloudGroup;
import org.windai.domain.vo.ReportFieldType;

public class CloudGroupRegexParser extends ReportRegexParser<CloudGroup> {

  private static final ReportFieldType FIELD_TYPE = ReportFieldType.CLOUD_GROUP;
  private static final String CLOUD_REGEX = CloudRegexes.fullPattern();

  @Override
  public CloudGroup parse(String rawText) {
    Matcher matcher = getMatcher(rawText, CLOUD_REGEX);
    CloudRegexParser cloudParser = new CloudRegexParser();

    List<Cloud> clouds = new ArrayList<>();
    while (matcher.find()) {
      String matchedCloudText = matcher.group(0);
      Cloud cloud = cloudParser.parse(matchedCloudText);
      clouds.add(cloud);
    }

    return CloudGroup.builder()
        .clouds(clouds)
        .build();

  }

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }

}
