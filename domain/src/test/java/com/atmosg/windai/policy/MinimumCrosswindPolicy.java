package com.atmosg.windai.policy;

import org.junit.jupiter.api.Test;

import static com.atmosg.windai.unit.LengthUnit.*;
import static com.atmosg.windai.unit.SpeedUnit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import static com.atmosg.windai.policy.crosswind.MinimumCrosswindPolicyType.*;
import com.atmosg.windai.service.WindOperation;
import com.atmosg.windai.vo.metar.field.Runway;
import com.atmosg.windai.vo.metar.field.RunwayEnd;
import com.atmosg.windai.vo.metar.field.Wind;
import com.atmosg.windai.vo.metar.field.WindDirection;
import com.atmosg.windai.vo.metar.type.RunwaySide;

public class MinimumCrosswindPolicy {

  Runway singleRunway1 = Runway.of(
        RunwayEnd.of(34, RunwaySide.NONE, true),
        RunwayEnd.of(16, RunwaySide.NONE, true),
        8000,
        FEET);

  Runway singleRunway2 = Runway.of(
        RunwayEnd.of(33, RunwaySide.NONE, true),
        RunwayEnd.of(15, RunwaySide.NONE, true),
        8000,
        FEET);

  Runway shortRunway = Runway.of(
        RunwayEnd.of(34, RunwaySide.NONE, true),
        RunwayEnd.of(16, RunwaySide.NONE, true),
        13_000,
        FEET);

  Runway longRunway = Runway.of(
        RunwayEnd.of(33, RunwaySide.NONE, true),
        RunwayEnd.of(15, RunwaySide.NONE, true),
        7_000,
        FEET);

  WindOperation wo = new WindOperation(8000, FEET);

  @Test
  void 단일활주로의_측풍최소치_정책_연산에_성공해야한다() {
    Wind wind = Wind.of(WindDirection.fixed(100), 20, 40, KT);

    // when
    double expected = 34.6;
    double actual = wo.minimumCrosswind(wind, List.of(singleRunway1), SINGLE_RUNWAY);
    
    assertEquals(expected, actual);
  }

  @Test
  void 복수활주로의_측풍최소치_정책_연산에_성공해야한다() {
    Wind wind = Wind.of(WindDirection.fixed(100), 20, 40, KT);

    double expected = 30.6;
    double actual = wo.minimumCrosswind(wind, List.of(singleRunway1, singleRunway2), MULTI_RUNWAY);

    assertEquals(expected, actual);
  }

  @Test
  void 복수활주로에서_활주로길이가_임계치_이하인_활주로는_연산에서_제외돼야한다() {
    Wind wind = Wind.of(WindDirection.fixed(100), 20, 40, KT);

    double expected = 34.6;
    double actual = wo.minimumCrosswind(wind, List.of(shortRunway, longRunway), MULTI_RUNWAY);

    assertEquals(expected, actual);
  }

}
