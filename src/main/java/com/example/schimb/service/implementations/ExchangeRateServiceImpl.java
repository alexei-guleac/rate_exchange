package com.example.schimb.service.implementations;


import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.exchange.ExchangeRate;
import com.example.schimb.repository.exchange.CurrencyExchangeRepository;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;
import com.example.schimb.service.exchange.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
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
    public ExchangeRate findByCurrencyCode(String currencyCode) throws CurrencyNotFoundException {

        long numericCode = 0;
        try {
            CurrencyUnit curr = Monetary.getCurrency(currencyCode.toUpperCase().strip());
            numericCode = curr.getNumericCode();
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new CurrencyNotFoundException(
                    "Invalid value for currency: " + numericCode + " :: " + e.getMessage());
        }

        long finalNumericCode = numericCode;
        return exchangeRateRepository.findByCurrencyId(numericCode).orElseThrow(
                () -> new CurrencyNotFoundException("Invalid currency " + finalNumericCode));
    }

    @Override
    public ExchangeRate updateExchangeRateByCurrency(ExchangeRateSetRequest exchangeRateSetRequest) throws CurrencyNotFoundException {

        long numericCode = 0;
        try {
            CurrencyUnit curr = Monetary.getCurrency(exchangeRateSetRequest.getCurrencyCode().toUpperCase().strip());
            numericCode = curr.getNumericCode();
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new CurrencyNotFoundException(
                    "Invalid value for currency: " + numericCode + " :: " + e.getMessage());
        }

        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findByCurrencyId(numericCode);
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
