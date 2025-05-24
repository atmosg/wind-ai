package com.atmosg.windai.ports.input;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.atmosg.windai.dto.statistic.HourlyCountDto;
import com.atmosg.windai.dto.statistic.MonthlyCountDto;
import com.atmosg.windai.dto.statistic.ObservationStatisticResponse;
import com.atmosg.windai.dto.statistic.ThresholdStatisticQuery;
import com.atmosg.windai.model.ThresholdCondition;
import com.atmosg.windai.ports.output.MetarManagementOutputPort;
import com.atmosg.windai.usecases.ThresholdStatisticUseCase;
import com.atmosg.windai.vo.metar.Metar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThresholdStatisticInputPort implements ThresholdStatisticUseCase {

  private final MetarManagementOutputPort metarOutputPort;

  @Override
  public ObservationStatisticResponse execute(ThresholdStatisticQuery query) {
    List<Metar> metarList = metarOutputPort.retrieveMetars(query.period());
    
    Stream<Metar> filtered = filterWithCondition(metarList, query.condition());

    Map<YearMonth, Set<LocalDate>> monthSet = new HashMap<>();
    Map<YearMonth, Map<Integer, Set<LocalDate>>> hourSet = new HashMap<>(); 

    filtered.forEach(m -> {
      ZonedDateTime utc = m.getObservationTime();
      YearMonth ym = YearMonth.from(utc);
      LocalDate day = utc.toLocalDate();
      int hr = utc.getHour();

      monthSet
        .computeIfAbsent(ym, k -> new HashSet<>())
        .add(day);

      hourSet
        .computeIfAbsent(ym, k -> new HashMap<>())
        .computeIfAbsent(hr, k -> new HashSet<>())
        .add(day);
    });
  
    List<MonthlyCountDto> monthly = monthSet.entrySet().stream()
      .map(e -> new MonthlyCountDto(e.getKey(), e.getValue().size()))
      .sorted(Comparator.comparing(MonthlyCountDto::YearMonth))
      .toList();

    List<HourlyCountDto> hourly = hourSet.entrySet().stream()
      .flatMap(e -> e.getValue().entrySet().stream()
        .map(hr -> new HourlyCountDto(e.getKey(), hr.getKey(), hr.getValue().size())))
      .sorted(Comparator
        .comparing(HourlyCountDto::yearMonth)
        .thenComparing(HourlyCountDto::hourUtc)
      ).toList();

    return new ObservationStatisticResponse(monthly, hourly);
  }

  private Stream<Metar> filterWithCondition(List<Metar> metarList, ThresholdCondition condition) {
    return metarList.stream().filter(m -> {
      double value = condition.field().extract(m, condition.unit());
      return condition.comparison().test(value, condition.threshold());
    });
  }

  
  
}
