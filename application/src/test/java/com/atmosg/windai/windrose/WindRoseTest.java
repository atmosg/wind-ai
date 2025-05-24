package com.atmosg.windai.windrose;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import com.atmosg.windai.TestData;
import com.atmosg.windai.dto.windrose.DirectionBin;
import com.atmosg.windai.dto.windrose.SpeedBin;
import com.atmosg.windai.dto.windrose.WindRose;
import com.atmosg.windai.dto.windrose.WindRose.BinPair;
import com.atmosg.windai.ports.input.WindRoseInputPort;
import com.atmosg.windai.unit.SpeedUnit;
import com.atmosg.windai.vo.metar.Metar;

public class WindRoseTest extends TestData {
  
  public WindRoseTest() {
    init();
  }
  
  void init() {
    MockitoAnnotations.openMocks(this);
    loadData(2019, 1);
    
    this.windRoseUsecase = new WindRoseInputPort(metarOutputPort);
    when(metarOutputPort.retrieveMetars(period))
      .thenReturn(metarQuery);
  }

  @Test
  public void 바람장미_생성에_성공해야한다() {
    Map<Month, WindRose> monthlyWindRose = windRoseUsecase.generateMonthlyWindRose(period, speedBins, directionBins);

    List<BinPair> binPairs = speedBins.stream()
      .flatMap(sb -> directionBins.stream()
        .map(db -> new WindRose.BinPair(sb, db)))
      .toList();

    
    double actual = binPairs.stream()
      .flatMap(bp -> monthlyWindRose.values().stream()
        .map(wr -> wr.getRate(bp.speedBin(), bp.directionBin())))
      .mapToDouble(d -> d)
      .sum();
    
    double expect = 1200.0;
    assertEquals(expect, actual, 0.1);

  }

}
