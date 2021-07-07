package com.roofstacks.walletservice.service;

import com.roofstacks.walletservice.dto.CustomerDTO;
import com.roofstacks.walletservice.dto.WalletDTO;
import com.roofstacks.walletservice.exception.BadRequestException;
import com.roofstacks.walletservice.exception.WalletAlreadyExistsException;
import com.roofstacks.walletservice.mapper.CustomerMapper;
import com.roofstacks.walletservice.mapper.WalletMapper;
import com.roofstacks.walletservice.model.Customer;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.model.WalletServiceTransactionLogger;
import com.roofstacks.walletservice.model.enums.Currency;
import com.roofstacks.walletservice.repository.CustomerRepository;
import com.roofstacks.walletservice.repository.WalletRepository;
import com.roofstacks.walletservice.repository.WalletServiceTransactionLoggerRepository;
import com.roofstacks.walletservice.utils.ClientRequestInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletAppServiceTest {

    @Mock
    private WalletRepository mockWalletRepository;
    @Mock
    private CustomerRepository mockCustomerRepository;
    @Mock
    private CustomerMapper mockCustomerMapper;
    @Mock
    private WalletMapper mockWalletMapper;
    @Mock
    private WalletServiceTransactionLoggerRepository mockTransactionLoggerRepository;
    @Mock
    private ClientRequestInfo mockClientRequestInfo;
    @InjectMocks
    WalletAppService walletAppService;

    @Test
    void save_customer() {
        // given
        Customer customer = new Customer();
        customer.setSsid(11111L);
        customer.setEmail("a@a.com");
        customer.setFirstName("Koray");
        when(mockCustomerRepository.selectExistsSsid(anyLong())).thenReturn(Boolean.FALSE);
        when(mockCustomerMapper.mapFromCustomerDTOtoCustomer(any())).thenReturn(customer);
        when(mockCustomerRepository.save(any())).thenReturn(customer);

        // when
        CustomerDTO dto = new CustomerDTO();
        Customer actual = this.walletAppService.save_customer(dto).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(customer, actual),
                () -> assertEquals(11111L, actual.getSsid())
        );
    }

    @Test
    void save_wallet() {
        // given
        Wallet expected = new Wallet();
        expected.setCurrency(Currency.TRY);
        Customer customer = new Customer();
        customer.setId(2L);
        expected.setCustomer(customer);
        when(mockWalletMapper.mapFromWalletDTOtowallet(any())).thenReturn(expected);
        when(mockWalletRepository.selectExistsWalletWithSameCurrency(any(), anyLong())).thenReturn(Boolean.FALSE);
        when(mockWalletRepository.save(any())).thenReturn(expected);

        // when
        WalletDTO dto = new WalletDTO();
        Wallet actual = this.walletAppService.save_wallet(dto).get();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void save_wallet_throws_WalletAlreadyExistsException() {
        // given
        Wallet expected = new Wallet();
        expected.setCurrency(Currency.TRY);
        Customer customer = new Customer();
        customer.setId(2L);
        expected.setCustomer(customer);
        when(mockWalletMapper.mapFromWalletDTOtowallet(any())).thenReturn(expected);
        when(mockWalletRepository.selectExistsWalletWithSameCurrency(any(), anyLong())).thenReturn(Boolean.TRUE);

        // when
        WalletDTO dto = new WalletDTO();
        Executable executable = () -> this.walletAppService.save_wallet(dto).get();

        // then
        assertThrows(WalletAlreadyExistsException.class, executable);
    }

    @Test
    void deposit() {
        // given
        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.TRY);
        List<Wallet> walletList = Arrays.asList(wallet);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setWallets(walletList);
        wallet.setCustomer(customer);
        wallet.setBalance(100.0);
        when(mockCustomerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(mockClientRequestInfo.getClientIpAdress()).thenReturn("test");
        when(mockClientRequestInfo.getClientUrl()).thenReturn("test");
        when(mockClientRequestInfo.getSessionActivityId()).thenReturn("test");
        WalletServiceTransactionLogger transactionLogger = new WalletServiceTransactionLogger();
        when(mockTransactionLoggerRepository.save(any())).thenReturn(transactionLogger);
        when(mockWalletRepository.save(any())).thenReturn(wallet);

        // when
        Wallet actual = this.walletAppService.deposit(2L, "TRY", 100.0).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(200.0, actual.getBalance())
        );
    }

    @Test
    void deposit_throws_BadRequestException() {
        // given
        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.EUR);
        List<Wallet> walletList = Arrays.asList(wallet);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setWallets(walletList);
        wallet.setCustomer(customer);
        wallet.setBalance(100.0);
        when(mockCustomerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // when
        Executable executable = () -> this.walletAppService.deposit(2L, "TRY", 100.0).get();

        // then
        assertThrows(BadRequestException.class, executable);
    }

    @Test
    void withdraw() {
        // given
        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.TRY);
        List<Wallet> walletList = Arrays.asList(wallet);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setWallets(walletList);
        wallet.setCustomer(customer);
        wallet.setBalance(100.0);
        when(mockCustomerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(mockClientRequestInfo.getClientIpAdress()).thenReturn("test");
        when(mockClientRequestInfo.getClientUrl()).thenReturn("test");
        when(mockClientRequestInfo.getSessionActivityId()).thenReturn("test");
        WalletServiceTransactionLogger transactionLogger = new WalletServiceTransactionLogger();
        when(mockTransactionLoggerRepository.save(any())).thenReturn(transactionLogger);
        when(mockWalletRepository.save(any())).thenReturn(wallet);

        // when
        Wallet actual = this.walletAppService.withdraw(2L, "TRY", 20.0).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(80.0, actual.getBalance())
        );
    }
}