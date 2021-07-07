package com.roofstacks.walletservice.exception;

public class NoEnoughBalanceForWithdrawException extends RuntimeException {
    public NoEnoughBalanceForWithdrawException(String msg) {
        super(msg);
    }
}
