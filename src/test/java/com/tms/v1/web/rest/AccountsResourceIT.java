package com.tms.v1.web.rest;

import com.tms.v1.JiotmsappApp;
import com.tms.v1.domain.Accounts;
import com.tms.v1.repository.AccountsRepository;
import com.tms.v1.service.AccountsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountsResource} REST controller.
 */
@SpringBootTest(classes = JiotmsappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountsResourceIT {

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final Double DEFAULT_OVER_30 = 1D;
    private static final Double UPDATED_OVER_30 = 2D;

    private static final Double DEFAULT_OVER_60 = 1D;
    private static final Double UPDATED_OVER_60 = 2D;

    private static final Double DEFAULT_OVER_90 = 1D;
    private static final Double UPDATED_OVER_90 = 2D;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Accounts accounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .balance(DEFAULT_BALANCE)
            .over30(DEFAULT_OVER_30)
            .over60(DEFAULT_OVER_60)
            .over90(DEFAULT_OVER_90)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return accounts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createUpdatedEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .balance(UPDATED_BALANCE)
            .over30(UPDATED_OVER_30)
            .over60(UPDATED_OVER_60)
            .over90(UPDATED_OVER_90)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return accounts;
    }

    @BeforeEach
    public void initTest() {
        accounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccounts() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();
        // Create the Accounts
        restAccountsMockMvc.perform(post("/api/accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isCreated());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate + 1);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testAccounts.getOver30()).isEqualTo(DEFAULT_OVER_30);
        assertThat(testAccounts.getOver60()).isEqualTo(DEFAULT_OVER_60);
        assertThat(testAccounts.getOver90()).isEqualTo(DEFAULT_OVER_90);
        assertThat(testAccounts.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAccounts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAccounts.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testAccounts.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createAccountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();

        // Create the Accounts with an existing ID
        accounts.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc.perform(post("/api/accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList
        restAccountsMockMvc.perform(get("/api/accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].over30").value(hasItem(DEFAULT_OVER_30.doubleValue())))
            .andExpect(jsonPath("$.[*].over60").value(hasItem(DEFAULT_OVER_60.doubleValue())))
            .andExpect(jsonPath("$.[*].over90").value(hasItem(DEFAULT_OVER_90.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get the accounts
        restAccountsMockMvc.perform(get("/api/accounts/{id}", accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accounts.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.over30").value(DEFAULT_OVER_30.doubleValue()))
            .andExpect(jsonPath("$.over60").value(DEFAULT_OVER_60.doubleValue()))
            .andExpect(jsonPath("$.over90").value(DEFAULT_OVER_90.doubleValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get("/api/accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccounts() throws Exception {
        // Initialize the database
        accountsService.save(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts
        Accounts updatedAccounts = accountsRepository.findById(accounts.getId()).get();
        // Disconnect from session so that the updates on updatedAccounts are not directly saved in db
        em.detach(updatedAccounts);
        updatedAccounts
            .balance(UPDATED_BALANCE)
            .over30(UPDATED_OVER_30)
            .over60(UPDATED_OVER_60)
            .over90(UPDATED_OVER_90)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restAccountsMockMvc.perform(put("/api/accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccounts)))
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testAccounts.getOver30()).isEqualTo(UPDATED_OVER_30);
        assertThat(testAccounts.getOver60()).isEqualTo(UPDATED_OVER_60);
        assertThat(testAccounts.getOver90()).isEqualTo(UPDATED_OVER_90);
        assertThat(testAccounts.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccounts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccounts.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAccounts.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc.perform(put("/api/accounts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccounts() throws Exception {
        // Initialize the database
        accountsService.save(accounts);

        int databaseSizeBeforeDelete = accountsRepository.findAll().size();

        // Delete the accounts
        restAccountsMockMvc.perform(delete("/api/accounts/{id}", accounts.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
