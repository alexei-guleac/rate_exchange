package com.example.schimb.service.implementations;


import com.example.schimb.exceptions.CurrencyNotFoundException;
import com.example.schimb.model.exchange.Cash;
import com.example.schimb.model.exchange.CurrencyElement;
import com.example.schimb.model.exchange.ExchangeOperation;
import com.example.schimb.model.users.User;
import com.example.schimb.repository.exchange.CashRepository;
import com.example.schimb.repository.exchange.CurrencyOperationRepository;
import com.example.schimb.repository.exchange.CurrencyRepository;
import com.example.schimb.rest.payload.CashUpdateRequest;
import com.example.schimb.rest.payload.CashUpdateResponse;
import com.example.schimb.rest.payload.ExchangeOperationRequest;
import com.example.schimb.service.exchange.ExchangeOpsService;
import com.example.schimb.service.users.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Date;
import java.util.Optional;


/**
 * Exchange rate Service class for database repository
 * Contains method for
 * - evaluate currency exchange
 */
@Service
@Slf4j
public class ExchangeOpsServiceImpl implements ExchangeOpsService {

    private final UserService userService;

    private final CurrencyOperationRepository currencyOperationRepository;

    private final CashRepository cashRepository;

    private final CurrencyRepository currencyRepository;

    @Autowired
    public ExchangeOpsServiceImpl(CurrencyOperationRepository currencyOperationRepository,
                                  UserService userService,
                                  CashRepository cashRepository,
                                  CurrencyRepository currencyRepository) {
        this.currencyOperationRepository = currencyOperationRepository;
        this.userService = userService;
        this.cashRepository = cashRepository;
        this.currencyRepository = currencyRepository;
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

    @Override
    public CashUpdateResponse updateCash(CashUpdateRequest cashUpdateRequest) throws CurrencyNotFoundException {

        final String username = cashUpdateRequest.getUsername();
        final String currencyCode = cashUpdateRequest.getCurrencyCode().toUpperCase().strip();
        final Double amount = cashUpdateRequest.getAmount();
        final Date date = cashUpdateRequest.getPerformedAt();

        // check if user exists in system
        User userByEmail = userService.getUserByEmail(username);

        // check if currency code string is valid
        long numericCode = 0;
        try {
            CurrencyUnit curr = Monetary.getCurrency(currencyCode.toUpperCase().strip());
            numericCode = curr.getNumericCode();
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new CurrencyNotFoundException(
                    "Invalid value for currency: " + numericCode + " :: " + e.getMessage());
        }

        // check if currency with this code is registered in database table
        long finalNumericCode = numericCode;
        currencyRepository.findById(numericCode).orElseThrow(
                () -> new CurrencyNotFoundException("Invalid currency " + finalNumericCode));


        // update cash balance
        CurrencyElement currency = new CurrencyElement(
                Monetary.getCurrency(currencyCode).getNumericCode(), currencyCode);
        Optional<Cash> cashOptional = cashRepository.findByUserAndCurrency(userByEmail, currency);

        Cash cash;
        if (cashOptional.isPresent()) {
            cash = cashOptional.get();
            cash.setAmount(amount);
            cash.setPerformedAt(date);
        } else {
            cash = Cash.builder()
                    .user(userByEmail)
                    .currency(currency)
                    .amount(amount)
                    .performedAt(date)
                    .build();
        }

        Cash savedCash = cashRepository.save(cash);

        return CashUpdateResponse.builder()
                .username(savedCash.getUser().getUsername())
                .currencyCode(savedCash.getCurrency().getCode())
                .amount(savedCash.getAmount())
                .performedAt(savedCash.getPerformedAt())
                .build();
    }
}
