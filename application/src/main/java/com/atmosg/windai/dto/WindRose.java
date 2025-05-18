package com.atmosg.windai.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WindRose {
  
  private final List<SpeedBin> speedBins;
  private final List<DirectionBin> directionBins;
  private final Map<BinPair, Long> frequencyMap;

  public record BinPair(SpeedBin speedBin, DirectionBin directionBin) {}

  @Builder
  public WindRose(List<SpeedBin> speedBins, List<DirectionBin> directionBins, Map<BinPair, Long> frequencyMap) {
    this.speedBins = List.copyOf(speedBins);
    this.directionBins = List.copyOf(directionBins);

    LinkedHashMap<BinPair, Long> map = initFreqencyMap(speedBins, directionBins);

    frequencyMap.forEach((key, val) -> map.put(key, val));
    this.frequencyMap = Collections.unmodifiableMap(map);
  }

  public long getFrequency(SpeedBin speedBin, DirectionBin directionBin) {
    return frequencyMap.getOrDefault(new BinPair(speedBin, directionBin), 0L);
  }

  public static LinkedHashMap<BinPair, Long> initFreqencyMap(List<SpeedBin> speedBins, List<DirectionBin> directionBins) {
    LinkedHashMap<BinPair, Long> map = new LinkedHashMap<>();
    for (SpeedBin sb : speedBins) {
      for (DirectionBin db : directionBins) {
        map.put(new BinPair(sb, db), 0L);
      }
    }
    return map;
  } 
  
}
