@VisibilityFind
Feature: 시정이 임계치 이하인 metar의 관측시간을 찾아낼 수 있는가?

  Scenario: 시정이 임계치 이하인 메타의 관측시간을 검색한다
    Given 조회 기간에 대응하는 메타 리스트를 갖고있다 
    And 시정이 임계치 이하인 메타를 조회한다
    Then 조건에 맞는 메타의 관측시간을 리스트로 반환한다

