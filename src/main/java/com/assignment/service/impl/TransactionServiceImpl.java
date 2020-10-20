package com.assignment.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.domain.Transaction;
import com.assignment.domain.Wallet;
import com.assignment.repository.TransactionRepository;
import com.assignment.service.TransactionService;
import com.assignment.service.dto.TransactionType;
import com.assignment.service.dto.TransactionDTO;
import com.assignment.service.dto.WalletDTO;
import com.assignment.service.mapper.TransactionMapper;
import com.assignment.service.mapper.WalletMapper;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private final TransactionRepository transactionRepository;

	private final TransactionMapper transactionMapper;

	private final WalletServiceImpl walletServiceImpl;

	private final WalletMapper walletMapper;

	public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper,
			WalletServiceImpl walletServiceImpl, WalletMapper walletMapper) {
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.walletServiceImpl = walletServiceImpl;
		this.walletMapper = walletMapper;
	}

	@Override
	public TransactionDTO save(TransactionDTO transactionDTO) {
		log.debug("Request to save Transaction : {}", transactionDTO);

		Transaction transaction = transactionMapper.toEntity(transactionDTO);
		transaction.setTransactionType(transactionDTO.getTransactionType());
		transaction.setCharges(fetchCharges(transaction.getAmount()));
		transaction = transactionRepository.save(transaction);

		switch (transaction.getTransactionType()) {
		case CREDIT: {
			updateCreditScenario(transaction);
			break;
		}
		case DEBIT: {
			updateDebitScenario(transaction);
			break;
		}
		default: {
			break;
		}
		}

		return transactionMapper.toDto(transaction);
	}

	private void updateDebitScenario(Transaction transaction) {
		Wallet receiver = walletMapper.toEntity(walletServiceImpl.findOne(transaction.getReceiver().getId()).get());
		Wallet sender = walletMapper.toEntity(walletServiceImpl.findOne(transaction.getSender().getId()).get());

		receiver.setBalance(receiver.getBalance() != null ? receiver.getBalance().add(transaction.getAmount())
				: transaction.getAmount());

		receiver.getPassbook().addTransaction(transaction);
		walletServiceImpl.save(walletMapper.toDto(receiver));

		sender.setBalance(sender.getBalance() != null
				? sender.getBalance().subtract(transaction.getAmount().add(transaction.getCharges()))
				: new BigDecimal(0).subtract(transaction.getAmount().add(transaction.getCharges())));

		sender.getPassbook().addTransaction(transaction);
		walletServiceImpl.save(walletMapper.toDto(sender));
	}

	private void updateCreditScenario(Transaction transaction) {
		Wallet receiver = walletMapper.toEntity(walletServiceImpl.findOne(transaction.getReceiver().getId()).get());
		Wallet sender = walletMapper.toEntity(walletServiceImpl.findOne(transaction.getSender().getId()).get());

		receiver.setBalance(receiver.getBalance() != null
				? receiver.getBalance().add(transaction.getAmount().subtract(transaction.getCharges()))
				: transaction.getAmount().subtract(transaction.getCharges()));

		receiver.getPassbook().addTransaction(transaction);
		walletServiceImpl.save(walletMapper.toDto(receiver));

		sender.setBalance(sender.getBalance() != null ? sender.getBalance().subtract(transaction.getAmount())
				: new BigDecimal(0).subtract(transaction.getAmount()));

		sender.getPassbook().addTransaction(transaction);
		walletServiceImpl.save(walletMapper.toDto(sender));
	}

	private BigDecimal fetchCharges(BigDecimal amount) {
		return amount.multiply(new BigDecimal("0.9975"));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TransactionDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Transactions");
		return transactionRepository.findAll(pageable).map(transactionMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TransactionDTO> findOne(Long id) {
		log.debug("Request to get Transaction : {}", id);
		return transactionRepository.findById(id).map(transactionMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Transaction : {}", id);
		transactionRepository.deleteById(id);
	}
}
