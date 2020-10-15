package com.assignment.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.assignment.service.dto.AddMoneyDto;
import com.assignment.service.dto.PassBookDTO;
import com.assignment.service.dto.WalletDTO;

/**
 * Service Interface for managing {@link com.assignment.domain.Wallet}.
 */
public interface WalletService {

    /**
     * Save a wallet.
     *
     * @param walletDTO the entity to save.
     * @return the persisted entity.
     */
    WalletDTO create(WalletDTO walletDTO);

    /**
     * Get all the wallets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WalletDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wallet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WalletDTO> findOne(Long id);

    /**
     * Delete the "id" wallet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	WalletDTO addMoney(AddMoneyDto addMoneyDto);

	WalletDTO save(WalletDTO walletDTO);

	Optional<PassBookDTO> findPassBookForWalletId(Long walletId);
}
