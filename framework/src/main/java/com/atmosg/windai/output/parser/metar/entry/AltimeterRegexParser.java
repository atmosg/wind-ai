package org.windai.domain.policy.parser.metar.entry;

import java.math.RoundingMode;
import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.regex.AltimeterRegexs;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.policy.rounding.RoundingPolicy;
import org.windai.domain.unit.PressureUnit;
import org.windai.domain.vo.Pressure;
import org.windai.domain.vo.ReportFieldType;

public class AltimeterRegexParser extends ReportRegexParser<Pressure> {

  private static final ReportFieldType FIELD_TYPE= ReportFieldType.ALTIMETER;
  private static final String ALTIMETER_REGEX = AltimeterRegexs.fullPattern();
  private static final RoundingPolicy roundingPolicy = RoundingPolicy.of(0, RoundingMode.HALF_UP);

  @Override
  public Pressure parse(String rawText) {
    Matcher matcher = getMatcher(rawText, ALTIMETER_REGEX);
    if (!check(matcher)) {
      throw new GenericPolicyException("Altimeter not found in report: " + rawText);
    }

    double altimeter = -1;
    PressureUnit unit = PressureUnit.HPA;
    for (AltimeterRegexs type: AltimeterRegexs.values()) {
      String match = matcher.group(type.getGroupName());
      
      if (match == null || match.isEmpty())
        continue;

      altimeter = type.toValue(match);
      unit = type.getUnit();
      break;
    }

    if (altimeter < 0) {
      throw new GenericPolicyException("Altimeter not found in report: " + rawText);
    }
            
    return Pressure.builder()
        .value(altimeter)
        .pressureUnit(unit)
        .build();
    
  }

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }

}
