package com.example.task_management.exception;




import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            messages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        });

        ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation Failed", messages);
        return new ResponseEntity<>(err, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());

        ApiError err = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error", messages);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
