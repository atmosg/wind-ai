package com.atmosg.windai.wind;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import static com.atmosg.windai.unit.SpeedUnit.*;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.field.WindDirection;

public class CrosswindTest {
  
  @Test
  void 측풍_계산에_성공해야한다() {
    // given
    Wind wind = Wind.of(WindDirection.fixed(270), 10, 25, KT);

    // when
    Wind expected = Wind.of(WindDirection.fixed(270), 0, 0, KT);
    Wind actual = wind.calculateCrosswind(9);

    // then
    assertEquals(expected, actual);
  }

  @Test
  void 가변풍향은_측풍계산에_실패해야한다() {
    Wind wind = Wind.of(WindDirection.variable(), 10, 25, KT);

    assertThrows(IllegalStateException.class, () -> 
      wind.calculateCrosswind(9)
    );
  }

}
