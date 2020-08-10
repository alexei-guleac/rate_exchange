package com.example.schimb.service.data;


import com.example.schimb.model.exchange.CurrencyElement;
import com.example.schimb.model.exchange.ExchangeRate;
import com.example.schimb.repository.exchange.CurrencyExchangeRepository;
import com.example.schimb.repository.exchange.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;


/**
 * For initialising currency database at startup
 */
@Service
@Slf4j
public class DataLoader {

    private final CurrencyExchangeRepository exchangeRateRepository;

    private final CurrencyRepository currencyRepository;

    private final ExchangeRatesRestService exchangeRatesRestService;

    @Autowired
    public DataLoader(CurrencyExchangeRepository exchangeRateRepository, CurrencyRepository currencyRepository, ExchangeRatesRestService exchangeRatesRestService) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeRatesRestService = exchangeRatesRestService;
    }

    /**
     * Method initiates the database with necessary data
     * This method runs once at every application start and fills data if table is empty.
     */
    @PostConstruct
    public void loadDatabase() {
        // fill currencies
        ArrayList<CurrencyUnit> currencies = new ArrayList<>();
        Locale[] locales = Locale.getAvailableLocales();

        for (Locale loc : locales) {
            try {
                CurrencyUnit unit = Monetary.getCurrency(loc);
                if (!currencies.contains(unit))
                    currencies.add(unit);
            } catch (Exception exc) {
                // Locale not found
            }
            Collections.sort(currencies);
        }
        log.info(currencies.toString());
        log.info(String.valueOf(currencies.size()));


        for (CurrencyUnit currency : currencies) {
            currencyRepository.save(
                    new CurrencyElement(
                            currency.getNumericCode(), currency.getCurrencyCode()));
        }

        // fill exchange rates
        Date date = new Date(System.currentTimeMillis());
        long numberOfRowsInDatabase = exchangeRateRepository.count();

        // if db empty
        long i = 1L;
        ExchangeRate.ExchangeRateBuilder exchangeRateBuilder = ExchangeRate.builder();
        CurrencyElement.CurrencyElementBuilder currencyCodeBuilder = CurrencyElement.builder();
        if (numberOfRowsInDatabase == 0) {
            for (CurrencyElement currencyElement : currencyRepository.findAll()) {
                // exchangeRateRepository.save(
                //         ExchangeRate.builder()
                //                 .id(i++)
                //                 .currency(
                //                         CurrencyCode.builder().id(currencyCode.getId()).code(currencyCode.getCode()).build())
                //                 .factor(1).rate(exchangeRatesRestService.getRateByCurrencyCode(currencyCode.getCode())).updatedAt(date).build());

                exchangeRateRepository.save(
                        exchangeRateBuilder
                                .id(i++)
                                .currency(
                                        currencyCodeBuilder
                                                .id(currencyElement.getId())
                                                .code(currencyElement.getCode())
                                                .build())
                                .factor(1)
                                .rate(
                                        exchangeRatesRestService.getRateByCurrencyCode(currencyElement.getCode())
                                )
                                .updatedAt(date)
                                .build());

            }
        }

        // show all data from database
        for (ExchangeRate exRate : exchangeRateRepository.findAll()) {
            // log.info(exRate.toString());
        }
    }

}
