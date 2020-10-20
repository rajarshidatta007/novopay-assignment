package com.assignment.service.mapper;


import org.mapstruct.Mapper;

import com.assignment.domain.PassBook;
import com.assignment.service.dto.PassBookDTO;

/**
 * Mapper for the entity {@link PassBook} and its DTO {@link PassBookDTO}.
 */
@Mapper(componentModel = "spring")
public interface PassBookMapper extends EntityMapper<PassBookDTO, PassBook> {


	/*
	 * @Mapping(target = "transactions", ignore = true)
	 * 
	 * @Mapping(target = "removeTransaction", ignore = true)
	 */
    PassBook toEntity(PassBookDTO passBookDTO);

    default PassBook fromId(Long id) {
        if (id == null) {
            return null;
        }
        PassBook passBook = new PassBook();
        passBook.setId(id);
        return passBook;
    }
}
