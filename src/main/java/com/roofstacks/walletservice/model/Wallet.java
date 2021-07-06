package com.roofstacks.walletservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roofstacks.walletservice.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDate createDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
