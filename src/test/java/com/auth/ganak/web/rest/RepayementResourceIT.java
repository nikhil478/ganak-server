package com.auth.ganak.web.rest;

import com.auth.ganak.JhipsterApp;
import com.auth.ganak.domain.Repayement;
import com.auth.ganak.repository.RepayementRepository;
import com.auth.ganak.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.auth.ganak.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RepayementResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class RepayementResourceIT {

    private static final Double DEFAULT_REPAYEMENT_AMOUNT = 1D;
    private static final Double UPDATED_REPAYEMENT_AMOUNT = 2D;

    private static final LocalDate DEFAULT_REPAYEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPAYEMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_OUTSTANDING_BALANCE = 1D;
    private static final Double UPDATED_OUTSTANDING_BALANCE = 2D;

    private static final String DEFAULT_REPAYEMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_REPAYEMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ZKP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZKP_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATED_BY_ID = 1L;
    private static final Long UPDATED_CREATED_BY_ID = 2L;

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATED_BY_ID = 1L;
    private static final Long UPDATED_UPDATED_BY_ID = 2L;

    @Autowired
    private RepayementRepository repayementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRepayementMockMvc;

    private Repayement repayement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RepayementResource repayementResource = new RepayementResource(repayementRepository);
        this.restRepayementMockMvc = MockMvcBuilders.standaloneSetup(repayementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repayement createEntity(EntityManager em) {
        Repayement repayement = new Repayement()
            .repayementAmount(DEFAULT_REPAYEMENT_AMOUNT)
            .repayementDate(DEFAULT_REPAYEMENT_DATE)
            .outstandingBalance(DEFAULT_OUTSTANDING_BALANCE)
            .repayementStatus(DEFAULT_REPAYEMENT_STATUS)
            .zkpCode(DEFAULT_ZKP_CODE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .createdById(DEFAULT_CREATED_BY_ID)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .updatedById(DEFAULT_UPDATED_BY_ID);
        return repayement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Repayement createUpdatedEntity(EntityManager em) {
        Repayement repayement = new Repayement()
            .repayementAmount(UPDATED_REPAYEMENT_AMOUNT)
            .repayementDate(UPDATED_REPAYEMENT_DATE)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .repayementStatus(UPDATED_REPAYEMENT_STATUS)
            .zkpCode(UPDATED_ZKP_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdById(UPDATED_CREATED_BY_ID)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .updatedById(UPDATED_UPDATED_BY_ID);
        return repayement;
    }

    @BeforeEach
    public void initTest() {
        repayement = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepayement() throws Exception {
        int databaseSizeBeforeCreate = repayementRepository.findAll().size();

        // Create the Repayement
        restRepayementMockMvc.perform(post("/api/repayements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repayement)))
            .andExpect(status().isCreated());

        // Validate the Repayement in the database
        List<Repayement> repayementList = repayementRepository.findAll();
        assertThat(repayementList).hasSize(databaseSizeBeforeCreate + 1);
        Repayement testRepayement = repayementList.get(repayementList.size() - 1);
        assertThat(testRepayement.getRepayementAmount()).isEqualTo(DEFAULT_REPAYEMENT_AMOUNT);
        assertThat(testRepayement.getRepayementDate()).isEqualTo(DEFAULT_REPAYEMENT_DATE);
        assertThat(testRepayement.getOutstandingBalance()).isEqualTo(DEFAULT_OUTSTANDING_BALANCE);
        assertThat(testRepayement.getRepayementStatus()).isEqualTo(DEFAULT_REPAYEMENT_STATUS);
        assertThat(testRepayement.getZkpCode()).isEqualTo(DEFAULT_ZKP_CODE);
        assertThat(testRepayement.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testRepayement.getCreatedById()).isEqualTo(DEFAULT_CREATED_BY_ID);
        assertThat(testRepayement.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testRepayement.getUpdatedById()).isEqualTo(DEFAULT_UPDATED_BY_ID);
    }

    @Test
    @Transactional
    public void createRepayementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repayementRepository.findAll().size();

        // Create the Repayement with an existing ID
        repayement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepayementMockMvc.perform(post("/api/repayements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repayement)))
            .andExpect(status().isBadRequest());

        // Validate the Repayement in the database
        List<Repayement> repayementList = repayementRepository.findAll();
        assertThat(repayementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRepayements() throws Exception {
        // Initialize the database
        repayementRepository.saveAndFlush(repayement);

        // Get all the repayementList
        restRepayementMockMvc.perform(get("/api/repayements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repayement.getId().intValue())))
            .andExpect(jsonPath("$.[*].repayementAmount").value(hasItem(DEFAULT_REPAYEMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].repayementDate").value(hasItem(DEFAULT_REPAYEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].outstandingBalance").value(hasItem(DEFAULT_OUTSTANDING_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].repayementStatus").value(hasItem(DEFAULT_REPAYEMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].zkpCode").value(hasItem(DEFAULT_ZKP_CODE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].createdById").value(hasItem(DEFAULT_CREATED_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedById").value(hasItem(DEFAULT_UPDATED_BY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getRepayement() throws Exception {
        // Initialize the database
        repayementRepository.saveAndFlush(repayement);

        // Get the repayement
        restRepayementMockMvc.perform(get("/api/repayements/{id}", repayement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(repayement.getId().intValue()))
            .andExpect(jsonPath("$.repayementAmount").value(DEFAULT_REPAYEMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.repayementDate").value(DEFAULT_REPAYEMENT_DATE.toString()))
            .andExpect(jsonPath("$.outstandingBalance").value(DEFAULT_OUTSTANDING_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.repayementStatus").value(DEFAULT_REPAYEMENT_STATUS.toString()))
            .andExpect(jsonPath("$.zkpCode").value(DEFAULT_ZKP_CODE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.createdById").value(DEFAULT_CREATED_BY_ID.intValue()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.updatedById").value(DEFAULT_UPDATED_BY_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRepayement() throws Exception {
        // Get the repayement
        restRepayementMockMvc.perform(get("/api/repayements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepayement() throws Exception {
        // Initialize the database
        repayementRepository.saveAndFlush(repayement);

        int databaseSizeBeforeUpdate = repayementRepository.findAll().size();

        // Update the repayement
        Repayement updatedRepayement = repayementRepository.findById(repayement.getId()).get();
        // Disconnect from session so that the updates on updatedRepayement are not directly saved in db
        em.detach(updatedRepayement);
        updatedRepayement
            .repayementAmount(UPDATED_REPAYEMENT_AMOUNT)
            .repayementDate(UPDATED_REPAYEMENT_DATE)
            .outstandingBalance(UPDATED_OUTSTANDING_BALANCE)
            .repayementStatus(UPDATED_REPAYEMENT_STATUS)
            .zkpCode(UPDATED_ZKP_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdById(UPDATED_CREATED_BY_ID)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .updatedById(UPDATED_UPDATED_BY_ID);

        restRepayementMockMvc.perform(put("/api/repayements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRepayement)))
            .andExpect(status().isOk());

        // Validate the Repayement in the database
        List<Repayement> repayementList = repayementRepository.findAll();
        assertThat(repayementList).hasSize(databaseSizeBeforeUpdate);
        Repayement testRepayement = repayementList.get(repayementList.size() - 1);
        assertThat(testRepayement.getRepayementAmount()).isEqualTo(UPDATED_REPAYEMENT_AMOUNT);
        assertThat(testRepayement.getRepayementDate()).isEqualTo(UPDATED_REPAYEMENT_DATE);
        assertThat(testRepayement.getOutstandingBalance()).isEqualTo(UPDATED_OUTSTANDING_BALANCE);
        assertThat(testRepayement.getRepayementStatus()).isEqualTo(UPDATED_REPAYEMENT_STATUS);
        assertThat(testRepayement.getZkpCode()).isEqualTo(UPDATED_ZKP_CODE);
        assertThat(testRepayement.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testRepayement.getCreatedById()).isEqualTo(UPDATED_CREATED_BY_ID);
        assertThat(testRepayement.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testRepayement.getUpdatedById()).isEqualTo(UPDATED_UPDATED_BY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingRepayement() throws Exception {
        int databaseSizeBeforeUpdate = repayementRepository.findAll().size();

        // Create the Repayement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepayementMockMvc.perform(put("/api/repayements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repayement)))
            .andExpect(status().isBadRequest());

        // Validate the Repayement in the database
        List<Repayement> repayementList = repayementRepository.findAll();
        assertThat(repayementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRepayement() throws Exception {
        // Initialize the database
        repayementRepository.saveAndFlush(repayement);

        int databaseSizeBeforeDelete = repayementRepository.findAll().size();

        // Delete the repayement
        restRepayementMockMvc.perform(delete("/api/repayements/{id}", repayement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Repayement> repayementList = repayementRepository.findAll();
        assertThat(repayementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Repayement.class);
        Repayement repayement1 = new Repayement();
        repayement1.setId(1L);
        Repayement repayement2 = new Repayement();
        repayement2.setId(repayement1.getId());
        assertThat(repayement1).isEqualTo(repayement2);
        repayement2.setId(2L);
        assertThat(repayement1).isNotEqualTo(repayement2);
        repayement1.setId(null);
        assertThat(repayement1).isNotEqualTo(repayement2);
    }
}
