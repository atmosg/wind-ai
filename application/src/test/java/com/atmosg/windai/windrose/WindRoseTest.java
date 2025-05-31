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

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WindRoseTest extends TestData {

  private Map<Month, WindRose> result;
  
  @Given("1년치 METAR 데이터를 보유하고 있다")
  public void METAR데이터_로드() {
    init();
  }

  @When("사용자가 월별 바람장미를 생성한다")
  public void 생성_월별_바람장미() {
    this.result = windRoseUsecase.generateMonthlyWindRose("RKSI", period, speedBins, directionBins);
  }

  @Then("모든 방향·속도 조합의 비율 합은 1200% 이어야 한다")
  public void 검증_바람장미합계() {
    List<BinPair> binPairs = speedBins.stream()
      .flatMap(sb -> directionBins.stream()
        .map(db -> new WindRose.BinPair(sb, db)))
      .toList();

    
    double actual = binPairs.stream()
      .flatMap(bp -> result.values().stream()
        .map(wr -> wr.getRate(bp.speedBin(), bp.directionBin())))
      .mapToDouble(d -> d)
      .sum();
    
    double expect = 1200.0;
    assertEquals(expect, actual, 0.0001);
  }

  void init() {
    MockitoAnnotations.openMocks(this);
    loadData(2019, 1);
    
    this.windRoseUsecase = new WindRoseInputPort(metarOutputPort);
    when(metarOutputPort.retrieveMetars("RKSI", period))
      .thenReturn(metarQuery);
  }

}
