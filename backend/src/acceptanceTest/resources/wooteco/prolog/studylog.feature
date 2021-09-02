@api
Feature: 스터디로그 관련 기능

  Background: 사전 작업
    Given 레벨 여러개를 생성하고
    And 미션 여러개를 생성하고
    And "브라운"이 로그인을 하고

  Scenario: 스터디로그 작성하기
    When 스터디로그를 작성하면
    Then 스터디로그가 작성된다

  Scenario: 스터디로그 수정하기
    Given 스터디로그 여러개를 작성하고
    When 1번째 스터디로그를 수정하면
    Then 1번째 스터디로그가 수정된다

  Scenario: 다른 계정으로 스터디로그 수정하기
    Given 스터디로그 여러개를 작성하고
    And "웨지"가 로그인을 하고
    When 1번째 스터디로그를 수정하면
    Then 에러 응답을 받는다

  Scenario: 스터디로그 목록 조회하기
    Given 스터디로그 여러개를 작성하고
    When 스터디로그 목록을 조회하면
    Then 스터디로그 목록을 받는다

  Scenario: 스터디로그 목록 페이지 조회하기
    Given 70개의 스터디로그를 작성하고
    When 10개, 2쪽의 페이지를 조회하면
    Then 10개의 스터디로그 목록을 받는다

  Scenario: 조건별 목록 페이지 조회하기 - 미션
    Given 1번 미션의 스터디로그를 9개 작성하고
    Given 2번 미션의 스터디로그를 11개 작성하고
    When 1번 미션의 스터디로그를 조회하면
    Then 9개의 스터디로그 목록을 받는다

  Scenario: 조건별 스터디로그 목록 페이지 조회하기 - 태그
    Given 1번 태그의 스터디로그를 13개 작성하고
    Given 3번 태그의 스터디로그를 7개 작성하고
    When 1번 태그의 스터디로그를 조회하면
    Then 13개의 스터디로그 목록을 받는다

  Scenario: 조건별 스터디로그 목록 페이지 조회하기 - 태그 + 미션1
    Given 서로 다른 태그와 미션을 가진 스터디로그를 다수 생성하고
    When 1번 미션과 5번 태그로 조회하면
    Then 6개의 스터디로그 목록을 받는다

  Scenario: 조건별 스터디로그 목록 페이지 조회하기 - 태그 + 미션2
    Given 서로 다른 태그와 미션을 가진 스터디로그를 다수 생성하고
    When 2번 미션과 3번 태그로 조회하면
    Then 5개의 스터디로그 목록을 받는다

  Scenario: 조건별 스터디로그 목록 페이지 조회하기 - 태그 + 미션3
    Given 서로 다른 태그와 미션을 가진 스터디로그를 다수 생성하고
    When 1번 미션과 1번 태그로 조회하면
    Then 13개의 스터디로그 목록을 받는다

  Scenario: 스터디로그 단건 조회하기
    Given 스터디로그 여러개를 작성하고
    When 1번째 스터디로그를 조회하면
    Then 1번째 스터디로그가 조회된다

  Scenario: 스터디로그 삭제하기
    * 스터디로그 여러개를 작성하고
    * 1번째 스터디로그를 삭제하면
    * 스터디로그가 삭제된다
