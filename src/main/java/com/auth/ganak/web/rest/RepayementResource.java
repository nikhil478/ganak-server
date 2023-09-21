package com.auth.ganak.web.rest;

import com.auth.ganak.domain.Repayement;
import com.auth.ganak.repository.RepayementRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.auth.ganak.domain.Repayement}.
 */
@RestController
@RequestMapping("/api")
public class RepayementResource {

    private final Logger log = LoggerFactory.getLogger(RepayementResource.class);

    private static final String ENTITY_NAME = "repayement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepayementRepository repayementRepository;

    public RepayementResource(RepayementRepository repayementRepository) {
        this.repayementRepository = repayementRepository;
    }

    /**
     * {@code POST  /repayements} : Create a new repayement.
     *
     * @param repayement the repayement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new repayement, or with status {@code 400 (Bad Request)} if the repayement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/repayements")
    public ResponseEntity<Repayement> createRepayement(@RequestBody Repayement repayement) throws URISyntaxException {
        log.debug("REST request to save Repayement : {}", repayement);
        if (repayement.getId() != null) {
            throw new BadRequestAlertException("A new repayement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        repayement.setDateCreated(LocalDate.now());
        Repayement result = repayementRepository.save(repayement);
        return ResponseEntity.created(new URI("/api/repayements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /repayements} : Updates an existing repayement.
     *
     * @param repayement the repayement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated repayement,
     * or with status {@code 400 (Bad Request)} if the repayement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the repayement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/repayements")
    public ResponseEntity<Repayement> updateRepayement(@RequestBody Repayement repayement) throws URISyntaxException {
        log.debug("REST request to update Repayement : {}", repayement);
        if (repayement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        repayement.setDateUpdated(LocalDate.now());
        Repayement result = repayementRepository.save(repayement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, repayement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /repayements} : get all the repayements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of repayements in body.
     */
    @GetMapping("/repayements")
    public List<Repayement> getAllRepayements() {
        log.debug("REST request to get all Repayements");
        return repayementRepository.findAll();
    }

    /**
     * {@code GET  /repayements/:id} : get the "id" repayement.
     *
     * @param id the id of the repayement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the repayement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/repayements/{id}")
    public ResponseEntity<Repayement> getRepayement(@PathVariable Long id) {
        log.debug("REST request to get Repayement : {}", id);
        Optional<Repayement> repayement = repayementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(repayement);
    }

    /**
     * {@code DELETE  /repayements/:id} : delete the "id" repayement.
     *
     * @param id the id of the repayement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/repayements/{id}")
    public ResponseEntity<Void> deleteRepayement(@PathVariable Long id) {
        log.debug("REST request to delete Repayement : {}", id);
        repayementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
