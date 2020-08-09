package com.example.schimb.repository;

import com.example.schimb.model.Currency;
import com.example.schimb.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Exchange rate repository
 */
@Repository
public interface CurrencyExchangeRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByCurrency(Currency currency);
    List<ExchangeRate> findAll();

}
