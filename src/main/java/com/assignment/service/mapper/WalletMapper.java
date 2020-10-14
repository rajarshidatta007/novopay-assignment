package com.assignment.service.mapper;


import com.assignment.domain.*;
import com.assignment.service.dto.WalletDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wallet} and its DTO {@link WalletDTO}.
 */
@Mapper(componentModel = "spring", uses = {PassBookMapper.class})
public interface WalletMapper extends EntityMapper<WalletDTO, Wallet> {

    @Mapping(source = "passbook.id", target = "passbookId")
    WalletDTO toDto(Wallet wallet);

    @Mapping(source = "passbookId", target = "passbook")
    Wallet toEntity(WalletDTO walletDTO);

    default Wallet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wallet wallet = new Wallet();
        wallet.setId(id);
        return wallet;
    }
}
