package com.atmosg.windai.ports.input.internal;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.atmosg.windai.dto.statistic.HourlyCountDto;
import com.atmosg.windai.dto.statistic.MonthlyCountDto;
import com.atmosg.windai.dto.statistic.ObservationStatisticResponse;
import com.atmosg.windai.vo.metar.Metar;

public final class ObservationStatisticAggregator {
  
  public static ObservationStatisticResponse aggregate(List<Metar> metars, Predicate<Metar> predicate) {
    Stream<Metar> filtered = metars.stream().filter(predicate);

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
            .thenComparing(HourlyCountDto::hourUtc))
        .toList();

    return new ObservationStatisticResponse(monthly, hourly);
  }

}
