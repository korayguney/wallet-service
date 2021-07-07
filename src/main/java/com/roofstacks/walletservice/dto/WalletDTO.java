package com.roofstacks.walletservice.dto;

import com.roofstacks.walletservice.model.enums.Currency;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO implements Serializable {
    @ApiModelProperty(hidden = true)
    private long id;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @ApiModelProperty(example = "0.0")
    private double balance;

    @ApiModelProperty(example = "TRY or USD or EUR or GBP")
    @NotNull(message = "Currency is mandatory")
    private Currency currency;

    @NotNull(message = "Customer ID is mandatory")
    private long customerId;
}
