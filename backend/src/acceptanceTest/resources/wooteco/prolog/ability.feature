@api
Feature: 역량 기능

  Background: 사전 작업
    Given "브라운"이 로그인을 하고
    And 부모역량 "프로그래밍"을 추가하고

  Scenario: 역량 추가 하기
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And 부모역량 "디자인"을 추가하고
    And "디자인"의 자식역량 "TDD"를 추가하고
    When 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: 부모 역량 조회 하기
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When 부모 역량 목록을 조회하면
    Then 부모 역량 목록을 받는다.

  Scenario: 사용자의 역량 정보 조회하기
    Given "브라운"이 로그인을 하고
    And 부모역량 "프로그래밍"을 추가하고
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When "브라운"의 역량 목록을 조회하면
    Then 역량 목록을 받는다.

  Scenario: 역량 수정 하기
    When "프로그래밍" 역량을 이름 "브라운", 설명 "피의 전사 브라운", 색상 "붉은 색" 으로 수정하면
    Then 이름 "브라운", 설명 "피의 전사 브라운", 색상 "붉은 색" 역량이 포함된 역량 목록을 받는다.

  Scenario: (부모) 역량 삭제 하기
    And "프로그래밍" 역량을 삭제하고
    When 역량 목록을 조회하면
    Then "프로그래밍" 역량이 포함되지 않은 목록을 받는다.

  Scenario: (자식) 역량 삭제 하기
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    And "언어" 역량을 삭제하고
    When 역량 목록을 조회하면
    Then "언어" 역량이 포함되지 않은 목록을 받는다.

  Scenario: 부모 역량이 자식 역량을 가진 경우 삭제 불가능
    And "프로그래밍"의 자식역량 "언어"를 추가하고
    When "프로그래밍" 역량을 삭제하면
    Then 삭제가 불가능하다는 예외가 발생한다.
