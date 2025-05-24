package com.atmosg.windai.usecases;

import com.atmosg.windai.dto.statistic.ObservationStatisticResponse;
import com.atmosg.windai.dto.statistic.PreciateStatisticQuery;

public interface PredicateStatisticUseCase {
  
  ObservationStatisticResponse execute(PreciateStatisticQuery query);

}
