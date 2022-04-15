package com.idus.backpacker.api.kernel.application.exception;

import com.idus.backpacker.core.kernel.domain.share.DomainInvalidInputException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SharedKernelExceptionHandler extends ResponseEntityExceptionHandler
        implements BaseExceptionHandler {

    @Value("${application.debug:false}")
    private Boolean debugFlag;

    @ExceptionHandler(DomainInvalidInputException.class)
    public ResponseEntity<?> handleDomainInvalidInputException(
            DomainInvalidInputException exception, WebRequest request) {
        return this.toResponseEntity(
                ExceptionResponse.builder()
                        .message("허용하지 않는 값입니다.")
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(
                                Collections.singletonList(
                                        ExceptionResponse.ErrorField.builder()
                                                .field(exception.getField())
                                                .message(exception.getMessage())
                                                .build()))
                        .path(this.getRequestUri(request))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.toResponseEntity(this.makeBindingException(exception, request));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return this.toResponseEntity(this.makeBindingException(exception, request));
    }

    @ExceptionHandler({Throwable.class, Exception.class, RuntimeException.class})
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<?> handle(Throwable cause, WebRequest request) {
        logger.error(String.format("[UNFILTERED EXCEPTION GLOBAL] %s", cause.getMessage()), cause);

        final var message =
                this.debugFlag ? cause.getMessage() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

        return this.toResponseEntity(
                ExceptionResponse.builder()
                        .message(message)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .path(this.getRequestUri(request))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return this.toResponseEntity(
                ExceptionResponse.builder()
                        .message("올바른 요청이 아닙니다.")
                        .status(HttpStatus.BAD_REQUEST)
                        .path(this.getRequestUri(request))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(String.format("[UNFILTERED EXCEPTION GLOBAL] %s", ex.getMessage()), ex);

        final var response =
                ExceptionResponse.builder().status(status).path(this.getRequestUri(request));

        if (this.debugFlag) {
            response.stacks(
                    Arrays.stream(ex.getStackTrace())
                            .map(StackTraceElement::toString)
                            .collect(Collectors.toList()));
        }

        return this.toResponseEntity(response.build());
    }

    private ExceptionResponse makeBindingException(BindingResult exception, WebRequest request) {
        return ExceptionResponse.builder()
                .message("허용하지 않는 값을 입력하였거나 잘못된 요청입니다.")
                .errors(
                        exception.getFieldErrors().stream()
                                .map(
                                        error ->
                                                ExceptionResponse.ErrorField.builder()
                                                        .field(error.getField())
                                                        .message(error.getDefaultMessage())
                                                        .build())
                                .sorted(Comparator.comparing(ExceptionResponse.ErrorField::getField))
                                .collect(Collectors.toList()))
                .path(this.getRequestUri(request))
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
