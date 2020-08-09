package com.example.schimb.service.exchange;

import com.example.schimb.model.exchange.ExchangeOperation;
import com.example.schimb.rest.payload.ExchangeOperationRequest;


public interface ExchangeOpsService {

    ExchangeOperation save(ExchangeOperationRequest exchangeOperationRequest);

}
