package com.idus.backpacker.api.kernel.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idus.backpacker.api.kernel.application.exception.BaseExceptionHandler;
import com.idus.backpacker.api.kernel.application.exception.ExceptionResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class HttpSecurityAuthenticationEntryPoint
        implements AuthenticationEntryPoint, BaseExceptionHandler {
    @Autowired private ObjectMapper mapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        final var result =
                ExceptionResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .path(request.getRequestURI())
                        .message(authException.getMessage())
                        .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(this.mapper.writeValueAsString(result));
    }
}
