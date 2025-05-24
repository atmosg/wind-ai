package com.atmosg.windai.dto.windrose;

import java.util.Objects;

import com.atmosg.windai.unit.SpeedUnit;

public record SpeedBin(int lowerInclusive, int upperExclusive, SpeedUnit unit, String label) {

  public SpeedBin {
    if (lowerInclusive < 0 || upperExclusive < 0) {
      throw new IllegalArgumentException("Speed bounds must be non-negative : "
        + "lowerInclusive=" + lowerInclusive
        + ", upperExclusive=" + upperExclusive
      );
    }

    if (upperExclusive <= lowerInclusive) {
      throw new IllegalArgumentException("upperExclusive must be greater than lowerInclusive: "
          + "lowerInclusive=" + lowerInclusive
          + ", upperExclusive=" + upperExclusive);
    }

    Objects.requireNonNull(unit, "unit must not be null");
    
    if (label==null || label.isBlank()) {
      throw new IllegalArgumentException("label can't be null and blank.");
    }
  }
  
}
