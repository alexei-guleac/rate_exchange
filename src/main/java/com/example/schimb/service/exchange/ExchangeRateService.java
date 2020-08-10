package com.example.schimb.service.exchange;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.exchange.ExchangeRate;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;

import java.util.List;


public interface ExchangeRateService {

    ExchangeRate findByCurrencyCode(String currencyCode) throws CurrencyNotFoundException;

    ExchangeRate updateExchangeRateByCurrency(ExchangeRateSetRequest exchangeRateSetRequest) throws CurrencyNotFoundException;

    List<ExchangeRate> findAll();

}
