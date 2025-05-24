package com.atmosg.windai.vo.metar.field;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import com.atmosg.windai.vo.metar.type.CloudCoverage;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CloudGroup {
  
  private final List<Cloud> clouds;

  public CloudGroup(List<Cloud> clouds) {
    this.clouds = List.copyOf(clouds);
  }

  public static CloudGroup of(List<Cloud> clouds) {
    return CloudGroup.builder()
      .clouds(clouds)
      .build();
  }

  public int size() {
    return clouds.size();
  }

  public OptionalInt getLowestCeiling(List<CloudCoverage> coverages) {
    return clouds.stream()
      .filter(cloud -> coverages.contains(cloud.getCoverage()))
      .map(Cloud::getAltitudeOptional)
      .flatMap(Optional::stream)
      .mapToInt(Integer::intValue)
      .min();
  }

}