package com.example.schimb.rest.controller.advices;

import com.example.schimb.exceptions.users.UserCreationException;
import com.example.schimb.exceptions.users.UserNotFoundException;
import com.example.schimb.exceptions.users.UserUpdatingException;
import com.example.schimb.rest.controller.users.RegistrationController;
import com.example.schimb.rest.controller.users.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackageClasses = {UserController.class, RegistrationController.class})
@Slf4j
public class UserControllerAdvice {
    @ExceptionHandler({UserNotFoundException.class,
            UserCreationException.class,
            UserUpdatingException.class})
    public ResponseEntity<Exception> handleUserException(Exception e) {
        log.info("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
