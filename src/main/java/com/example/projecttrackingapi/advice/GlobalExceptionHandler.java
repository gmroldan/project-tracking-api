package com.example.projecttrackingapi.advice;

import com.example.projecttrackingapi.exception.TaskNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity generalError(HttpServletRequest request,
                                       Exception exception) {
        log.error("Error processing request: {}", request, exception);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity notFoundError(HttpServletRequest request,
                                        Exception exception) {
        log.warn("Record not found. {}", exception.getMessage());
        return ResponseEntity.notFound().build();
    }
}
