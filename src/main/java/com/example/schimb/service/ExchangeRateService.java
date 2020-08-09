package com.example.schimb.service;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.Currency;
import com.example.schimb.model.ExchangeRate;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface ExchangeRateService {

    ExchangeRate findByCurrency(Currency currency) throws CurrencyNotFoundException;
    ExchangeRate updateExchangeRateByCurrency(ExchangeRateSetRequest exchangeRateSetRequest);
    List<ExchangeRate> findAll();

}
