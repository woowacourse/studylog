package wooteco.prolog.docu;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.studylog.application.dto.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StudyLogDocumentation extends Documentation {

    @Test
    void 스터디로그를_생성한다() {
        // given
        StudylogRequest studylog = createStudylogRequest1();
        List<StudylogRequest> studylogRequest = Arrays.asList(studylog);

        // when
        ExtractableResponse<Response> createResponse = given("studylog/create")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(studylogRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/studylogs")
            .then().log().all().extract();

        // then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createResponse.header("Location")).isNotNull();
    }

    @Test
    void 스터디로그_단건을_조회한다() {
        // given
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(Arrays.asList(createStudylogRequest1()));
        String location = studylogResponse.header("Location");

        given("studylog/read")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get(location)
            .then().log().all().extract();
    }

    @Test
    void 스터디로그_목록을_조회한다() {
        // given
        List<StudylogRequest> studylogRequests = Arrays.asList(createStudylogRequest1(), createStudylogRequest2());
        스터디로그_등록함(studylogRequests);

        // when
        ExtractableResponse<Response> response = given("studylog/list")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/posts")
            .then().log().all().extract();

        // then
        StudylogsResponse studylogsResponse = response.as(StudylogsResponse.class);
        List<String> studylogsTitles = studylogsResponse.getData().stream()
            .map(StudylogResponse::getTitle)
            .collect(Collectors.toList());
        List<String> expectedTitles = studylogRequests.stream()
            .map(StudylogRequest::getTitle)
            .sorted()
            .collect(Collectors.toList());
        assertThat(studylogsTitles).usingRecursiveComparison().isEqualTo(expectedTitles);
    }

    @Disabled
    @Test
    void 스터디로그_목록을_검색_및_필터링한다() {
        // given
        스터디로그_등록함(Arrays.asList(createStudylogRequest1(), createStudylogRequest2()));

        // when
        ExtractableResponse<Response> response = given("studylog/filter")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get(
                "/posts?keyword=joanne&levels=1&missions=1&tags=1&tags=2&usernames=soulG&startDate=20210901&endDate=20211231")
            .then().log().all().extract();

        // given
        StudylogsResponse studylogsResponse = response.as(StudylogsResponse.class);
        assertThat(studylogsResponse.getData()).hasSize(1);
    }

    @Test
    void 스터디로그를_수정한다() {
        // given
        ExtractableResponse<Response> studylogResponses = 스터디로그_등록함(Arrays.asList(createStudylogRequest1()));
        String location = studylogResponses.header("Location");

        String title = "수정된 제목";
        String content = "수정된 내용";

        Long levelId = 레벨_등록함(new LevelRequest("레벨2"));
        Long missionId = 미션_등록함(new MissionRequest("수정된 미션", levelId));
        List<TagRequest> tags = Arrays.asList(
            new TagRequest("spa"),
            new TagRequest("edit")
        );
        StudylogRequest editStudylogRequest = new StudylogRequest(title, content, missionId, tags);

        // when
        ExtractableResponse<Response> editResponse = given("studylog/edit")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(editStudylogRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().put(location)
            .then().log().all()
            .extract();

        // then
        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 스터디로그를_삭제한다() {
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(Arrays.asList(createStudylogRequest1()));
        String location = studylogResponse.header("Location");

        given("studylog/delete")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().delete(location);
    }

    private StudylogRequest createStudylogRequest1() {
        String title = "나는야 Joanne";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long levelId = 레벨_등록함(new LevelRequest("레벨1"));
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new StudylogRequest(title, content, missionId, tags);
    }

    private StudylogRequest createStudylogRequest2() {
        String title = "JAVA";
        String content = "Spring Data JPA를 학습함.";
        Long levelId = 레벨_등록함(new LevelRequest("레벨3"));
        Long missionId = 미션_등록함(new MissionRequest("레벨3 - 프로젝트", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("java"), new TagRequest("jpa"));

        return new StudylogRequest(title, content, missionId, tags);
    }

    private ExtractableResponse<Response> 스터디로그_등록함(List<StudylogRequest> request) {
        return RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/posts")
            .then().log().all().extract();
    }

    private Long 레벨_등록함(LevelRequest request) {
        return RestAssured
            .given().log().all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/levels")
            .then()
            .log().all()
            .extract()
            .as(LevelResponse.class)
            .getId();
    }

    private Long 미션_등록함(MissionRequest request) {
        return RestAssured.given()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/missions")
            .then()
            .log().all()
            .extract()
            .as(MissionResponse.class)
            .getId();
    }
}
