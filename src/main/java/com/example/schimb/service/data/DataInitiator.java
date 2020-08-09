package com.example.schimb.service.data;


import com.example.schimb.model.exchange.Currency;
import com.example.schimb.model.exchange.ExchangeRate;
import com.example.schimb.repository.exchange.CurrencyExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;


/**
 * For initialising currency database at startup
 */
@Service
@Slf4j
public class DataInitiator {

    private final CurrencyExchangeRepository exchangeRateRepository;

    @Autowired
    public DataInitiator(CurrencyExchangeRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Method initiates the database with necessary data
     * This method runs once at every application start and fills data if table is empty.
     */
    @PostConstruct
    public void loadDatabase() {
        Date date = new Date(System.currentTimeMillis());
        long numberOfRowsInDatabase = exchangeRateRepository.count();

        // if db empty
        if (numberOfRowsInDatabase == 0) {
            long i = 1L;
            exchangeRateRepository.save(new ExchangeRate(i++, Currency.USD, 1, 16.62, date));
            exchangeRateRepository.save(new ExchangeRate(i++, Currency.EUR, 1, 19.68, date));
            exchangeRateRepository.save(new ExchangeRate(i, Currency.BGN, 1, 10.07, date));
        }

        // show all data from database
        for (ExchangeRate exRate : exchangeRateRepository.findAll()) {
            log.info(exRate.toString());
        }
    }

}
