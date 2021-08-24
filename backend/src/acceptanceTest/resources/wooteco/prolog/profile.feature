@api
Feature: 프로필 관련 기능
  Background: 사전 작업
    Given 레벨 여러개를 생성하고
    And 미션 여러개를 생성하고

  Scenario: 멤버 프로필 조회하기
    Given "브라운"이 로그인을 하고
    When "서니"이 로그인을 하고
    When "브라운"의 멤버 프로필을 조회하면
    Then 멤버 프로필이 조회된다

  Scenario: 멤버 프로필 포스트 조회하기
    Given "브라운"이 로그인을 하고
    And 포스트 여러개를 작성하고
    And "엘라"가 로그인을 하고
    When "브라운"의 프로필 포스트를 조회하면
    Then 프로필 포스트가 조회된다