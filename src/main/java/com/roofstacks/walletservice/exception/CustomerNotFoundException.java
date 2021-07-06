package com.roofstacks.walletservice.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
