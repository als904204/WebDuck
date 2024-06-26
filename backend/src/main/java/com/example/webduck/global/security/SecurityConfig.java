package com.example.webduck.global.security;

import static com.example.webduck.member.infrastructure.Role.ADMIN;
import static com.example.webduck.member.infrastructure.Role.USER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.example.webduck.global.security.oauth.handler.OAuth2LoginFailureHandler;
import com.example.webduck.global.security.oauth.handler.OAuth2LoginSuccessHandler;
import com.example.webduck.global.security.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandle;


    @Value("${index.page}")
    private String indexPage;

    private static final String[] WHITE_LIST_URL = {
        "/",
        "/css/**",
        "/img/**",
        "/js/**",
        "/auth/login",
        "/images/**",
        "/publish/**",
        "/genre/**",
        "/naver/**",
        "/kakao/**",
        "/webtoon/details/**",
        "/api/v1/dev/**",
        "/docs/**"
    };


    private static final String[] WHITE_GET_API_LIST_URL = {
        "/api/v1/webtoon/**",
        "/api/v1/genre/**",
        "/api/v1/review/**",
        "/api/v1/auth/status",
        "/api/v1/collection/**",
        "/api/v1/health/**"
    };

    private static final String[] POST_API_LIST_URL = {
        "/api/v1/review/**",
    };
    private static final String[] DELETE_API_LIST_URL = {
        "/api/v1/review/**",
    };
    private static final String[] GET_API_LIST_URL = {
        "/api/v1/auth/**",
    };
    private static final String[] ADMIN_URL = {
        "/api/v1/admin/**",
        "/admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((req) ->
                req
                    .antMatchers(WHITE_LIST_URL)
                    .permitAll()
                    .antMatchers(GET, WHITE_GET_API_LIST_URL)
                    .permitAll()

                    .antMatchers(POST, POST_API_LIST_URL)
                    .hasAnyRole(ADMIN.name(), USER.name())
                    .antMatchers(GET, GET_API_LIST_URL)
                    .hasAnyRole(USER.name(), ADMIN.name())
                    .antMatchers(DELETE, DELETE_API_LIST_URL)
                    .hasAnyRole(USER.name(), ADMIN.name())
                    .antMatchers(ADMIN_URL)
                    .hasRole(ADMIN.name())

                    .anyRequest()
                    .authenticated()
            )
            .csrf(csrf -> csrf
                .csrfTokenRepository(sessionCsrfTokenRepository()))

            .httpBasic((AbstractHttpConfigurer::disable))
            .formLogin((AbstractHttpConfigurer::disable))
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .userInfoEndpoint(point -> point // OAuth 액세스 토큰,외부 사용자 정보 처리하는 지점
                    .userService(customOAuth2UserService))
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandle)
            )
            .sessionManagement(session -> session
                .maximumSessions(1) // 동시 세션 제한
                .expiredUrl("/") // 동시 로그인 시 기존로그인 한 사람 리다이렉트
            )
            .logout(logout -> logout
                .permitAll()
                .logoutUrl("/api/v1/auth/logout")
                .logoutRequestMatcher(
                    new AntPathRequestMatcher("/api/v1/auth/logout")) // 주소창에 입력해도 POST 로 처리
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true) // 로그아웃 시 세션 날리기
                .clearAuthentication(true)   // 시큐리티 컨텍스트 홀더 인증정보 날리기
                .logoutSuccessUrl(indexPage)
            );

        return http.build();
    }


    // 서버측 세션에서 csrf 토큰을 관리
    @Bean
    protected HttpSessionCsrfTokenRepository sessionCsrfTokenRepository() {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfTokenRepository.setHeaderName("X-CSRF-TOKEN");
        csrfTokenRepository.setParameterName("_csrf");
        csrfTokenRepository.setSessionAttributeName("CSRF_TOKEN");
        return csrfTokenRepository;
    }

}
