package com.example.schimb.service;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.Currency;
import com.example.schimb.model.ExchangeOperation;
import com.example.schimb.model.ExchangeRate;
import com.example.schimb.rest.payload.ExchangeOperationRequest;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;

import java.util.List;


public interface ExchangeOpsService {

    ExchangeOperation save(ExchangeOperationRequest exchangeOperationRequest);

}
