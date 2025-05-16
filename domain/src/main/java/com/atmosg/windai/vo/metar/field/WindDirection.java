package com.atmosg.windai.vo.metar.field;

import java.util.Optional;

import com.atmosg.windai.vo.metar.type.WindDirectionType;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class WindDirection {
  
  private final WindDirectionType type;
  private final Double degree;

  private WindDirection(WindDirectionType type, Double degree) {
    this.type = type;
    this.degree = degree;
  }

  public static WindDirection fixed(double degree) {
    return new WindDirection(WindDirectionType.FIXED, degree);
  }

  public static WindDirection variable() {
    return new WindDirection(WindDirectionType.VARIABLE, null);
  }

  public double getDegreeOrThrow() {
    if (isVariable()) {
      throw new IllegalStateException("Variable wind has no fixed direction.");
    }
    return degree;
  }

  public Optional<Double> getDegreeOptional() {
    return Optional.ofNullable(degree);
  }
  
  public boolean isBetween(double lower, double upper) {
    double deg = getDegreeOrThrow();
    return deg <= upper && deg >= lower;
  }
  
  public boolean isVariable() {
    return type == WindDirectionType.VARIABLE;
  }

}
