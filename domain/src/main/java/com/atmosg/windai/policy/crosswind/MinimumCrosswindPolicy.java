package com.atmosg.windai.policy.crosswind;

import java.util.List;

import com.atmosg.windai.vo.metar.field.Runway;
import com.atmosg.windai.vo.metar.field.Wind;

public interface MinimumCrosswindPolicy {
  
  double calculate(Wind wind, List<Runway> runways);

}
