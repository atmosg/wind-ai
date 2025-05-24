package com.atmosg.windai.time;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.mockito.MockitoAnnotations;

import com.atmosg.windai.TestData;
import com.atmosg.windai.ports.input.MetarObservationTimeQueryInputPort;
import com.atmosg.windai.unit.LengthUnit;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class VisibilityFind extends TestData {

  List<ZonedDateTime> resultTimes;

  public VisibilityFind() {
    init();
  }

  void init() {
    MockitoAnnotations.openMocks(this);
    loadData();
    this.timeUseCase = new MetarObservationTimeQueryInputPort(metarOutputPort);
    when(metarOutputPort.retrieveMetars(period))
      .thenReturn(metarList);
  }

  @Given("조회 기간에 대응하는 메타 리스트를 갖고있다")
  public void 메타리스트가_존재한다() {
  }

  @And("시정이 임계치 이하인 메타를 조회한다")
  public void 시정이_임계치_이하인_메타의_시간을_조회한다() {
    resultTimes = timeUseCase.findTimeWhenVisibilityAtMost(period, 10, LengthUnit.MILE);
  }

  @Then("조건에 맞는 메타의 관측시간을 리스트로 반환한다")
  public void 반환확인() {
    List<ZonedDateTime> expectedTimes = List.of(ZonedDateTime.of(LocalDateTime.of(
        2025, 5, 3, 9, 53), ZoneId.of("UTC")));

    assertEquals(expectedTimes, resultTimes);
  }

}
