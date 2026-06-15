package com.klu.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        String token = null;

        String username = null;

        String role = null;

        // CHECK TOKEN
        if (
                authHeader != null
                &&
                authHeader.startsWith("Bearer ")
        ) {

            token =
                    authHeader.substring(7);

            try {

                Claims claims =
                        jwtUtil.getClaims(token);

                username =
                        claims.getSubject();

                role =
                        claims.get(
                                "role",
                                String.class
                        );

            } catch (Exception e) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"Invalid or expired token\"}");
                return;
            }
        }

        // SET AUTHENTICATION
        if (
                username != null
                &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        == null
        ) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            role != null
                                    ? List.of(
                                            new SimpleGrantedAuthority(
                                                    "ROLE_" + role.toUpperCase()
                                            )
                                    )
                                    : List.of()
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}
