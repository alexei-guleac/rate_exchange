package com.example.schimb.service.data;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


/**
 * Service for access public exchange rate API's
 */
@Service
@Slf4j
public class ExchangeRatesRestService {

    private final RestService restService;

    //EUR by default
    private final String URL = "http://data.fixer.io/api/latest?access_key=9bae12d963886684fb06600c448c78e2&format=1";

    private String RATES_JSON = null;

    @Autowired
    public ExchangeRatesRestService(RestService restService) {
        this.restService = restService;
    }

    public Double getRateByCurrencyCode(@NotNull String code) {

        if (code.equals("EUR")) {
            return 1.00;
        }

        Double rate;

        double undefinedRate = 0.00;
        if (RATES_JSON == null) {
            try {
                RATES_JSON = restService.getPlainJSON(URL);
            } catch (HttpClientErrorException e) {
                return undefinedRate;
            }
            // log.info(RATES_JSON);
        }

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(RATES_JSON);
            // log.info(String.valueOf(json));
            json = (JSONObject) json.get("rates");
            rate = (Double) json.get(code);
            // log.info(String.valueOf(rate));
        } catch (ParseException err) {
            log.error("Error :: ", err.toString());
            return undefinedRate;
        }

        return rate != null ? rate : undefinedRate;
    }

    // public Double getRateByCurrencyCode(@NotNull String code) {
    //     not allowed via free plan
    //     String url = "https://api.exchangeratesapi.io/latest?base=" + code;
    //     String ratesJSON;
    //     try {
    //         ratesJSON = restService.getPlainJSON(url);
    //     } catch (HttpClientErrorException e) {
    //         return 0.00;
    //     }
    //     log.info(ratesJSON);
    //     return 0.00;
    // }

    public String getAllEURRates() {
        String url = "http://data.fixer.io/api/latest?access_key=9bae12d963886684fb06600c448c78e2&format=1";        //EUR by default
        String ratesJSON;
        try {
            ratesJSON = restService.getPlainJSON(url);
        } catch (HttpClientErrorException e) {
            return null;
        }
        log.info(ratesJSON);
        return ratesJSON;
    }
}
