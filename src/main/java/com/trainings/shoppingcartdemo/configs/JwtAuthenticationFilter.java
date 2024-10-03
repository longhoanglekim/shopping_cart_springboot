package com.trainings.shoppingcartdemo.configs;

import com.trainings.shoppingcartdemo.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final List<String> publicUrls = List.of(
            "/auth/**", "/login", "/welcome", "/register", "/WEB-INF/**", "/css/**", "/js/**", "/image/**",
            "/api/**", "/showListProduct/**", "/productInfo", "/search"
    );

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String token = resolveToken(request);

        // Nếu token tồn tại và đã hết hạn, xóa cookie
        if (token != null) {
            try {
                if (!jwtService.isTokenValid(token)) {
                    clearJwtTokenCookie(response);
                }
            } catch (ExpiredJwtException e) {
                log.warn("JWT token is expired: {}", e.getMessage());
                clearJwtTokenCookie(response);
            }
        }

        log.debug("Token of " + requestURI + " : " + token);
        try {
            if (token != null && jwtService.isTokenValid(token)) {
                log.debug("Token :" + token);
                String username = jwtService.extractUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtService.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is expired");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    log.debug("Found jwtToken cookie");
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void clearJwtTokenCookie(HttpServletResponse response) {
        Cookie newCookie = new Cookie("jwtToken", null);
        newCookie.setMaxAge(0); // Đặt thời gian sống bằng 0 để trình duyệt xóa cookie
        newCookie.setPath("/"); // Đảm bảo cookie được xóa trên toàn bộ domain
        response.addCookie(newCookie);
        log.debug("Cleared jwtToken cookie due to expired or invalid token");
    }
}
