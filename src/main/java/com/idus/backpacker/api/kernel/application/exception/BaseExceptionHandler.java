package com.idus.backpacker.api.kernel.application.exception;

import javax.annotation.Nullable;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public interface BaseExceptionHandler {
    default ResponseEntity<Object> toResponseEntity(@NonNull ExceptionResponse response) {
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Nullable
    default String getRequestUri(WebRequest request) {
        if ((request instanceof ServletWebRequest)) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        }

        return null;
    }
}
