package com.gameapi.Exceptions.Handlers;

import com.gameapi.Exceptions.UserAlredyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionsHandlers {

    @ExceptionHandler(UserAlredyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlredyExistsException exp) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 302);
        response.put("error", exp.getMessage());
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}
