package com.example.schimb.service.exchange;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.exchange.Currency;
import com.example.schimb.model.exchange.ExchangeRate;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;

import java.util.List;


public interface ExchangeRateService {

    ExchangeRate findByCurrency(Currency currency) throws CurrencyNotFoundException;
    ExchangeRate updateExchangeRateByCurrency(ExchangeRateSetRequest exchangeRateSetRequest);
    List<ExchangeRate> findAll();

}
