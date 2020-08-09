package com.example.schimb.service.implementations;


import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.Currency;
import com.example.schimb.model.ExchangeOperation;
import com.example.schimb.model.ExchangeRate;
import com.example.schimb.repository.CurrencyExchangeRepository;
import com.example.schimb.repository.CurrencyOperationRepository;
import com.example.schimb.rest.payload.ExchangeOperationRequest;
import com.example.schimb.rest.payload.ExchangeRateSetRequest;
import com.example.schimb.service.ExchangeOpsService;
import com.example.schimb.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Exchange rate Service class for database repository
 * Contains method for
 * - evaluate currency exchange
 */
@Service
@Slf4j
public class ExchangeOpsServiceImpl implements ExchangeOpsService {

    private final CurrencyOperationRepository currencyOperationRepository;

    @Autowired
    public ExchangeOpsServiceImpl(CurrencyOperationRepository currencyOperationRepository) {
        this.currencyOperationRepository = currencyOperationRepository;
    }

    @Override
    public ExchangeOperation save(ExchangeOperationRequest exchangeOperationRequest) {

        ExchangeOperation exchangeOperation = ExchangeOperation.builder()
                .currency(exchangeOperationRequest.getCurrencyCode())
                .rate(exchangeOperationRequest.getRate())
                .amountReceived(exchangeOperationRequest.getAmountReceived())
                .issuedAmount(exchangeOperationRequest.getIssuedAmount())
                .performedAt(new Date(System.currentTimeMillis()))
                .build();

        return currencyOperationRepository.save(exchangeOperation);
    }
}
