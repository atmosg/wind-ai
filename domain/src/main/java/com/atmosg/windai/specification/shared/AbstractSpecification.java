package com.atmosg.windai.specification.shared;

import com.atmosg.windai.exception.GenericSpecificationExeception;

public abstract class AbstractSpecification<T> implements Specification<T> {

    public abstract boolean isSatisfiedBy(T t);
    
    public abstract void check(T t) throws GenericSpecificationExeception;

    public Specification<T> and(Specification<T> other) {
      return new AndSpecification<T>(this, other);
    }
  
}
