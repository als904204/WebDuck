package com.example.webduck.config.security;

import static com.example.webduck.Member.entity.Role.ADMIN;
import static com.example.webduck.Member.entity.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.Customizer.withDefaults;

import com.example.webduck.config.security.oauth.handler.OAuth2LoginFailureHandler;
import com.example.webduck.config.security.oauth.handler.OAuth2LoginSuccessHandler;
import com.example.webduck.config.security.oauth.service.CustomOAuth2UserService;
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
        "/auth/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((req) ->
                req.requestMatchers(WHITE_LIST_URL)
                    .permitAll()
                    .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                    .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN.name(), MANAGER.name())
                    .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN.name(), MANAGER.name())
                    .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN.name(), MANAGER.name())
                    .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN.name(), MANAGER.name())
                    .anyRequest()
                    .authenticated()
            )

            .httpBasic((AbstractHttpConfigurer::disable))
            .formLogin((AbstractHttpConfigurer::disable))
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
                .userInfoEndpoint(point -> point
                    .userService(customOAuth2UserService))
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandle)
            );

        return http.build();
    }
}
