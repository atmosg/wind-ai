package com.atmosg.windai.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UnitTest {
  
  @Test
  void 길이단위_변환에_성공해야한다() {
    double value = 1;

    double actual = LengthUnit.MILE.convertTo(value, LengthUnit.METERS);
    double expected = 1609.3444978925634;

    assertEquals(expected, actual);
  }

  @Test
  void 온도단위_변환에_성공해야한다() {
    double value = 10;

    double actual = TemperatureUnit.CELSIUS.convertTo(value, TemperatureUnit.FAHRENHEIT);
    double expected = 50;

    assertEquals(expected, actual);
  }

}
