package com.roofstacks.walletservice.exception;

public class TransactionDateParseException extends RuntimeException {

    public TransactionDateParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionDateParseException(String message) {
        super(message);
    }

    public TransactionDateParseException(Throwable cause) {
        super(cause);
    }
}
