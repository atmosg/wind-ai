package com.atmosg.windai.output.parser.metar.entry;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import com.atmosg.windai.exception.GenericPolicyException;
import com.atmosg.windai.output.parser.metar.regex.TemperaturePairRegexes;
import com.atmosg.windai.output.parser.shared.ReportRegexParser;
import com.atmosg.windai.unit.TemperatureUnit;
import com.atmosg.windai.vo.metar.field.Temperature;
import com.atmosg.windai.vo.metar.field.TemperaturePair;
import com.atmosg.windai.vo.metar.type.MetarField;

public class TemperaturePairRegexParser extends ReportRegexParser<TemperaturePair> {

  private static final MetarField FIELD_TYPE= MetarField.TEMPERATURE_PAIR;
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
  public MetarField getFieldType() {
    return FIELD_TYPE;
  }
  
}
