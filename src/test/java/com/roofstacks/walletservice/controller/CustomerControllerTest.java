package com.roofstacks.walletservice.controller;

import com.roofstacks.walletservice.dto.CustomerDTO;
import com.roofstacks.walletservice.model.Customer;
import com.roofstacks.walletservice.service.WalletAppService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    WalletAppService mockWalletAppService;
    @InjectMocks
    CustomerController customerController;

    @Test
    void saveCustomer() {
        // given
        Customer customer = new Customer();
        customer.setEmail("a@a.com");
        customer.setSsid(11111L);
        Optional<Customer> expected = Optional.of(customer);
        when(mockWalletAppService.save_customer(any())).thenReturn(expected);

        // when
        CustomerDTO dto = new CustomerDTO();
        Customer actual = this.customerController.saveCustomer(dto).getBody();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.get(), actual),
                () -> assertEquals(11111L, actual.getSsid())
        );
    }
}