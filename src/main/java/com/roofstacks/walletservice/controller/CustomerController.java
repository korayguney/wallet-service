package com.roofstacks.walletservice.controller;

import com.roofstacks.walletservice.dto.CustomerDTO;
import com.roofstacks.walletservice.model.Customer;
import com.roofstacks.walletservice.service.WalletAppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/customer")
public class CustomerController {
    public WalletAppService walletAppService;

    public CustomerController(WalletAppService walletAppService) {
        this.walletAppService = walletAppService;
    }

    @PostMapping(value = "/save-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> saveCustomer(@RequestBody @Valid CustomerDTO customer) {
        Optional<Customer> resultOptional = this.walletAppService.save_customer(customer);
        if (resultOptional.isPresent()) {
            return new ResponseEntity<>(resultOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
