package com.roofstacks.walletservice.exception;

public class WalletAlreadyExistsException extends RuntimeException {
    public WalletAlreadyExistsException(String msg) {
        super(msg);
    }
}
