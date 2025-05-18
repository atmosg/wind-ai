package com.atmosg.windai.dto;

public record DirectionBin(double startDegInclusive, double endDegExclusive, String label) {

  public DirectionBin {
    if (startDegInclusive < 0 || endDegExclusive < 0
        || startDegInclusive >= 360 || endDegExclusive > 360) {
      throw new IllegalArgumentException("Direction degrees must be within [0, 360): "
          + "start=" + startDegInclusive
          + ", end=" + endDegExclusive);
    }
    // end > start
    if (endDegExclusive <= startDegInclusive) {
      throw new IllegalArgumentException("endDegExclusive must be greater than startDegInclusive: "
          + "start=" + startDegInclusive
          + ", end=" + endDegExclusive);
    }

    if (label==null || label.isBlank()) {
      throw new IllegalArgumentException("label can't be null and blank.");
    }
  }

  public boolean contains(double deg) {
    return deg >= startDegInclusive && deg < endDegExclusive;
  }

}
