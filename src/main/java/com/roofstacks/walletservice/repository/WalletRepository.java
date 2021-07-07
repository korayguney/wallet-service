package com.roofstacks.walletservice.repository;

import com.roofstacks.walletservice.model.Customer;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.model.WalletServiceTransactionLogger;
import com.roofstacks.walletservice.model.enums.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    @Query("SELECT CASE WHEN COUNT(w)>0 THEN TRUE ELSE FALSE END FROM Wallet w WHERE w.currency = ?1 AND w.customer.id = ?2")
    boolean selectExistsWalletWithSameCurrency(Currency currency, long id);

    @Query("SELECT w FROM Wallet w WHERE w.customer.id = ?1 AND w.currency = ?2")
    Wallet findWallet(long customerId, Currency currency);

    List<Wallet> findAllByCustomer(Customer customer);
}
