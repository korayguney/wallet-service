package com.roofstacks.walletservice.dto;

import com.roofstacks.walletservice.model.enums.Currency;
import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO implements Serializable {
    @ApiModelProperty(hidden = true)
    private long id;
    private double balance;
    private Currency currency;
    private long customerId;
}
