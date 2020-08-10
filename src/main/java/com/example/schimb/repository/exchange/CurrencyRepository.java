package com.example.schimb.repository.exchange;

import com.example.schimb.model.exchange.CurrencyElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Currency code repository
 */
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyElement, Long> {
}
