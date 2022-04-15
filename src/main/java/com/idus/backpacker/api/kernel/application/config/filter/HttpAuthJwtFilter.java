package com.idus.backpacker.api.kernel.application.config.filter;

import com.idus.backpacker.core.kernel.infrastructure.auth.AuthenticatedInfo;
import com.idus.backpacker.core.user.infrastructure.user.service.UserJwtService;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class HttpAuthJwtFilter extends OncePerRequestFilter {
    private static final String HEADER_PREFIX = "^(b|B)earer (.*?)$";
    private final UserJwtService service;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.matches(HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final var splits = header.split(" ");
        if (splits.length != 2) {
            filterChain.doFilter(request, response);
            return;
        }

        final var token = splits[1].trim();

        this.service.validateAccessToken(token);

        final var claims = service.parse(token);

        UsernamePasswordAuthenticationToken authenticationToken =
                this.getUserAuthenticationToken(token);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUserAuthenticationToken(String token) {
        final var claims = this.service.parse(token);

        return new UsernamePasswordAuthenticationToken(
                AuthenticatedInfo.builder().id(UUID.fromString(claims.getSubject())).build(),
                null,
                Collections.emptyList());
    }
}
