package com.atmosg.windai.output.parser.metar.entry;

import java.math.RoundingMode;
import java.util.regex.Matcher;

import com.atmosg.windai.output.parser.metar.regex.AltimeterRegexs;
import com.atmosg.windai.output.parser.shared.ReportRegexParser;
import com.atmosg.windai.policy.rounding.RoundingPolicy;
import com.atmosg.windai.unit.PressureUnit;
import com.atmosg.windai.vo.metar.field.Pressure;
import com.atmosg.windai.vo.metar.type.MetarField;

public class AltimeterRegexParser extends ReportRegexParser<Pressure> {

  private static final MetarField FIELD_TYPE= MetarField.ALTIMETER;
  private static final String ALTIMETER_REGEX = AltimeterRegexs.fullPattern();
  private static final RoundingPolicy roundingPolicy = RoundingPolicy.of(0, RoundingMode.HALF_UP);

  @Override
  public Pressure parse(String rawText) {
    Matcher matcher = getMatcher(rawText, ALTIMETER_REGEX);
    if (!check(matcher)) {
      throw new IllegalArgumentException("Altimeter not found in report: " + rawText);
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
      throw new IllegalArgumentException("Altimeter not found in report: " + rawText);
    }
            
    return Pressure.builder()
        .value(altimeter)
        .unit(unit)
        .build();
    
  }

  @Override
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }

}
