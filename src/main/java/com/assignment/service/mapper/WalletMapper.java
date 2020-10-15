package com.assignment.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.assignment.domain.Wallet;
import com.assignment.service.dto.WalletDTO;

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
