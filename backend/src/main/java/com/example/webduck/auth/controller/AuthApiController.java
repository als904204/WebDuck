package com.example.webduck.auth.controller;


import javax.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthApiController {

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

    private Boolean isAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
