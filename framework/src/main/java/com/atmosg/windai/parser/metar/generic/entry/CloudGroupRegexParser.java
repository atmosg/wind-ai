package com.atmosg.windai.parser.metar.generic.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.atmosg.windai.parser.metar.generic.regex.CloudRegexes;
import com.atmosg.windai.parser.shared.ReportRegexParser;
import com.atmosg.windai.vo.metar.field.Cloud;
import com.atmosg.windai.vo.metar.field.CloudGroup;
import com.atmosg.windai.vo.metar.type.MetarField;

public class CloudGroupRegexParser extends ReportRegexParser<CloudGroup> {

  private static final MetarField FIELD_TYPE = MetarField.CLOUD_GROUP;
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
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }

}
