package com.assignment.web.rest;

import com.assignment.service.PassBookService;
import com.assignment.web.rest.errors.BadRequestAlertException;
import com.assignment.service.dto.PassBookDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.assignment.domain.PassBook}.
 */
@RestController
@RequestMapping("/api")
public class PassBookResource {

    private final Logger log = LoggerFactory.getLogger(PassBookResource.class);

    private static final String ENTITY_NAME = "passBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PassBookService passBookService;

    public PassBookResource(PassBookService passBookService) {
        this.passBookService = passBookService;
    }

    /**
     * {@code POST  /pass-books} : Create a new passBook.
     *
     * @param passBookDTO the passBookDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new passBookDTO, or with status {@code 400 (Bad Request)} if the passBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pass-books")
    public ResponseEntity<PassBookDTO> createPassBook(@RequestBody PassBookDTO passBookDTO) throws URISyntaxException {
        log.debug("REST request to save PassBook : {}", passBookDTO);
        if (passBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new passBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PassBookDTO result = passBookService.save(passBookDTO);
        return ResponseEntity.created(new URI("/api/pass-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pass-books} : Updates an existing passBook.
     *
     * @param passBookDTO the passBookDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passBookDTO,
     * or with status {@code 400 (Bad Request)} if the passBookDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the passBookDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pass-books")
    public ResponseEntity<PassBookDTO> updatePassBook(@RequestBody PassBookDTO passBookDTO) throws URISyntaxException {
        log.debug("REST request to update PassBook : {}", passBookDTO);
        if (passBookDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PassBookDTO result = passBookService.save(passBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, passBookDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pass-books} : get all the passBooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of passBooks in body.
     */
    @GetMapping("/pass-books")
    public ResponseEntity<List<PassBookDTO>> getAllPassBooks(Pageable pageable) {
        log.debug("REST request to get a page of PassBooks");
        Page<PassBookDTO> page = passBookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pass-books/:id} : get the "id" passBook.
     *
     * @param id the id of the passBookDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the passBookDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pass-books/{id}")
    public ResponseEntity<PassBookDTO> getPassBook(@PathVariable Long id) {
        log.debug("REST request to get PassBook : {}", id);
        Optional<PassBookDTO> passBookDTO = passBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(passBookDTO);
    }

    /**
     * {@code DELETE  /pass-books/:id} : delete the "id" passBook.
     *
     * @param id the id of the passBookDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pass-books/{id}")
    public ResponseEntity<Void> deletePassBook(@PathVariable Long id) {
        log.debug("REST request to delete PassBook : {}", id);
        passBookService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
