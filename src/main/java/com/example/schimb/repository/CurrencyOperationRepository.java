package com.example.schimb.repository;

import com.example.schimb.model.ExchangeOperation;
import com.example.schimb.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Exchange operations repository
 */
@Repository
public interface CurrencyOperationRepository extends JpaRepository<ExchangeOperation, Long> {

}
