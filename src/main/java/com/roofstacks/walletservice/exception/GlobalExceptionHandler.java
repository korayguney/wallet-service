package com.roofstacks.walletservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<WalletAppErrorResponse> handleException(CustomerNotFoundException exc) {
        WalletAppErrorResponse error = prepareErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({WalletAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WalletAppErrorResponse> handleException(WalletAlreadyExistsException exc) {
        WalletAppErrorResponse error = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransactionDateParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WalletAppErrorResponse> handleException(TransactionDateParseException exc) {
        WalletAppErrorResponse error = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WalletAppErrorResponse> handleException(BadRequestException exc) {
        WalletAppErrorResponse error = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoEnoughBalanceForWithdrawException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WalletAppErrorResponse> handleException(NoEnoughBalanceForWithdrawException exc) {
        WalletAppErrorResponse error = prepareErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private WalletAppErrorResponse prepareErrorResponse(HttpStatus httpStatus, String message) {
        WalletAppErrorResponse error = new WalletAppErrorResponse();
        error.setStatus(httpStatus.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());
        return error;
    }
}
