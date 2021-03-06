package com.example.schimb.rest.controller.exchange;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.exchange.ExchangeRate;
import com.example.schimb.rest.payload.ExchangeRateResponse;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;
import com.example.schimb.service.ApiEndpoints;
import com.example.schimb.service.exchange.ExchangeRateService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Provides methods for
 * - set exchange rate
 * - get current exchange rate by specified currency
 * - etc...
 */
@Api(value = "Exchange rate Controller")
@RestController
@RequestMapping(ApiEndpoints.rates)
@Slf4j
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Endpoint to set current exchange rate by currency code
     *
     * @param exchangeRateSetRequest - exchange rate request
     * @return result ExchangeRate
     */
    @PostMapping(ApiEndpoints.update)
    public ExchangeRate setExchangeRate(@RequestBody ExchangeRateSetRequest exchangeRateSetRequest) throws CurrencyNotFoundException {
        return exchangeRateService.updateExchangeRateByCurrency(exchangeRateSetRequest);
    }

    /**
     * Endpoint to get current exchange rate by currency
     *
     * @param code - currency code
     * @return result ExchangeRate
     * @throws CurrencyNotFoundException if currency code not found
     */
    @GetMapping("/get")
    public ResponseEntity<?> getExchangeRate(@RequestParam String code)
            throws CurrencyNotFoundException {
        log.info(code);
        ExchangeRate exchangeRate = exchangeRateService.findByCurrencyCode(code);

        ExchangeRateResponse exchangeRateResponse = ExchangeRateResponse.builder()
                .currencyCode(exchangeRate.getCurrency().getCode())
                .rate(exchangeRate.getRate())
                .factor(exchangeRate.getFactor())
                .dateRate(exchangeRate.getUpdatedAt())
                .build();

        return ResponseEntity.ok(exchangeRateResponse);
    }

    /**
     * Endpoint to get all stored current exchange rates
     *
     * @return list of exchange rates
     */
    @GetMapping
    public ResponseEntity<?> getExchangeRates() {
        return ResponseEntity.ok(exchangeRateService.findAll());
    }

}
