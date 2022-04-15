package com.idus.backpacker.api.user.application.exception;

import com.idus.backpacker.api.kernel.application.exception.BaseExceptionHandler;
import com.idus.backpacker.api.kernel.application.exception.ExceptionResponse;
import com.idus.backpacker.core.user.infrastructure.user.service.UserJwtServiceException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(
        basePackages = {"com.idus.backpacker.api.user", "com.idus.backpacker.core.user"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler implements BaseExceptionHandler {
    @ExceptionHandler({UserJwtServiceException.class})
    public ResponseEntity<?> handleUserJwtServiceException(
            UserJwtServiceException exception, WebRequest request) {
        return this.toResponseEntity(
                ExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(exception.getMessage())
                        .path(this.getRequestUri(request))
                        .build());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(
            UserNotFoundException exception, WebRequest request) {
        return this.toResponseEntity(
                ExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(exception.getMessage())
                        .path(this.getRequestUri(request))
                        .build());
    }
}
