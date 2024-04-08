package com.example.webduck.auth.controller;


import com.example.webduck.auth.controller.port.AuthService;
import com.example.webduck.auth.controller.response.AuthResponse;
import com.example.webduck.global.security.oauth.dto.LoginMember;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import javax.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;

    @GetMapping("/status")
    public ResponseEntity<Boolean> getAuthStatus() {
        return ResponseEntity.ok(isAuthenticated());
    }

    @GetMapping("/csrf")
    public ResponseEntity<String> getOrCreateCsrfToken(
        @NonNull HttpServletRequest request) {
        final CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return ResponseEntity.ok().header(csrfToken.getHeaderName(), csrfToken.getToken())
            .body(csrfToken.getToken());
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me(@LoginMember SessionMember sessionMember) {
        AuthResponse response = AuthResponse.from(authService.me(sessionMember));
        return ResponseEntity.ok(response);
    }

    private Boolean isAuthenticated() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
