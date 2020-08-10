package com.example.schimb.service.implementations;


import com.example.schimb.model.exchange.CurrencyElement;
import com.example.schimb.model.exchange.ExchangeOperation;
import com.example.schimb.repository.exchange.CurrencyOperationRepository;
import com.example.schimb.rest.payload.ExchangeOperationRequest;
import com.example.schimb.service.exchange.ExchangeOpsService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import java.util.Date;


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

        @NonNull String currencyCode = exchangeOperationRequest.getCurrencyCode();
        ExchangeOperation exchangeOperation = ExchangeOperation.builder()
                .currency(
                        new CurrencyElement(
                                Monetary.getCurrency(currencyCode).getNumericCode(), currencyCode))
                .rate(exchangeOperationRequest.getRate())
                .amountReceived(exchangeOperationRequest.getAmountReceived())
                .issuedAmount(exchangeOperationRequest.getIssuedAmount())
                .performedAt(new Date(System.currentTimeMillis()))
                .build();

        return currencyOperationRepository.save(exchangeOperation);
    }
}
