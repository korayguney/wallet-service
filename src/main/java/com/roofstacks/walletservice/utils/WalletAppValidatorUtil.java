package com.roofstacks.walletservice.utils;

import com.roofstacks.walletservice.exception.ErrorMessageConstants;
import com.roofstacks.walletservice.exception.TransactionDateParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WalletAppValidatorUtil {

    private WalletAppValidatorUtil() {
    }

    public static void validateTransactionDate(String transactionDate, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(transactionDate, formatter);
        } catch (DateTimeParseException ex){
            throw new TransactionDateParseException(ErrorMessageConstants.TRANSACTION_DATE_IS_WRONG);
        }
    }
}
