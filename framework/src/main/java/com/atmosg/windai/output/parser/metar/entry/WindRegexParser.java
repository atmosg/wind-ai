package org.windai.domain.policy.parser.metar.entry;

import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.regex.WindRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.unit.SpeedUnit;
import org.windai.domain.vo.ReportFieldType;
import org.windai.domain.vo.Wind;
import org.windai.domain.vo.WindDirection;
import org.windai.domain.vo.WindDirectionType;

public class WindRegexParser extends ReportRegexParser<Wind> {
    
  private static final ReportFieldType FIELD_TYPE = ReportFieldType.WIND;
  private static final String WIND_REGEX = WindRegexes.fullPattern();
  
  @Override
  public Wind parse(String rawText) {
    Matcher matcher = getMatcher(rawText, WIND_REGEX);

    if (!check(matcher)) {
      throw new GenericPolicyException("Wind not found in report:  " + rawText);
    }

    String windDirection = matcher.group(WindRegexes.DIRECTION.getGroupName());
    String windSpeed = matcher.group(WindRegexes.SPEED.getGroupName());
    String windGusts = matcher.group(WindRegexes.GUSTS.getGroupName());
    String windUnit = matcher.group(WindRegexes.UNIT.getGroupName());

    WindDirection direction = windDirection.equals(WindDirectionType.VARIABLE.getSymbol())
      ? WindDirection.variable()
      : WindDirection.fixed(Double.valueOf(windDirection));
      
    double windSpeedValue = Double.parseDouble(windSpeed);
    double windGustsValue = windGusts != null ? Double.parseDouble(windGusts) : 0;
    SpeedUnit speedUnit = windUnit.equals(SpeedUnit.KT.getSymbol()) ? SpeedUnit.KT : SpeedUnit.MPS;

    return Wind.builder()
        .direction(direction)
        .speed(windSpeedValue)
        .gusts(windGustsValue)
        .speedUnit(speedUnit)
        .build();
  }

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }

}
