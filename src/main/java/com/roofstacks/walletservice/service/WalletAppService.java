package com.roofstacks.walletservice.service;

import com.roofstacks.walletservice.dto.CustomerDTO;
import com.roofstacks.walletservice.dto.WalletDTO;
import com.roofstacks.walletservice.exception.BadRequestException;
import com.roofstacks.walletservice.exception.CustomerNotFoundException;
import com.roofstacks.walletservice.exception.WalletAlreadyExistsException;
import com.roofstacks.walletservice.mapper.CustomerMapper;
import com.roofstacks.walletservice.mapper.WalletMapper;
import com.roofstacks.walletservice.model.Customer;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.model.enums.Currency;
import com.roofstacks.walletservice.repository.CustomerRepository;
import com.roofstacks.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletAppService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    WalletMapper walletMapper;

    public Optional<Customer> save_customer(CustomerDTO customer) {
        boolean isExists = customerRepository.selectExistsSsid(customer.getSsid());
        if (isExists) {
            throw new BadRequestException("Customer with SSID : " + customer.getSsid() + " is already created!");
        }

        Customer mappedCustomer = customerMapper.mapFromCustomerDTOtoCustomer(customer);
        mappedCustomer = customerRepository.save(mappedCustomer);
        return Optional.of(mappedCustomer);
    }


    public Optional<Wallet> save_wallet(WalletDTO wallet) {
        Wallet mappedWalled = walletMapper.mapFromWalletDTOtowallet(wallet);
        if (walletRepository.selectExistsWalletWithSameCurrency(mappedWalled.getCurrency(), mappedWalled.getCustomer().getId())) {
            throw new WalletAlreadyExistsException("Wallet with currency type : " + mappedWalled.getCurrency().getCurrencyName()
                    + " is already exists for customer: " + mappedWalled.getCustomer().getFirstName() + " " + mappedWalled.getCustomer().getSecondName());
        }
        mappedWalled = walletRepository.save(mappedWalled);
        return Optional.of(mappedWalled);

    }

    // will be used by WalletMapper during compile
    public Customer findCustomerById(long customerId) {
        Customer foundCustomer = customerRepository.findById(customerId).
                orElseThrow(() -> new CustomerNotFoundException("Customer with ID : " + customerId + " could not found!"));
        return foundCustomer;
    }

    public Optional<Wallet> getWallet(long customerId, String currencyName) {
        return Optional.of(walletRepository.findWallet(customerId, Currency.valueOf(currencyName)));
    }

    public Optional<List<Wallet>> getAllWallets(long customerId) {
        return Optional.of(walletRepository.findAllByCustomer(this.findCustomerById(customerId)));
    }

    public Optional<Wallet> deposit(long customerId, String currencyName, double amount) {
        Optional<Wallet> wallet = getWalletForDepositWithdraw(customerId, currencyName);
        wallet.get().setBalance(wallet.get().getBalance() + amount);
        this.walletRepository.save(wallet.get());
        return wallet;
    }

    public Optional<Wallet> withdraw(long customerId, String currencyName, double amount) {
        Optional<Wallet> wallet = getWalletForDepositWithdraw(customerId, currencyName);
        wallet.get().setBalance(wallet.get().getBalance() - amount);
        this.walletRepository.save(wallet.get());
        return wallet;
    }

    private Optional<Wallet> getWalletForDepositWithdraw(long customerId, String currencyName) {
        Customer foundCustomer = this.findCustomerById(customerId);
        Optional<Wallet> wallet = foundCustomer.getWallets().stream().filter(w -> w.getCurrency().equals(Currency.valueOf(currencyName))).findFirst();
        if (!wallet.isPresent()) {
            throw new BadRequestException("Customer : " + foundCustomer.getFirstName() + " " + foundCustomer.getSecondName() + " does not have wallet with currency : " + currencyName);
        }
        return wallet;
    }
}
