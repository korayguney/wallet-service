package com.roofstacks.walletservice.model;

import com.roofstacks.walletservice.model.enums.Currency;
import com.roofstacks.walletservice.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WalletServiceTransactionLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private double balance_before;
    private double balance_after;
    private double transaction_amount;
    @Enumerated(EnumType.STRING)
    private Currency transaction_currency;
    private LocalDate transaction_date;
    private String clientIpAdress;
    private String clientReqUrl;
    private String clientSessionActivityId;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
