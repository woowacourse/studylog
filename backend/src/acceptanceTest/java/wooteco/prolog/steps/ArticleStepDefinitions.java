package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.fixtures.ArticleFixture.ARTICLE_REQUEST1;
import static wooteco.prolog.fixtures.ArticleFixture.ARTICLE_REQUEST2;
import static wooteco.prolog.fixtures.ArticleFixture.ARTICLE_URL_REQUEST;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.article.ui.ArticleResponse;
import wooteco.prolog.article.ui.ArticleUrlResponse;


public class ArticleStepDefinitions extends AcceptanceSteps {

    @Given("아티클을 여러개 작성하고")
    public void 아티클을_여러개_작성하고() {
        context.invokeHttpPostWithToken("/articles", ARTICLE_REQUEST1);
        context.invokeHttpPostWithToken("/articles", ARTICLE_REQUEST2);
    }

    @Given("아티클을 작성하고")
    @When("아티클을 작성하면")
    public void 아티클을_작성하면() {
        context.invokeHttpPostWithToken("/articles", ARTICLE_REQUEST1);
        if (context.response.statusCode() == HttpStatus.CREATED.value()) {
            context.storage.put("article", context.response.statusCode());
        }
    }

    @When("아티클을 전체 조회 하면")
    public void 아티클을_전체_조회_하면() {
        context.invokeHttpGetWithToken("/articles");
        context.storage.put("articles", context.response.as(Object.class));
    }

    @When("{long}번 아티클을 수정하면")
    public void 아티클을_수정하면(Long articleId) {
        String path = "/articles/" + articleId;
        context.invokeHttpPutWithToken(path, ARTICLE_REQUEST2);
    }

    @When("{long}번 아티클을 삭제하면")
    public void 아티클을_삭제하면(Long articleId) {
        String path = "/articles/" + articleId;
        context.invokeHttpDeleteWithToken(path, ARTICLE_REQUEST2);
    }

    @When("Url을 입력하면")
    public void Url을_입력하면() {
        final String path = "/articles/parse-url";
        context.invokeHttpPostWithToken(path, ARTICLE_URL_REQUEST);
    }

    @Then("아티클이 작성된다")
    public void 아티클이_작성된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @Then("아티클이 전체 조회 된다")
    public void 아티클이_전체_조회된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());

        List<ArticleResponse> articleResponses = (List<ArticleResponse>) context.storage.get(
            "articles");
        assertThat(articleResponses.size()).isEqualTo(2);
    }

    @Then("아티클이 수정된다")
    public void 아티클이_수정된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Then("아티클이 삭제된다")
    public void 아티클이_삭제된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("og태그를 파싱해서 반환한다.")
    public void 아티클을_파싱해서_반환한다() {
        final ArticleUrlResponse actual = context.response
            .as(ArticleUrlResponse.class);
        final ArticleUrlResponse expected = new ArticleUrlResponse(
            "우아한형제들",
            "https://woowahan-cdn.woowahan.com/static/image/share_kor.jpg"
        );

        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
