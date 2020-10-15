package com.assignment.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.assignment.service.dto.PassBookDTO;

/**
 * Service Interface for managing {@link com.assignment.domain.PassBook}.
 */
public interface PassBookService {

    /**
     * Save a passBook.
     *
     * @param passBookDTO the entity to save.
     * @return the persisted entity.
     */
    PassBookDTO save(PassBookDTO passBookDTO);

    /**
     * Get all the passBooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PassBookDTO> findAll(Pageable pageable);


    /**
     * Get the "id" passBook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PassBookDTO> findOne(Long id);

    /**
     * Delete the "id" passBook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
