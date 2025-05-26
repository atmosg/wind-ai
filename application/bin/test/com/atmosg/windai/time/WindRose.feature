@VisibilityFind
Feature: 바람장미 생산

Background:
  Given 장기간 METAR 데이터 리스트를 보유하고 있다

  Scenario: 바람장미를 생성하면 유효한 데이터가 포함된다
    When 사용자가 바람장미 생성을 실행한다
    Then 생성된 바람장미의 풍향_풍속별 비율의 총합은 100%이다

