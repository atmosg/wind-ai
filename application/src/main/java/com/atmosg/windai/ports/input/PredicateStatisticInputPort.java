package com.atmosg.windai.ports.input;

import java.util.List;
import java.util.function.Predicate;

import com.atmosg.windai.dto.statistic.ObservationStatisticResponse;
import com.atmosg.windai.dto.statistic.PreciateStatisticQuery;
import com.atmosg.windai.model.PredicateCondition;
import com.atmosg.windai.ports.input.internal.ObservationStatisticAggregator;
import com.atmosg.windai.ports.output.MetarManagementOutputPort;
import com.atmosg.windai.usecases.PredicateStatisticUseCase;
import com.atmosg.windai.vo.metar.Metar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PredicateStatisticInputPort implements PredicateStatisticUseCase {

  private final MetarManagementOutputPort metarOutputPort;

  @Override
  public ObservationStatisticResponse execute(PreciateStatisticQuery query) {
    List<Metar> metarList = metarOutputPort.retrieveMetars(query.icao(), query.period());
    
    PredicateCondition condition = query.condition();
    Predicate<Metar> predicate = m -> condition.field().test(m, condition.targeList());

    return ObservationStatisticAggregator.aggregate(metarList, predicate);
  }

}
