package com.roofstacks.walletservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roofstacks.walletservice.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet extends AbstractEntity {

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private double balance;

    @NotNull(message = "Currency is mandatory")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private LocalDate createDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
