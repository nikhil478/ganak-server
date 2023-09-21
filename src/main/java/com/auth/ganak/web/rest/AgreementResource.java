package com.auth.ganak.web.rest;

import com.auth.ganak.domain.Agreement;
import com.auth.ganak.repository.AgreementRepository;
import com.auth.ganak.service.JwtService;
import com.auth.ganak.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.auth.ganak.domain.Agreement}.
 */
@RestController
@RequestMapping("/api")
public class AgreementResource {

    private final Logger log = LoggerFactory.getLogger(AgreementResource.class);

    private static final String ENTITY_NAME = "agreement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgreementRepository agreementRepository;
    private final JwtService jwtService;

    public AgreementResource(AgreementRepository agreementRepository, JwtService jwtService) {
        this.agreementRepository = agreementRepository;
        this.jwtService = jwtService;
    }

    /**
     * {@code POST  /agreements} : Create a new agreement.
     *
     * @param agreement the agreement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agreement, or with status {@code 400 (Bad Request)} if the agreement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agreements")
    public ResponseEntity<Agreement> createAgreement(@RequestBody Agreement agreement) throws URISyntaxException {
        log.debug("REST request to save Agreement : {}", agreement);
        if (agreement.getId() != null) {
            throw new BadRequestAlertException("A new agreement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agreement result = agreementRepository.save(agreement);
        agreement.setDateCreated(LocalDate.now());
        return ResponseEntity.created(new URI("/api/agreements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agreements} : Updates an existing agreement.
     *
     * @param agreement the agreement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agreement,
     * or with status {@code 400 (Bad Request)} if the agreement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agreement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agreements")
    public ResponseEntity<Agreement> updateAgreement(@RequestBody Agreement agreement) throws URISyntaxException {
        log.debug("REST request to update Agreement : {}", agreement);
        if (agreement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        agreement.setDateUpdated(LocalDate.now());
        Agreement result = agreementRepository.save(agreement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agreement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agreements} : get all the agreements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agreements in body.
     */
    @GetMapping("/agreements")
    public List<Agreement> getAllAgreements() {
        log.debug("REST request to get all Agreements");
        return agreementRepository.findAll();
    }

    /**
     * {@code GET  /agreements} : get all flagged agreements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agreements in body.
     */
    @GetMapping("/agreements/flagged")
    public List<Agreement> getAllFlaggedAgreements() {
        log.debug("REST request to get all Agreements");
        return agreementRepository.findAllFlaggedAgreement();
    }

    /**
     * {@code GET  /agreements/:id} : get the "id" agreement.
     *
     * @param id the id of the agreement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agreement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agreements/{id}")
    public ResponseEntity<Agreement> getAgreement(@PathVariable Long id) {
        log.debug("REST request to get Agreement : {}", id);
        Optional<Agreement> agreement = agreementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agreement);
    }

    /**
     * {@code DELETE  /agreements/:id} : delete the "id" agreement.
     *
     * @param id the id of the agreement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agreements/{id}")
    public ResponseEntity<Void> deleteAgreement(@PathVariable Long id) {
        log.debug("REST request to delete Agreement : {}", id);
        agreementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
