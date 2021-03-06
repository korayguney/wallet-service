package com.roofstacks.walletservice.controller;

import com.roofstacks.walletservice.dto.WalletDTO;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.model.WalletServiceTransactionLogger;
import com.roofstacks.walletservice.service.WalletAppService;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/wallet")
public class WalletAppController {
    public WalletAppService walletAppService;

    public WalletAppController(WalletAppService walletAppService) {
        this.walletAppService = walletAppService;
    }

    @PostMapping(value = "/save-wallet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> saveWallet(@RequestBody @Valid WalletDTO wallet) {
        Optional<Wallet> resultOptional = this.walletAppService.save_wallet(wallet);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-wallets")
    public ResponseEntity<List<Wallet>> getAllWallets(@RequestParam(required = false, defaultValue = "0") long customerId){
        if(customerId > 0) {
            Optional<List<Wallet>> resultOptional = this.walletAppService.getAllWallets(customerId);
            if (resultOptional.isPresent()) {
                return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
            }
        } else {
            Optional<List<Wallet>> resultOptional = this.walletAppService.getAllWallets();
            if (resultOptional.isPresent()) {
                return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-wallet/{customerId}/{currency}")
    public ResponseEntity<Wallet> getWallet(@PathVariable long customerId, @PathVariable("currency") String currencyName){
        Optional<Wallet> resultOptional = this.walletAppService.getWallet(customerId, currencyName);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/deposit/{customerId}/{currency}/{amount}")
    public ResponseEntity<Wallet> depositToWallet(@PathVariable long customerId, @PathVariable("currency") String currencyName, @PathVariable double amount){
        Optional<Wallet> resultOptional = this.walletAppService.deposit(customerId, currencyName, amount);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/withdraw/{customerId}/{currency}/{amount}")
    public ResponseEntity<Wallet> withdrawFromWallet(@PathVariable long customerId, @PathVariable("currency") String currencyName, @PathVariable double amount){
        Optional<Wallet> resultOptional = this.walletAppService.withdraw(customerId, currencyName, amount);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/get-transactions-by-date", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<List<WalletServiceTransactionLogger>>> getAllTransactionsWithDate(
            @ApiParam(value = "transaction date for query wallet service usage", example = "e.g. 05/07/2021", required = true)
            @RequestParam String transactionDate,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return new ResponseEntity<>(this.walletAppService.getAllTransactionsWithDate(transactionDate, pageNumber, pageSize, pageable), HttpStatus.OK);
    }
}
