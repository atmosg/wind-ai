package com.atmosg.windai.vo.metar.field;

import java.util.List;

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

}