@api
Feature: 댓글 관련 기능

  Background: 사전 작업
    Given "브라운"이 로그인을 하고
    And 세션 여러개를 생성하고
    And 미션 여러개를 생성하고
    And 스터디로그를 작성하면

  Scenario: 댓글 작성하기
    When 1번 스터디로그에 대한 댓글을 작성하면
    Then 댓글이 작성된다
