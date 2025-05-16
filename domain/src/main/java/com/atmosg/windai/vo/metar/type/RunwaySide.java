package com.atmosg.windai.vo.metar.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RunwaySide {
  LEFT("L"),
  RIGHT("R"),
  CNETER("C"),
  NONE("N");

  private final String code;

  public String getCode() {
    return code;
  }

  public static RunwaySide fromCode(String code) {
    for (RunwaySide side : RunwaySide.values()) {
      if (side.getCode().equalsIgnoreCase(code)) return side;
    }
    throw new IllegalArgumentException("Invalid runway side code: " + code);
  }
}
