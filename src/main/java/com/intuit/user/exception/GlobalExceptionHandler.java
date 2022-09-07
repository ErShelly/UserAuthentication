package com.intuit.user.exception;

import com.intuit.user.dto.ApiErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND, "User Not Found", details);
        return new ResponseEntity<>(apiErrorDTO, apiErrorDTO.getStatus());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND, "User Already Exists", details);
        return new ResponseEntity<>(apiErrorDTO, apiErrorDTO.getStatus());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Incorrect Username/Password", details);
        return new ResponseEntity<>(apiErrorDTO, apiErrorDTO.getStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Validation Error", details);
        return new ResponseEntity<>(apiErrorDTO, apiErrorDTO.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<String>();
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Validation Errors",
                details);

        return new ResponseEntity<>(apiErrorDTO, apiErrorDTO.getStatus());
    }
}
