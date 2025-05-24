package com.atmosg.windai.model;

import java.util.function.BiPredicate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Comparison {

  LT((v,t) -> v < t),
  LTE((v,t) -> v <= t),
  EQ((v,t) -> v == t),
  NE((v,t) -> v != t),
  GT((v,t) -> v > t),
  GTE((v,t) -> v >= t);

  private final BiPredicate<Double, Double> op;

  public boolean test(double value, double threshold) {
    return op.test(value, threshold);
  }

}
