package com.roofstacks.walletservice.controller;

import com.roofstacks.walletservice.dto.CustomerDTO;
import com.roofstacks.walletservice.dto.WalletDTO;
import com.roofstacks.walletservice.model.Customer;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.service.WalletAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/")
public class WalletAppController {
    public WalletAppService walletAppService;

    public WalletAppController(WalletAppService walletAppService) {
        this.walletAppService = walletAppService;
    }

    @PostMapping(value = "/save-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerDTO customer) {
        Optional<Customer> resultOptional = this.walletAppService.save_customer(customer);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/save-wallet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> saveWallet(@RequestBody WalletDTO wallet) {
        Optional<Wallet> resultOptional = this.walletAppService.save_wallet(wallet);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-wallets/{customerId}")
    public ResponseEntity<List<Wallet>> getAllWallets(@PathVariable long customerId){
        Optional<List<Wallet>> resultOptional = this.walletAppService.getAllWallets(customerId);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
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



}
