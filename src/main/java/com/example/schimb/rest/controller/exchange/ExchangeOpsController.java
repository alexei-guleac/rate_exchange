package com.example.schimb.rest.controller.exchange;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.exchange.ExchangeOperation;
import com.example.schimb.rest.payload.CashUpdateRequest;
import com.example.schimb.rest.payload.ExchangeOperationRequest;
import com.example.schimb.service.ApiEndpoints;
import com.example.schimb.service.exchange.ExchangeOpsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
     * @param username - user login (email)
     * @param code     - currency code
     * @param amount   - monetary amount
     * @return operation result
     */
    @PutMapping(ApiEndpoints.balance + ApiEndpoints.update)
    public ResponseEntity<?> performCashUpdate(
            @RequestParam String username,
            @RequestParam String code,
            @RequestParam String amount,
            @RequestParam String date
    ) throws CurrencyNotFoundException, ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //Parsing the given String to Date object
        Date performedAt = formatter.parse(date);

        CashUpdateRequest cashUpdateRequest = CashUpdateRequest.builder()
                .username(username)
                .currencyCode(code)
                .amount(Double.valueOf(amount))
                .performedAt(performedAt)
                .build();

        return ResponseEntity.ok().body(exchangeOpsService.updateCash(cashUpdateRequest));
    }
}
