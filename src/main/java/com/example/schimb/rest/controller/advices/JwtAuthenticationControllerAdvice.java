package com.example.schimb.rest.controller.advices;

import com.example.schimb.exceptions.users.InvalidCredentialsException;
import com.example.schimb.rest.controller.users.JwtAuthenticationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackageClasses = {JwtAuthenticationController.class})
@Slf4j
public class JwtAuthenticationControllerAdvice {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Exception> handleBadCredentials(Exception e) {
        log.info("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
