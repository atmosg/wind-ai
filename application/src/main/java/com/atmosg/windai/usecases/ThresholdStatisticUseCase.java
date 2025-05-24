package com.atmosg.windai.usecases;

import com.atmosg.windai.dto.statistic.ObservationStatisticResponse;
import com.atmosg.windai.dto.statistic.ThresholdStatisticQuery;

public interface ThresholdStatisticUseCase {
  
  ObservationStatisticResponse execute(ThresholdStatisticQuery query);

}
