package com.atmosg.windai.vo.metar.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WindDirectionType {
  FIXED("FIX"),
  VARIABLE("VRB");

  private final String symbol;

  public String getSymbol() {
    return symbol;
  }

}
