package com.atmosg.windai.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

public record MetarRetrievalPeriod(String icao, ZonedDateTime from, ZonedDateTime to) {
  
  public MetarRetrievalPeriod {
    if (icao.length() != 4) {
      throw new IllegalArgumentException("ICAO code must be 4 letter.");
    }

    Objects.requireNonNull(from, "Time must not be null");
    Objects.requireNonNull(to, "Time must not be null");

    if (!from.isBefore(to)) {
      throw new IllegalArgumentException("Start time must be before end time");
    }
  }

}
