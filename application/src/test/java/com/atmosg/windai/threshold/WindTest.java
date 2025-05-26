package com.atmosg.windai.threshold;

import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;

import com.atmosg.windai.TestData;
import com.atmosg.windai.dto.statistic.ObservationStatisticResponse;
import com.atmosg.windai.dto.statistic.ThresholdStatisticQuery;
import com.atmosg.windai.model.Comparison;
import com.atmosg.windai.model.MetarField;
import com.atmosg.windai.model.ThresholdCondition;
import com.atmosg.windai.ports.input.ThresholdStatisticInputPort;
import com.atmosg.windai.ports.input.WindRoseInputPort;
import com.atmosg.windai.unit.SpeedUnit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WindTest extends TestData {

  private ObservationStatisticResponse result;

  @Given("장기간 METAR 데이터 리스트를 보유하고 있다")
  public void 장기간_METAR_데이터_로드() {
    init();
  }

  @When("사용자가 최대풍속 임계치 이하 날의 통계를 생성한다")
  public void 생성_최대풍속_임계치_이하_날의_통계() {
    this.result = thresholdStatisticUseCase.execute(
      new ThresholdStatisticQuery(period, new ThresholdCondition(
        MetarField.PEAK_WIND,
        Comparison.GTE,
        25.0,
        SpeedUnit.KT
      )));
  }

  @Then("생성된 통계의 모든 최대풍속 값은 임계치 이하이어야 한다")
  public void 검증_최대풍속_임계치_이하() {
    System.out.println(result.monthly().size());
  }


  void init() {
    MockitoAnnotations.openMocks(this);
    loadData(2019, 1);
    this.thresholdStatisticUseCase = new ThresholdStatisticInputPort(metarOutputPort);
    when(metarOutputPort.retrieveMetars(period))
      .thenReturn(metarQuery);
  }
  
}
