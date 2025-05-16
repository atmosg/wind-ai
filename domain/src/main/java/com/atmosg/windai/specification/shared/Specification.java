package com.atmosg.windai.specification.shared;

public interface Specification<T> {
  
  boolean isSatisfiedBy(T t);

  Specification<T> and(Specification<T> other);

}
