package com.applemart.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
