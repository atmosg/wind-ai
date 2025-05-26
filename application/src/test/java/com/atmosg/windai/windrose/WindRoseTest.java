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
  
  @Given("장기간 METAR 데이터 리스트를 보유하고 있다")
  public void 장기간_METAR_데이터_리스트를_보유하고_있다() {
    init();
  }

  @When("사용자가 바람장미 생성을 실행한다")
  public void 사용자가_바람장미_생성을_실행한다() {
    this.result = windRoseUsecase.generateMonthlyWindRose(period, speedBins, directionBins);
  }

  @Then("생성된 바람장미의 풍향_풍속별 비율의 총합은 100%이다")
  public void 생성된_바람장미의_풍향_풍속별_비율의_총합은_100프로이다() {
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
    when(metarOutputPort.retrieveMetars(period))
      .thenReturn(metarQuery);
  }

}
