package com.assignment.service.mapper;


import com.assignment.domain.*;
import com.assignment.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {WalletMapper.class, PassBookMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "passbook.id", target = "passbookId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "receiverId", target = "receiver")
    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "passbookId", target = "passbook")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
