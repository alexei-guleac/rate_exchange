package com.example.schimb.repository.exchange;

import com.example.schimb.model.exchange.ExchangeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Exchange operations repository
 */
@Repository
public interface CurrencyOperationRepository extends JpaRepository<ExchangeOperation, Long> {

}
