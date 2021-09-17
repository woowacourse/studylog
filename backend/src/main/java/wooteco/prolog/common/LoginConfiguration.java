package wooteco.prolog.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.application.OAuth2AccessTokenResponseClient;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.security.AuthenticationFailureHandler;
import wooteco.prolog.security.AuthenticationFilter;
import wooteco.prolog.security.AuthenticationProvider;
import wooteco.prolog.security.AuthenticationSuccessHandler;
import wooteco.prolog.security.OAuth2AccessTokenResponse;

@Configuration
@AllArgsConstructor
public class LoginConfiguration implements WebMvcConfigurer {

    private final OAuth2AccessTokenResponseClient tokenResponseClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(1);
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(2);
        registrationBean.setFilter(
            new AuthenticationFilter(authenticationProvider(), successHandler(), failureHandler()));
        registrationBean.addUrlPatterns("/login/token");
        return registrationBean;
    }

    private AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider(tokenResponseClient);
    }

    private AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            OAuth2AccessTokenResponse oAuth2AccessTokenResponse = (OAuth2AccessTokenResponse) authentication;
            GithubProfileResponse githubProfile = tokenResponseClient
                .getGithubProfileFromGithub((String) oAuth2AccessTokenResponse.getPrincipal());

            Member member = memberService.findOrCreateMember(githubProfile);
            String accessToken = jwtTokenProvider.createToken(member);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter()
                .write(new ObjectMapper().writeValueAsString(TokenResponse.of(accessToken)));
        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            throw exception;
        };
    }
}
