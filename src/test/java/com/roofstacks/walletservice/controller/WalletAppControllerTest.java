package com.roofstacks.walletservice.controller;

import com.roofstacks.walletservice.dto.WalletDTO;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.model.enums.Currency;
import com.roofstacks.walletservice.service.WalletAppService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletAppControllerTest {

    @Mock
    WalletAppService mockWalletAppService;
    @InjectMocks
    WalletAppController walletAppController;

    @Test
    void saveWallet() {
        // given
        Wallet wallet = new Wallet();
        Optional<Wallet> expected = Optional.of(wallet);
        when(mockWalletAppService.save_wallet(any())).thenReturn(expected);

        // when
        WalletDTO dto = new WalletDTO();
        dto.setBalance(100.0);
        Wallet actual = walletAppController.saveWallet(dto).getBody();

        // then
        assertEquals(expected.get(), actual);
    }

    @Test
    void getAllWallets() {
        // given
        Wallet wallet1 = new Wallet();
        wallet1.setCurrency(Currency.TRY);
        Wallet wallet2 = new Wallet();
        wallet2.setCurrency(Currency.USD);
        List<Wallet> walletList = Arrays.asList(wallet1, wallet2);
        Optional<List<Wallet>> expected = Optional.of(walletList);
        when(mockWalletAppService.getAllWallets(anyLong())).thenReturn(expected);

        // when
        List<Wallet> actual = this.walletAppController.getAllWallets(1).getBody();

        // then
        assertAll(
                () -> assertTrue(actual.size() == 2),
                () -> assertEquals(actual.get(0).getCurrency(), Currency.TRY),
                () -> assertEquals(actual.get(1).getCurrency(), Currency.USD)
        );
    }

    @Test
    void getWallet() {
        // given
        Wallet wallet1 = new Wallet();
        wallet1.setCurrency(Currency.TRY);
        Optional<Wallet> expected = Optional.of(wallet1);
        when(mockWalletAppService.getWallet(anyLong(), eq("TRY"))).thenReturn(expected);

        // when
        Wallet actual = this.walletAppController.getWallet(1, "TRY").getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getCurrency(), Currency.TRY)
        );
    }

    @Test
    void depositToWallet() {
        // given
        Wallet wallet1 = new Wallet();
        wallet1.setCurrency(Currency.TRY);
        wallet1.setBalance(500.0);
        Optional<Wallet> expected = Optional.of(wallet1);
        when(mockWalletAppService.deposit(anyLong(),anyString(), anyDouble())).thenReturn(expected);

        // when
        Wallet actual = this.walletAppController.depositToWallet(1, "TRY", 250.0).getBody();

        // then
        assertEquals(500.0, actual.getBalance());
    }

    @Test
    void withdrawFromWallet() {
        // given
        Wallet wallet1 = new Wallet();
        wallet1.setCurrency(Currency.TRY);
        wallet1.setBalance(250.0);
        Optional<Wallet> expected = Optional.of(wallet1);
        when(mockWalletAppService.withdraw(anyLong(),anyString(), anyDouble())).thenReturn(expected);

        // when
        Wallet actual = this.walletAppController.withdrawFromWallet(1, "TRY", 150.0).getBody();

        // then
        assertEquals(250.0, actual.getBalance());
    }

}