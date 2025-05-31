package com.atmosg.windai.ports.input;

import java.util.List;
import java.util.function.Predicate;

import com.atmosg.windai.dto.statistic.ObservationStatisticResponse;
import com.atmosg.windai.dto.statistic.ThresholdStatisticQuery;
import com.atmosg.windai.model.ThresholdCondition;
import com.atmosg.windai.ports.input.internal.ObservationStatisticAggregator;
import com.atmosg.windai.ports.output.MetarManagementOutputPort;
import com.atmosg.windai.usecases.ThresholdStatisticUseCase;
import com.atmosg.windai.vo.metar.Metar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThresholdStatisticInputPort implements ThresholdStatisticUseCase {

  private final MetarManagementOutputPort metarOutputPort;

  @Override
  public ObservationStatisticResponse execute(ThresholdStatisticQuery query) {
    List<Metar> metarList = metarOutputPort.retrieveMetars(query.icao(), query.period());
    
    ThresholdCondition condition = query.condition();
    Predicate<Metar> predicate = m -> {
      double value = condition.field().extract(m, condition.unit());
      return condition.comparison().test(value, condition.threshold());
    };

    return ObservationStatisticAggregator.aggregate(metarList, predicate);
  }

}
