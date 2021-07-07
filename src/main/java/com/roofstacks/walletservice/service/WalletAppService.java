package com.roofstacks.walletservice.service;

import com.roofstacks.walletservice.dto.CustomerDTO;
import com.roofstacks.walletservice.dto.WalletDTO;
import com.roofstacks.walletservice.exception.*;
import com.roofstacks.walletservice.mapper.CustomerMapper;
import com.roofstacks.walletservice.mapper.WalletMapper;
import com.roofstacks.walletservice.model.Customer;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.model.WalletServiceTransactionLogger;
import com.roofstacks.walletservice.model.enums.Currency;
import com.roofstacks.walletservice.model.enums.TransactionType;
import com.roofstacks.walletservice.repository.CustomerRepository;
import com.roofstacks.walletservice.repository.WalletRepository;
import com.roofstacks.walletservice.repository.WalletServiceTransactionLoggerRepository;
import com.roofstacks.walletservice.utils.ClientRequestInfo;
import com.roofstacks.walletservice.utils.WalletAppValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class WalletAppService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WalletServiceTransactionLoggerRepository transactionLoggerRepository;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    WalletMapper walletMapper;
    @Autowired
    private ClientRequestInfo clientRequestInfo;

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
        this.validateRequest(wallet);
        Wallet mappedWalled = walletMapper.mapFromWalletDTOtowallet(wallet);
        if (walletRepository.selectExistsWalletWithSameCurrency(mappedWalled.getCurrency(), mappedWalled.getCustomer().getId())) {
            throw new WalletAlreadyExistsException("Wallet with currency type : " + mappedWalled.getCurrency().getCurrencyName()
                    + " is already exists for customer: " + mappedWalled.getCustomer().getFirstName() + " " + mappedWalled.getCustomer().getSecondName());
        }
        mappedWalled = walletRepository.save(mappedWalled);
        return Optional.of(mappedWalled);

    }

    private void validateRequest(WalletDTO wallet) {
        WalletAppValidatorUtil.validateWalletBalance(wallet.getBalance());
    }

    // will be used by WalletMapper during compile
    public Customer findCustomerById(long customerId) {
        Customer foundCustomer = customerRepository.findById(customerId).
                orElseThrow(() -> new CustomerNotFoundException("Customer with ID : " + customerId + " could not found!"));
        return foundCustomer;
    }

    public Optional<Wallet> getWallet(long customerId, String currencyName) {
        return Optional.of(walletRepository.findWallet(customerId, Currency.valueOf(currencyName.toUpperCase())));
    }

    public Optional<List<Wallet>> getAllWallets(long customerId) {
        return Optional.of(walletRepository.findAllByCustomer(this.findCustomerById(customerId)));
    }

    public Optional<Wallet> deposit(long customerId, String currencyName, double amount) {
        Optional<Wallet> wallet = getWalletForDepositWithdraw(customerId, currencyName.toUpperCase());
        this.saveTransactionToDatabase(wallet.get(), amount, TransactionType.DEPOSIT);
        wallet.get().setBalance(wallet.get().getBalance() + amount);
        this.walletRepository.save(wallet.get());
        return wallet;
    }

    public Optional<Wallet> withdraw(long customerId, String currencyName, double amount) {
        Optional<Wallet> wallet = getWalletForDepositWithdraw(customerId, currencyName.toUpperCase());
        if(amount > wallet.get().getBalance()) {
            throw new NoEnoughBalanceForWithdrawException(ErrorMessageConstants.NO_ENOUGH_BALANCE + " " + amount + " " + wallet.get().getCurrency().getCurrencySign());
        }
        this.saveTransactionToDatabase(wallet.get(), amount, TransactionType.WITHDRAW);
        wallet.get().setBalance(wallet.get().getBalance() - amount);
        this.walletRepository.save(wallet.get());
        return wallet;
    }

    private void saveTransactionToDatabase(Wallet wallet, double amount, TransactionType transactionType) {
        WalletServiceTransactionLogger transactionLogger = new WalletServiceTransactionLogger();
        transactionLogger.setCustomerId(wallet.getCustomer().getId());
        transactionLogger.setBalance_before(wallet.getBalance());
        transactionLogger.setBalance_after(wallet.getBalance() + amount);
        transactionLogger.setTransaction_amount(amount);
        transactionLogger.setTransaction_currency(wallet.getCurrency());
        transactionLogger.setTransaction_date(LocalDate.now());
        transactionLogger.setClientIpAdress(clientRequestInfo.getClientIpAdress());
        transactionLogger.setClientReqUrl(clientRequestInfo.getClientIpAdress()+clientRequestInfo.getClientUrl());
        transactionLogger.setClientSessionActivityId(clientRequestInfo.getSessionActivityId());
        transactionLogger.setTransactionType(transactionType);
        this.transactionLoggerRepository.save(transactionLogger);
    }

    private Optional<Wallet> getWalletForDepositWithdraw(long customerId, String currencyName) {
        Customer foundCustomer = this.findCustomerById(customerId);
        Optional<Wallet> wallet = foundCustomer.getWallets().stream().filter(w -> w.getCurrency().equals(Currency.valueOf(currencyName.toUpperCase()))).findFirst();
        if (!wallet.isPresent()) {
            throw new BadRequestException("Customer : " + foundCustomer.getFirstName() + " " + foundCustomer.getSecondName() + " does not have wallet with currency : " + currencyName.toUpperCase());
        }
        return wallet;
    }

    public Page<List<WalletServiceTransactionLogger>> getAllTransactionsWithDate(String transactionDate, Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        WalletAppValidatorUtil.validateTransactionDate(transactionDate, formatter);
        LocalDate transaction_date = LocalDate.parse(transactionDate, formatter);
        return this.transactionLoggerRepository.findAllByTransactionDate(transaction_date, pageable);
    }
}
