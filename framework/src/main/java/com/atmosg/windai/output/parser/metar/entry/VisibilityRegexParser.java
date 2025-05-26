package org.windai.domain.policy.parser.metar.entry;

import java.math.RoundingMode;
import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.regex.VisibilityRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.policy.rounding.RoundingPolicy;
import org.windai.domain.unit.LengthUnit;
import org.windai.domain.vo.ReportFieldType;
import org.windai.domain.vo.Visibility;

public class VisibilityRegexParser extends ReportRegexParser<Visibility> {

  private static final ReportFieldType FIELD_TYPE= ReportFieldType.VISIBILITY;
  private static final String VISIBILITY_REGEX = VisibilityRegexes.fullPattern();
  private static final RoundingPolicy policy = RoundingPolicy.of(0, RoundingMode.HALF_UP);

  @Override
  public Visibility parse(String rawText) {
    Matcher matcher = getMatcher(rawText, VISIBILITY_REGEX);

    if (!check(matcher)) {
      throw new GenericPolicyException("Visibility not found in report: " + rawText);
    }

    double visibility = -1;
    LengthUnit unit = LengthUnit.METERS;
    for (VisibilityRegexes type : VisibilityRegexes.values()) {
      String match = matcher.group(type.getGroupName());

      if (match == null || match.isEmpty())
        continue;

      visibility = type.toValue(match);
      unit = type.getUnit();
      break;
    }

    if (visibility < 0) {
      throw new GenericPolicyException("Visibility not found in report: " + rawText);
    }

    return Visibility.builder()
        .visibility(visibility)
        .lengthUnit(unit)
        .build();
  }

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }

}
