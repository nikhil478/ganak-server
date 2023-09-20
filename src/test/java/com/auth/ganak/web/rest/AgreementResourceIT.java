package com.auth.ganak.web.rest;

import com.auth.ganak.JhipsterApp;
import com.auth.ganak.domain.Agreement;
import com.auth.ganak.repository.AgreementRepository;
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
 * Integration tests for the {@Link AgreementResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class AgreementResourceIT {

    private static final String DEFAULT_FINTECH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FINTECH_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_AGREEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AGREEMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_FLDG_PERECENTAGE = 1D;
    private static final Double UPDATED_FLDG_PERECENTAGE = 2D;

    private static final Double DEFAULT_FLDG_AMOUNT = 1D;
    private static final Double UPDATED_FLDG_AMOUNT = 2D;

    private static final String DEFAULT_GUARANTEE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GUARANTEE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GUARANTEE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_GUARANTEE_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_AGREEMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_AGREEMENT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATED_BY_ID = 1L;
    private static final Long UPDATED_CREATED_BY_ID = 2L;

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATED_BY_ID = 1L;
    private static final Long UPDATED_UPDATED_BY_ID = 2L;

    @Autowired
    private AgreementRepository agreementRepository;

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

    private MockMvc restAgreementMockMvc;

    private Agreement agreement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgreementResource agreementResource = new AgreementResource(agreementRepository);
        this.restAgreementMockMvc = MockMvcBuilders.standaloneSetup(agreementResource)
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
    public static Agreement createEntity(EntityManager em) {
        Agreement agreement = new Agreement()
            .fintechName(DEFAULT_FINTECH_NAME)
            .agreementDate(DEFAULT_AGREEMENT_DATE)
            .fldgPerecentage(DEFAULT_FLDG_PERECENTAGE)
            .fldgAmount(DEFAULT_FLDG_AMOUNT)
            .guaranteeType(DEFAULT_GUARANTEE_TYPE)
            .guaranteeDetails(DEFAULT_GUARANTEE_DETAILS)
            .agreementNo(DEFAULT_AGREEMENT_NO)
            .dateCreated(DEFAULT_DATE_CREATED)
            .createdById(DEFAULT_CREATED_BY_ID)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .updatedById(DEFAULT_UPDATED_BY_ID);
        return agreement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agreement createUpdatedEntity(EntityManager em) {
        Agreement agreement = new Agreement()
            .fintechName(UPDATED_FINTECH_NAME)
            .agreementDate(UPDATED_AGREEMENT_DATE)
            .fldgPerecentage(UPDATED_FLDG_PERECENTAGE)
            .fldgAmount(UPDATED_FLDG_AMOUNT)
            .guaranteeType(UPDATED_GUARANTEE_TYPE)
            .guaranteeDetails(UPDATED_GUARANTEE_DETAILS)
            .agreementNo(UPDATED_AGREEMENT_NO)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdById(UPDATED_CREATED_BY_ID)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .updatedById(UPDATED_UPDATED_BY_ID);
        return agreement;
    }

    @BeforeEach
    public void initTest() {
        agreement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgreement() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();

        // Create the Agreement
        restAgreementMockMvc.perform(post("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isCreated());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate + 1);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.getFintechName()).isEqualTo(DEFAULT_FINTECH_NAME);
        assertThat(testAgreement.getAgreementDate()).isEqualTo(DEFAULT_AGREEMENT_DATE);
        assertThat(testAgreement.getFldgPerecentage()).isEqualTo(DEFAULT_FLDG_PERECENTAGE);
        assertThat(testAgreement.getFldgAmount()).isEqualTo(DEFAULT_FLDG_AMOUNT);
        assertThat(testAgreement.getGuaranteeType()).isEqualTo(DEFAULT_GUARANTEE_TYPE);
        assertThat(testAgreement.getGuaranteeDetails()).isEqualTo(DEFAULT_GUARANTEE_DETAILS);
        assertThat(testAgreement.getAgreementNo()).isEqualTo(DEFAULT_AGREEMENT_NO);
        assertThat(testAgreement.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAgreement.getCreatedById()).isEqualTo(DEFAULT_CREATED_BY_ID);
        assertThat(testAgreement.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testAgreement.getUpdatedById()).isEqualTo(DEFAULT_UPDATED_BY_ID);
    }

    @Test
    @Transactional
    public void createAgreementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();

        // Create the Agreement with an existing ID
        agreement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgreementMockMvc.perform(post("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAgreements() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get all the agreementList
        restAgreementMockMvc.perform(get("/api/agreements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].fintechName").value(hasItem(DEFAULT_FINTECH_NAME.toString())))
            .andExpect(jsonPath("$.[*].agreementDate").value(hasItem(DEFAULT_AGREEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fldgPerecentage").value(hasItem(DEFAULT_FLDG_PERECENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fldgAmount").value(hasItem(DEFAULT_FLDG_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].guaranteeType").value(hasItem(DEFAULT_GUARANTEE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].guaranteeDetails").value(hasItem(DEFAULT_GUARANTEE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].agreementNo").value(hasItem(DEFAULT_AGREEMENT_NO.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].createdById").value(hasItem(DEFAULT_CREATED_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedById").value(hasItem(DEFAULT_UPDATED_BY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", agreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agreement.getId().intValue()))
            .andExpect(jsonPath("$.fintechName").value(DEFAULT_FINTECH_NAME.toString()))
            .andExpect(jsonPath("$.agreementDate").value(DEFAULT_AGREEMENT_DATE.toString()))
            .andExpect(jsonPath("$.fldgPerecentage").value(DEFAULT_FLDG_PERECENTAGE.doubleValue()))
            .andExpect(jsonPath("$.fldgAmount").value(DEFAULT_FLDG_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.guaranteeType").value(DEFAULT_GUARANTEE_TYPE.toString()))
            .andExpect(jsonPath("$.guaranteeDetails").value(DEFAULT_GUARANTEE_DETAILS.toString()))
            .andExpect(jsonPath("$.agreementNo").value(DEFAULT_AGREEMENT_NO.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.createdById").value(DEFAULT_CREATED_BY_ID.intValue()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.updatedById").value(DEFAULT_UPDATED_BY_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAgreement() throws Exception {
        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Update the agreement
        Agreement updatedAgreement = agreementRepository.findById(agreement.getId()).get();
        // Disconnect from session so that the updates on updatedAgreement are not directly saved in db
        em.detach(updatedAgreement);
        updatedAgreement
            .fintechName(UPDATED_FINTECH_NAME)
            .agreementDate(UPDATED_AGREEMENT_DATE)
            .fldgPerecentage(UPDATED_FLDG_PERECENTAGE)
            .fldgAmount(UPDATED_FLDG_AMOUNT)
            .guaranteeType(UPDATED_GUARANTEE_TYPE)
            .guaranteeDetails(UPDATED_GUARANTEE_DETAILS)
            .agreementNo(UPDATED_AGREEMENT_NO)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdById(UPDATED_CREATED_BY_ID)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .updatedById(UPDATED_UPDATED_BY_ID);

        restAgreementMockMvc.perform(put("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgreement)))
            .andExpect(status().isOk());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.getFintechName()).isEqualTo(UPDATED_FINTECH_NAME);
        assertThat(testAgreement.getAgreementDate()).isEqualTo(UPDATED_AGREEMENT_DATE);
        assertThat(testAgreement.getFldgPerecentage()).isEqualTo(UPDATED_FLDG_PERECENTAGE);
        assertThat(testAgreement.getFldgAmount()).isEqualTo(UPDATED_FLDG_AMOUNT);
        assertThat(testAgreement.getGuaranteeType()).isEqualTo(UPDATED_GUARANTEE_TYPE);
        assertThat(testAgreement.getGuaranteeDetails()).isEqualTo(UPDATED_GUARANTEE_DETAILS);
        assertThat(testAgreement.getAgreementNo()).isEqualTo(UPDATED_AGREEMENT_NO);
        assertThat(testAgreement.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAgreement.getCreatedById()).isEqualTo(UPDATED_CREATED_BY_ID);
        assertThat(testAgreement.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testAgreement.getUpdatedById()).isEqualTo(UPDATED_UPDATED_BY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAgreement() throws Exception {
        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Create the Agreement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgreementMockMvc.perform(put("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeDelete = agreementRepository.findAll().size();

        // Delete the agreement
        restAgreementMockMvc.perform(delete("/api/agreements/{id}", agreement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agreement.class);
        Agreement agreement1 = new Agreement();
        agreement1.setId(1L);
        Agreement agreement2 = new Agreement();
        agreement2.setId(agreement1.getId());
        assertThat(agreement1).isEqualTo(agreement2);
        agreement2.setId(2L);
        assertThat(agreement1).isNotEqualTo(agreement2);
        agreement1.setId(null);
        assertThat(agreement1).isNotEqualTo(agreement2);
    }
}
