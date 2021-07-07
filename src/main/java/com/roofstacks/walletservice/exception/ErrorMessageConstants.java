package com.roofstacks.walletservice.exception;

public class ErrorMessageConstants {
    private ErrorMessageConstants(){}
    /*
        Currency Converter API Error Messages
     */
    public static final String TRANSACTION_DATE_IS_WRONG = "Transaction date format is wrong! It must be such as '05/07/2021'";
    public static final String BALANCE_AMOUNT_IS_MINUS = "Balance value must be greater than zero!";
    public static final String NO_ENOUGH_BALANCE = "Balance amount is not enough for requested withdraw amount : ";

}
