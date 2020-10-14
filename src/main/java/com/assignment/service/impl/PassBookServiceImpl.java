package com.assignment.service.impl;

import com.assignment.service.PassBookService;
import com.assignment.domain.PassBook;
import com.assignment.repository.PassBookRepository;
import com.assignment.service.dto.PassBookDTO;
import com.assignment.service.mapper.PassBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PassBook}.
 */
@Service
@Transactional
public class PassBookServiceImpl implements PassBookService {

    private final Logger log = LoggerFactory.getLogger(PassBookServiceImpl.class);

    private final PassBookRepository passBookRepository;

    private final PassBookMapper passBookMapper;

    public PassBookServiceImpl(PassBookRepository passBookRepository, PassBookMapper passBookMapper) {
        this.passBookRepository = passBookRepository;
        this.passBookMapper = passBookMapper;
    }

    @Override
    public PassBookDTO save(PassBookDTO passBookDTO) {
        log.debug("Request to save PassBook : {}", passBookDTO);
        PassBook passBook = passBookMapper.toEntity(passBookDTO);
        passBook = passBookRepository.save(passBook);
        return passBookMapper.toDto(passBook);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PassBookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PassBooks");
        return passBookRepository.findAll(pageable)
            .map(passBookMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PassBookDTO> findOne(Long id) {
        log.debug("Request to get PassBook : {}", id);
        return passBookRepository.findById(id)
            .map(passBookMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PassBook : {}", id);
        passBookRepository.deleteById(id);
    }
}
