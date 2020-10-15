package com.assignment.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.NovopayassignmentApp;
import com.assignment.domain.PassBook;
import com.assignment.repository.PassBookRepository;
import com.assignment.service.PassBookService;
import com.assignment.service.dto.PassBookDTO;
import com.assignment.service.mapper.PassBookMapper;

/**
 * Integration tests for the {@link PassBookResource} REST controller.
 */
@SpringBootTest(classes = NovopayassignmentApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PassBookResourceIT {

    @Autowired
    private PassBookRepository passBookRepository;

    @Autowired
    private PassBookMapper passBookMapper;

    @Autowired
    private PassBookService passBookService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassBookMockMvc;

    private PassBook passBook;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PassBook createEntity(EntityManager em) {
        PassBook passBook = new PassBook();
        return passBook;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PassBook createUpdatedEntity(EntityManager em) {
        PassBook passBook = new PassBook();
        return passBook;
    }

    @BeforeEach
    public void initTest() {
        passBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createPassBook() throws Exception {
        int databaseSizeBeforeCreate = passBookRepository.findAll().size();
        // Create the PassBook
        PassBookDTO passBookDTO = passBookMapper.toDto(passBook);
        restPassBookMockMvc.perform(post("/api/pass-books").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(passBookDTO)))
            .andExpect(status().isCreated());

        // Validate the PassBook in the database
        List<PassBook> passBookList = passBookRepository.findAll();
        assertThat(passBookList).hasSize(databaseSizeBeforeCreate + 1);
        PassBook testPassBook = passBookList.get(passBookList.size() - 1);
    }

    @Test
    @Transactional
    public void createPassBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = passBookRepository.findAll().size();

        // Create the PassBook with an existing ID
        passBook.setId(1L);
        PassBookDTO passBookDTO = passBookMapper.toDto(passBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassBookMockMvc.perform(post("/api/pass-books").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(passBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PassBook in the database
        List<PassBook> passBookList = passBookRepository.findAll();
        assertThat(passBookList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPassBooks() throws Exception {
        // Initialize the database
        passBookRepository.saveAndFlush(passBook);

        // Get all the passBookList
        restPassBookMockMvc.perform(get("/api/pass-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passBook.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPassBook() throws Exception {
        // Initialize the database
        passBookRepository.saveAndFlush(passBook);

        // Get the passBook
        restPassBookMockMvc.perform(get("/api/pass-books/{id}", passBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passBook.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPassBook() throws Exception {
        // Get the passBook
        restPassBookMockMvc.perform(get("/api/pass-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePassBook() throws Exception {
        // Initialize the database
        passBookRepository.saveAndFlush(passBook);

        int databaseSizeBeforeUpdate = passBookRepository.findAll().size();

        // Update the passBook
        PassBook updatedPassBook = passBookRepository.findById(passBook.getId()).get();
        // Disconnect from session so that the updates on updatedPassBook are not directly saved in db
        em.detach(updatedPassBook);
        PassBookDTO passBookDTO = passBookMapper.toDto(updatedPassBook);

        restPassBookMockMvc.perform(put("/api/pass-books").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(passBookDTO)))
            .andExpect(status().isOk());

        // Validate the PassBook in the database
        List<PassBook> passBookList = passBookRepository.findAll();
        assertThat(passBookList).hasSize(databaseSizeBeforeUpdate);
        PassBook testPassBook = passBookList.get(passBookList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPassBook() throws Exception {
        int databaseSizeBeforeUpdate = passBookRepository.findAll().size();

        // Create the PassBook
        PassBookDTO passBookDTO = passBookMapper.toDto(passBook);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassBookMockMvc.perform(put("/api/pass-books").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(passBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PassBook in the database
        List<PassBook> passBookList = passBookRepository.findAll();
        assertThat(passBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePassBook() throws Exception {
        // Initialize the database
        passBookRepository.saveAndFlush(passBook);

        int databaseSizeBeforeDelete = passBookRepository.findAll().size();

        // Delete the passBook
        restPassBookMockMvc.perform(delete("/api/pass-books/{id}", passBook.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PassBook> passBookList = passBookRepository.findAll();
        assertThat(passBookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
