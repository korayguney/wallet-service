package com.roofstacks.walletservice.mapper;

import com.roofstacks.walletservice.dto.WalletDTO;
import com.roofstacks.walletservice.model.Wallet;
import com.roofstacks.walletservice.service.WalletAppService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class WalletMapper {

    @Autowired
    protected WalletAppService walletService;

    @Mapping(target = "customer", expression = "java(walletService.findCustomerById(walletDTO.getCustomerId()))")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.now())")
    public abstract Wallet mapFromWalletDTOtowallet(WalletDTO walletDTO);
    public abstract WalletDTO mapFromWalletToWalletDTO(Wallet wallet);
}
