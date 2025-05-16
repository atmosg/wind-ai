package com.atmosg.windai.vo.metar.field;

import java.util.List;
import java.util.Optional;

import com.atmosg.windai.specification.CloudAltitudeSpec;
import com.atmosg.windai.specification.CloudCoverageSpec;
import com.atmosg.windai.vo.metar.type.CloudCoverage;
import com.atmosg.windai.vo.metar.type.CloudType;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Cloud {

  @Getter
  private final CloudCoverage coverage;

  private final Integer altitude;

  @Getter
  private final CloudType type;

  private static final CloudCoverageSpec coverageSpec = new CloudCoverageSpec();
  private static final CloudAltitudeSpec altitudeSpec = new CloudAltitudeSpec();

  @Builder
  public Cloud(CloudCoverage coverage, Integer altitude, CloudType type) {
    this.coverage = coverage;
    this.altitude = altitude;
    this.type = type;

    coverageSpec.check(this);
    altitudeSpec.check(this);
  }

  public static Cloud of(CloudCoverage coverage, Integer altitude, CloudType type) {
    return Cloud.builder()
        .coverage(coverage)
        .altitude(altitude)
        .type(type)
        .build();
  }

  public boolean isAltitudeAtMost(int threshold, List<CloudCoverage> targetCoverages) {
    for (CloudCoverage target : targetCoverages) {
      if (!target.requiresAltitude()) {
        throw new IllegalArgumentException(target + " has no fixed altitude.");
      }
    }

    if (!targetCoverages.contains(coverage)) {
      return false;
    }

    return getAltitudeOptional()
        .map((altitude) -> altitude <= threshold)
        .orElse(false);
  }

  public int getAltitudeOrThrow() {
    return getAltitudeOptional()
        .orElseThrow(() -> new IllegalStateException(coverage + " has no fixed altitude."));
  }

  public Optional<Integer> getAltitudeOptional() {
    return Optional.ofNullable(altitude);
  }

  public boolean hasCloudType() {
    return type != CloudType.NONE;
  }

}
