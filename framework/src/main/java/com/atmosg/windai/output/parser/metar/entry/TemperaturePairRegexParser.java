package org.windai.domain.policy.parser.metar.entry;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.windai.domain.exception.GenericPolicyException;
import org.windai.domain.policy.parser.metar.regex.TemperaturePairRegexes;
import org.windai.domain.policy.parser.shared.ReportRegexParser;
import org.windai.domain.unit.TemperatureUnit;
import org.windai.domain.vo.ReportFieldType;
import org.windai.domain.vo.Temperature;
import org.windai.domain.vo.TemperaturePair;

public class TemperaturePairRegexParser extends ReportRegexParser<TemperaturePair> {

  private static final ReportFieldType FIELD_TYPE= ReportFieldType.TEMPERATURE_PAIR;
  private static final String TEMPERATURE_PAIR_REGEX = TemperaturePairRegexes.fullPattern();

  @Override
  public TemperaturePair parse(String rawText) {
    Matcher matcher = getMatcher(rawText, TEMPERATURE_PAIR_REGEX);

    if (!check(matcher)) {
      throw new GenericPolicyException("TemperaturePair not found in report: " + rawText);
    }
    
    Map<String, Temperature> temperatureMap = new HashMap<>();
    for (TemperaturePairRegexes type : TemperaturePairRegexes.values()) {
      String match = matcher.group(type.getGroupName());
      
      if (match == null || match.isEmpty()) {
        throw new GenericPolicyException("Temperature must always exist as a pair: " + rawText);
      }

      temperatureMap.put(type.getGroupName(), Temperature.builder()
                                              .unit(TemperatureUnit.CELSIUS)
                                              .value(type.toCelsius(match))
                                              .build()
      );
    }

    return TemperaturePair.builder()
      .temperature(temperatureMap.get(TemperaturePairRegexes.TEMPERATURE.getGroupName()))
      .dewPoint(temperatureMap.get(TemperaturePairRegexes.DEW_POINT.getGroupName()))
      .build();

  }

  @Override
  public ReportFieldType getFieldType() {
    return FIELD_TYPE;
  }
  
}
