package com.atmosg.windai.specification;

import com.atmosg.windai.exception.GenericSpecificationExeception;
import com.atmosg.windai.specification.shared.AbstractSpecification;

public class DirectionRangeSpec extends AbstractSpecification<Integer> {

  @Override
  public boolean isSatisfiedBy(Integer direction) {
    return direction >= 0 && direction <= 360;
  }

  @Override
  public void check(Integer direction) throws GenericSpecificationExeception {
    if (!isSatisfiedBy(direction)) {
      throw new GenericSpecificationExeception("Direction must be between 0 and 360 degrees.");
    }
  }

}
