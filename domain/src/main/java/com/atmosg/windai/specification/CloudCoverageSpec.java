package com.atmosg.windai.specification;

import com.atmosg.windai.exception.GenericSpecificationExeception;
import com.atmosg.windai.specification.shared.AbstractSpecification;
import com.atmosg.windai.vo.metar.field.Cloud;

public class CloudCoverageSpec extends AbstractSpecification<Cloud> {
  
  @Override
  public boolean isSatisfiedBy(Cloud cloud) {
    boolean requires = cloud.getCoverage().requiresAltitude();
    boolean hasAltitude = cloud.getAltitudeOptional().isPresent();

    return requires ? hasAltitude : !hasAltitude;
  }

  @Override
  public void check(Cloud cloud) throws GenericSpecificationExeception {
    if (!isSatisfiedBy(cloud)) {
      handleViolation(cloud);
    }
  }

  private void handleViolation(Cloud cloud) {
    boolean requires = cloud.getCoverage().requiresAltitude();
    boolean hasAltitude = cloud.getAltitudeOptional().isPresent();
    
    if (requires && !hasAltitude) {
      throw new GenericSpecificationExeception(cloud.getCoverage() + " requires altitude.");
    }

    if (!requires && hasAltitude) {
      throw new GenericSpecificationExeception(cloud.getCoverage() + " has no fixed altitude.");
    }
  }

}