@WindRose
Feature: 월별 바람장미 생성

  Scenario: 월별 바람장미 생성 시 합계 검증
    Given 1년치 METAR 데이터를 보유하고 있다
    When 사용자가 월별 바람장미를 생성한다
    Then 모든 방향·속도 조합의 비율 합은 1200% 이어야 한다