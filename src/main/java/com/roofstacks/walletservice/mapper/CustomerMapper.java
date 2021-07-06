package com.roofstacks.walletservice.mapper;

import com.roofstacks.walletservice.dto.CustomerDTO;
import com.roofstacks.walletservice.model.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer mapFromCustomerDTOtoCustomer(CustomerDTO customerDTO);
    CustomerDTO mapFromCustomerToCustomerDTO(Customer customer);
}
