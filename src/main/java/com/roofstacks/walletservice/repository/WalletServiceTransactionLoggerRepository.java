package com.roofstacks.walletservice.repository;

import com.roofstacks.walletservice.model.WalletServiceTransactionLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WalletServiceTransactionLoggerRepository extends PagingAndSortingRepository<WalletServiceTransactionLogger, Long> {

    @Query("SELECT w FROM WalletServiceTransactionLogger w WHERE w.transaction_date= ?1")
    Page<List<WalletServiceTransactionLogger>> findAllByTransactionDate(LocalDate transaction_date, Pageable pageable);
}
