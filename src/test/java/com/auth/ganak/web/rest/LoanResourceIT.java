package com.auth.ganak.web.rest;

import com.auth.ganak.JhipsterApp;
import com.auth.ganak.domain.Loan;
import com.auth.ganak.repository.LoanRepository;
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
 * Integration tests for the {@Link LoanResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
public class LoanResourceIT {

    private static final String DEFAULT_LOANEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOANEE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DOB = 1L;
    private static final Long UPDATED_DOB = 2L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DISBURSEMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DISBURSEMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;

    private static final Double DEFAULT_LOAN_TENURE = 1D;
    private static final Double UPDATED_LOAN_TENURE = 2D;

    private static final Double DEFAULT_INTEREST_RATE = 1D;
    private static final Double UPDATED_INTEREST_RATE = 2D;

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
    private LoanRepository loanRepository;

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

    private MockMvc restLoanMockMvc;

    private Loan loan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanResource loanResource = new LoanResource(loanRepository);
        this.restLoanMockMvc = MockMvcBuilders.standaloneSetup(loanResource)
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
    public static Loan createEntity(EntityManager em) {
        Loan loan = new Loan()
            .loaneeName(DEFAULT_LOANEE_NAME)
            .dob(DEFAULT_DOB)
            .address(DEFAULT_ADDRESS)
            .contact(DEFAULT_CONTACT)
            .disbursementDate(DEFAULT_DISBURSEMENT_DATE)
            .loanAmount(DEFAULT_LOAN_AMOUNT)
            .loanTenure(DEFAULT_LOAN_TENURE)
            .interestRate(DEFAULT_INTEREST_RATE)
            .zkpCode(DEFAULT_ZKP_CODE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .createdById(DEFAULT_CREATED_BY_ID)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .updatedById(DEFAULT_UPDATED_BY_ID);
        return loan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Loan createUpdatedEntity(EntityManager em) {
        Loan loan = new Loan()
            .loaneeName(UPDATED_LOANEE_NAME)
            .dob(UPDATED_DOB)
            .address(UPDATED_ADDRESS)
            .contact(UPDATED_CONTACT)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanTenure(UPDATED_LOAN_TENURE)
            .interestRate(UPDATED_INTEREST_RATE)
            .zkpCode(UPDATED_ZKP_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdById(UPDATED_CREATED_BY_ID)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .updatedById(UPDATED_UPDATED_BY_ID);
        return loan;
    }

    @BeforeEach
    public void initTest() {
        loan = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoan() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isCreated());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate + 1);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getLoaneeName()).isEqualTo(DEFAULT_LOANEE_NAME);
        assertThat(testLoan.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testLoan.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLoan.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testLoan.getDisbursementDate()).isEqualTo(DEFAULT_DISBURSEMENT_DATE);
        assertThat(testLoan.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testLoan.getLoanTenure()).isEqualTo(DEFAULT_LOAN_TENURE);
        assertThat(testLoan.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testLoan.getZkpCode()).isEqualTo(DEFAULT_ZKP_CODE);
        assertThat(testLoan.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testLoan.getCreatedById()).isEqualTo(DEFAULT_CREATED_BY_ID);
        assertThat(testLoan.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testLoan.getUpdatedById()).isEqualTo(DEFAULT_UPDATED_BY_ID);
    }

    @Test
    @Transactional
    public void createLoanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanRepository.findAll().size();

        // Create the Loan with an existing ID
        loan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLoans() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get all the loanList
        restLoanMockMvc.perform(get("/api/loans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loan.getId().intValue())))
            .andExpect(jsonPath("$.[*].loaneeName").value(hasItem(DEFAULT_LOANEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].disbursementDate").value(hasItem(DEFAULT_DISBURSEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanTenure").value(hasItem(DEFAULT_LOAN_TENURE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].zkpCode").value(hasItem(DEFAULT_ZKP_CODE.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].createdById").value(hasItem(DEFAULT_CREATED_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].updatedById").value(hasItem(DEFAULT_UPDATED_BY_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", loan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loan.getId().intValue()))
            .andExpect(jsonPath("$.loaneeName").value(DEFAULT_LOANEE_NAME.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.disbursementDate").value(DEFAULT_DISBURSEMENT_DATE.toString()))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loanTenure").value(DEFAULT_LOAN_TENURE.doubleValue()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.zkpCode").value(DEFAULT_ZKP_CODE.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.createdById").value(DEFAULT_CREATED_BY_ID.intValue()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.updatedById").value(DEFAULT_UPDATED_BY_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLoan() throws Exception {
        // Get the loan
        restLoanMockMvc.perform(get("/api/loans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Update the loan
        Loan updatedLoan = loanRepository.findById(loan.getId()).get();
        // Disconnect from session so that the updates on updatedLoan are not directly saved in db
        em.detach(updatedLoan);
        updatedLoan
            .loaneeName(UPDATED_LOANEE_NAME)
            .dob(UPDATED_DOB)
            .address(UPDATED_ADDRESS)
            .contact(UPDATED_CONTACT)
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanTenure(UPDATED_LOAN_TENURE)
            .interestRate(UPDATED_INTEREST_RATE)
            .zkpCode(UPDATED_ZKP_CODE)
            .dateCreated(UPDATED_DATE_CREATED)
            .createdById(UPDATED_CREATED_BY_ID)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .updatedById(UPDATED_UPDATED_BY_ID);

        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoan)))
            .andExpect(status().isOk());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);
        Loan testLoan = loanList.get(loanList.size() - 1);
        assertThat(testLoan.getLoaneeName()).isEqualTo(UPDATED_LOANEE_NAME);
        assertThat(testLoan.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testLoan.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLoan.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testLoan.getDisbursementDate()).isEqualTo(UPDATED_DISBURSEMENT_DATE);
        assertThat(testLoan.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testLoan.getLoanTenure()).isEqualTo(UPDATED_LOAN_TENURE);
        assertThat(testLoan.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testLoan.getZkpCode()).isEqualTo(UPDATED_ZKP_CODE);
        assertThat(testLoan.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testLoan.getCreatedById()).isEqualTo(UPDATED_CREATED_BY_ID);
        assertThat(testLoan.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testLoan.getUpdatedById()).isEqualTo(UPDATED_UPDATED_BY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLoan() throws Exception {
        int databaseSizeBeforeUpdate = loanRepository.findAll().size();

        // Create the Loan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loan)))
            .andExpect(status().isBadRequest());

        // Validate the Loan in the database
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoan() throws Exception {
        // Initialize the database
        loanRepository.saveAndFlush(loan);

        int databaseSizeBeforeDelete = loanRepository.findAll().size();

        // Delete the loan
        restLoanMockMvc.perform(delete("/api/loans/{id}", loan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Loan> loanList = loanRepository.findAll();
        assertThat(loanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loan.class);
        Loan loan1 = new Loan();
        loan1.setId(1L);
        Loan loan2 = new Loan();
        loan2.setId(loan1.getId());
        assertThat(loan1).isEqualTo(loan2);
        loan2.setId(2L);
        assertThat(loan1).isNotEqualTo(loan2);
        loan1.setId(null);
        assertThat(loan1).isNotEqualTo(loan2);
    }
}
