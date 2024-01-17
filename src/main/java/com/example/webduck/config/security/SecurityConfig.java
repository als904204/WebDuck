package com.example.webduck.config.security;

import static com.example.webduck.member.entity.Role.ADMIN;
import static com.example.webduck.member.entity.Role.MANAGER;
import static com.example.webduck.member.entity.Role.USER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.example.webduck.config.security.oauth.handler.OAuth2LoginFailureHandler;
import com.example.webduck.config.security.oauth.handler.OAuth2LoginSuccessHandler;
import com.example.webduck.config.security.oauth.service.CustomOAuth2UserService;
import com.example.webduck.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandle;

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
        "/webtoon/details/**"

    };

    private static final String[] WHITE_GET_API_LIST_URL = {
        "/api/v1/webtoon/**",
        "/api/v1/genre/**",
        "/api/v1/review/**"
    };

    private static final String[] WHITE_POST_API_LIST_URL = {
        "/api/v1/review/**"
    };


    private static final String[] ADMIN_URL = {
        "/api/v1/admin/**",
        "/admin/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((req) ->
                req.requestMatchers(WHITE_LIST_URL)
                    .permitAll()
                    .requestMatchers(GET, WHITE_GET_API_LIST_URL)
                    .permitAll()
                    .requestMatchers(POST, WHITE_POST_API_LIST_URL)
                    .hasAnyRole(ADMIN.name(),USER.name(),MANAGER.name())
                    .requestMatchers(ADMIN_URL)
                    .hasRole(ADMIN.name())

                    // ex) 권한 세부 설정
                    .requestMatchers(GET, "/api/v1/management/**")
                    .hasAnyAuthority(ADMIN.name(), MANAGER.name())

                    .anyRequest()
                    .authenticated()
            )
//            .csrf((AbstractHttpConfigurer::disable))
//            .cors((AbstractHttpConfigurer::disable))

            .httpBasic((AbstractHttpConfigurer::disable))
            .formLogin((AbstractHttpConfigurer::disable))
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
                .userInfoEndpoint(point -> point // OAuth 액세스 토큰,외부 사용자 정보 처리하는 지점
                    .userService(customOAuth2UserService))
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandle)
            )
            .sessionManagement(session -> session
                .maximumSessions(1) // 동시 세션 제한
                .expiredUrl("/session-expired") // 동시 로그인 시 기존로그인 한 사람 리다이렉트

            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true) // 로그아웃 시 세션 날리기
                .clearAuthentication(true)   // 시큐리티 컨텍스트 홀더 인증정보 날리기
                .logoutSuccessUrl("/")

            );



        return http.build();
    }
}
