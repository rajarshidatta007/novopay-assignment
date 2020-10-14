package com.assignment.repository;

import com.assignment.domain.PassBook;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PassBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassBookRepository extends JpaRepository<PassBook, Long> {
}
