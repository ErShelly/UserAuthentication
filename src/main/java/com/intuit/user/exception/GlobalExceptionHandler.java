package com.intuit.user.exception;

import com.intuit.user.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "User Not Found", details);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "User Already Exists", details);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiError apiError = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Incorrect Username/Password", details);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
