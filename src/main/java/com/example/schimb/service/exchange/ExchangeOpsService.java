package com.example.schimb.service.exchange;

import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.exchange.ExchangeOperation;
import com.example.schimb.rest.payload.CashUpdateRequest;
import com.example.schimb.rest.payload.CashUpdateResponse;
import com.example.schimb.rest.payload.ExchangeOperationRequest;


public interface ExchangeOpsService {

    ExchangeOperation save(ExchangeOperationRequest exchangeOperationRequest);

    CashUpdateResponse updateCash(CashUpdateRequest cashUpdateRequest) throws CurrencyNotFoundException;
}
