package com.intuit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorDTO {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;
}
