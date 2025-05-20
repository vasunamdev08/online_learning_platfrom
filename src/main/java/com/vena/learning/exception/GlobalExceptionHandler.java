package com.vena.learning.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception e, WebRequest req) {
        Map<String, Object> body = Map.of(
                "status", 500,
                "error", "Internal Server Error",
                "message", e.getMessage(),
                "path", req.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(500).body(body);
    }
}
