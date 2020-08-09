package com.example.schimb.rest.controller;

import com.example.schimb.model.ExchangeOperation;
import com.example.schimb.rest.payload.ExchangeOperationRequest;
import com.example.schimb.service.ApiEndpoints;
import com.example.schimb.service.ExchangeOpsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Provides methods for
 * - evaluate currency exchange
 * - etc...
 */
@Api(value = "Exchange operations Controller")
@RestController
@Slf4j
public class ExchangeOpsController {

    private final ExchangeOpsService exchangeOpsService;

    @Autowired
    public ExchangeOpsController(ExchangeOpsService exchangeOpsService) {
        this.exchangeOpsService = exchangeOpsService;
    }

    /**
     * Endpoint to perform currency exchange
     *
     * @param exchangeOperationRequest - exchange operations request
     * @return result ExchangeRate
     */
    @PostMapping(ApiEndpoints.exchange)
    public ExchangeOperation performExchange(@RequestBody ExchangeOperationRequest exchangeOperationRequest) {
        return exchangeOpsService.save(exchangeOperationRequest);
    }

    /**
     * Endpoint to perform cash update
     *
     * @param username
     * @param code
     * @param amount
     * @return
     */
    @PutMapping(ApiEndpoints.updateCash)
    public ResponseEntity<?> performCashUpdate(
            @RequestParam String username,
            @RequestParam String code,
            @RequestParam String amount
    ) {
        throw new UnsupportedOperationException("Not implemented yet!");
        // return exchangeOpsService.updateCash(exchangeOperationRequest);
    }
}
