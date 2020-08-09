package com.example.schimb.rest.controller.advices;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.rest.controller.exchange.ExchangeOpsController;
import com.example.schimb.rest.controller.exchange.ExchangeRateController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackageClasses = {ExchangeOpsController.class, ExchangeRateController.class})
@Slf4j
public class ExchangeControllerAdvice {
    @ExceptionHandler({CurrencyNotFoundException.class})
    public ResponseEntity<Exception> handleUserException(Exception e) {
        log.info("Exception!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
