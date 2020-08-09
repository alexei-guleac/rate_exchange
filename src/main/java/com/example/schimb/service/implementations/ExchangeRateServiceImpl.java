package com.example.schimb.service.implementations;


import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.Currency;
import com.example.schimb.model.ExchangeRate;
import com.example.schimb.repository.CurrencyExchangeRepository;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;
import com.example.schimb.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Exchange rate Service class for database repository
 * Contains method for
 * - set exchange rate
 * - get current exchange rate by specified currency
 * - etc...
 */
@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final CurrencyExchangeRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateServiceImpl(CurrencyExchangeRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate findByCurrency(Currency currency) throws CurrencyNotFoundException {
        return exchangeRateRepository.findByCurrency(currency).orElseThrow(
                () -> new CurrencyNotFoundException("Invalid currency " + currency));
    }

    @Override
    public ExchangeRate updateExchangeRateByCurrency(ExchangeRateSetRequest exchangeRateSetRequest) {

        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByCurrency(exchangeRateSetRequest.getCurrencyCode());
        ExchangeRate updatedExchangeRate = optionalExchangeRate.get();
        log.info(updatedExchangeRate.toString());

        updatedExchangeRate.setFactor(exchangeRateSetRequest.getFactor());
        updatedExchangeRate.setRate(exchangeRateSetRequest.getRate());
        updatedExchangeRate.setUpdatedAt(new Date(System.currentTimeMillis()));

        return exchangeRateRepository.save(updatedExchangeRate);
    }

    @Override
    public List<ExchangeRate> findAll() {
        return exchangeRateRepository.findAll();
    }
}
