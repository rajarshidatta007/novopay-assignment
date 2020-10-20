package com.assignment.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.domain.PassBook;
import com.assignment.domain.Wallet;
import com.assignment.repository.WalletRepository;
import com.assignment.service.PassBookService;
import com.assignment.service.WalletService;
import com.assignment.service.dto.AddMoneyDto;
import com.assignment.service.dto.PassBookDTO;
import com.assignment.service.dto.WalletDTO;
import com.assignment.service.mapper.PassBookMapper;
import com.assignment.service.mapper.WalletMapper;

/**
 * Service Implementation for managing {@link Wallet}.
 */
@Service
@Transactional
public class WalletServiceImpl implements WalletService {

	private final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

	private final WalletRepository walletRepository;

	private final WalletMapper walletMapper;

	private final PassBookMapper passBookMapper;

	private final PassBookService passBookService;

	public WalletServiceImpl(WalletRepository walletRepository, WalletMapper walletMapper,
			PassBookMapper passBookMapper, PassBookService passBookService) {
		this.walletRepository = walletRepository;
		this.walletMapper = walletMapper;
		this.passBookMapper = passBookMapper;
		this.passBookService = passBookService;
	}

	@Override
	public WalletDTO create(WalletDTO walletDTO) {
		log.debug("Request to create Wallet : {}", walletDTO);
		Wallet wallet = walletMapper.toEntity(walletDTO);

		PassBook passBook = new PassBook();

		wallet.setPassbook(passBook);
		wallet = walletRepository.save(wallet);
		return walletMapper.toDto(wallet);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<WalletDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Wallets");
		return walletRepository.findAll(pageable).map(walletMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<WalletDTO> findOne(Long id) {
		log.debug("Request to get Wallet : {}", id);
		return walletRepository.findById(id).map(walletMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Wallet : {}", id);
		walletRepository.deleteById(id);
	}

	@Override
	public WalletDTO addMoney(AddMoneyDto addMoneyDto) {
		Wallet wallet = walletRepository.getOne(addMoneyDto.getWalletId());

		wallet.setBalance(wallet.getBalance().add(addMoneyDto.getAmount()));

		return walletMapper.toDto(wallet);
	}

	@Override
	public WalletDTO save(WalletDTO walletDTO) {
		log.debug("Request to save Wallet : {}", walletDTO);
		Wallet wallet = walletMapper.toEntity(walletDTO);

		wallet = walletRepository.save(wallet);
		return walletMapper.toDto(wallet);
	}

	@Override
	public Optional<PassBookDTO> findPassBookForWalletId(Long walletId) {
		Optional<Wallet> wallet = walletRepository.findById(walletId);
		return wallet.isPresent() ? (wallet.get().getPassbook() != null && wallet.get().getPassbook().getId() != null
				? passBookService.findOne(wallet.get().getPassbook().getId())
				: Optional.empty()) : Optional.empty();
	}
}
