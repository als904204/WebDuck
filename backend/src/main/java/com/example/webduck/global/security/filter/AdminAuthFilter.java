package com.example.webduck.global.security.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class AdminAuthFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/v1/admin")) {

            log.info("admin filter");
            String clientIp = getClientProxyIP(request);
            log.info("IP={}",clientIp);
            log.info("REQUEST URI={}",request.getRequestURI());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                OAuth2User principal = (OAuth2User) authentication.getPrincipal();
                if (principal.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {

                    log.info("Successfully completed Admin authentication.");

                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("not found");
                    return;
                }
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }

    public String getClientProxyIP(HttpServletRequest req) {
        String clientIP = req.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()
            || "unknown".equalsIgnoreCase(clientIP)) {
            clientIP = req.getHeader("X-Real-IP");
        }
        if (clientIP == null || clientIP.isEmpty()
            || "unknown".equalsIgnoreCase(clientIP)) {
            clientIP = req.getRemoteAddr();
        }
        return clientIP;
    }
}
