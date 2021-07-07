package com.roofstacks.walletservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletAppErrorResponse {

    private int status;
    private String message;
    private long timeStamp;

}
