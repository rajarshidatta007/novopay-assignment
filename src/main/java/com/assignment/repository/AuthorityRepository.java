package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
