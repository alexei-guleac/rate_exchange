package com.example.schimb.repository.exchange;

import com.example.schimb.model.exchange.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Exchange rate repository
 */
@Repository
public interface CurrencyExchangeRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByCurrencyId(Long currencyId);

}
