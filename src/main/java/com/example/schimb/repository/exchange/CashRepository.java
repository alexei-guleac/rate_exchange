package com.example.schimb.repository.exchange;

import com.example.schimb.model.exchange.Cash;
import com.example.schimb.model.exchange.CurrencyElement;
import com.example.schimb.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Cash (numerar/баланс) repository
 */
@Repository
public interface CashRepository extends JpaRepository<Cash, Long> {

    Optional<Cash> findByUserAndCurrency(User user, CurrencyElement currency);

}
