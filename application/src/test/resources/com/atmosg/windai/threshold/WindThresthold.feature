@WindThreshold
Feature: 풍속 임계치 통계

  Scenario: 최대풍속 임계치 이하인 날 통계 생성
    Given 장기간 METAR 데이터 리스트를 보유하고 있다
    When 사용자가 최대풍속 임계치 이하 날의 통계를 생성한다
    Then 생성된 통계의 모든 최대풍속 값은 임계치 이하이어야 한다