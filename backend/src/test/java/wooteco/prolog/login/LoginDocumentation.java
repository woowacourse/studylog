package wooteco.prolog.login;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class LoginDocumentation extends Documentation {

    @Test
    void create() {

        String code = "1234567890qazwsxedcrfvtgbyhnujmiklop";
        TokenRequest params = new TokenRequest(code);

        RestAssured
                .given(spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(document("login/token",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .body(params)
                .when().post("/login/token")
                .then().log().all()
                .extract().as(TokenResponse.class);
    }

    @Test
    void findMember() {
        RestAssured
                .given(spec).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .filter(document("members/me",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .when().get("/members/me")
                .then().log().all()
                .extract();
    }
}
