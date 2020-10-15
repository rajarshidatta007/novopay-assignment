package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.domain.PassBook;

/**
 * Spring Data  repository for the PassBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassBookRepository extends JpaRepository<PassBook, Long> {
}
