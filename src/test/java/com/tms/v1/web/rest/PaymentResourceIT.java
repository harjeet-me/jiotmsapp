package com.tms.v1.web.rest;

import com.tms.v1.JiotmsappApp;
import com.tms.v1.domain.Payment;
import com.tms.v1.repository.PaymentRepository;
import com.tms.v1.service.PaymentService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tms.v1.domain.enumeration.PayMode;
/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@SpringBootTest(classes = JiotmsappApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentResourceIT {

    private static final String DEFAULT_INVOICE_NO = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAY_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAY_REF_NO = "BBBBBBBBBB";

    private static final PayMode DEFAULT_MODE = PayMode.CHECK;
    private static final PayMode UPDATED_MODE = PayMode.CASH;

    private static final Double DEFAULT_AMMOUNT = 1D;
    private static final Double UPDATED_AMMOUNT = 2D;

    private static final Double DEFAULT_UNUSED_AMMOUNT = 1D;
    private static final Double UPDATED_UNUSED_AMMOUNT = 2D;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .invoiceNo(DEFAULT_INVOICE_NO)
            .payDate(DEFAULT_PAY_DATE)
            .payRefNo(DEFAULT_PAY_REF_NO)
            .mode(DEFAULT_MODE)
            .ammount(DEFAULT_AMMOUNT)
            .unusedAmmount(DEFAULT_UNUSED_AMMOUNT)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY);
        return payment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .invoiceNo(UPDATED_INVOICE_NO)
            .payDate(UPDATED_PAY_DATE)
            .payRefNo(UPDATED_PAY_REF_NO)
            .mode(UPDATED_MODE)
            .ammount(UPDATED_AMMOUNT)
            .unusedAmmount(UPDATED_UNUSED_AMMOUNT)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        restPaymentMockMvc.perform(post("/api/payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getInvoiceNo()).isEqualTo(DEFAULT_INVOICE_NO);
        assertThat(testPayment.getPayDate()).isEqualTo(DEFAULT_PAY_DATE);
        assertThat(testPayment.getPayRefNo()).isEqualTo(DEFAULT_PAY_REF_NO);
        assertThat(testPayment.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testPayment.getAmmount()).isEqualTo(DEFAULT_AMMOUNT);
        assertThat(testPayment.getUnusedAmmount()).isEqualTo(DEFAULT_UNUSED_AMMOUNT);
        assertThat(testPayment.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPayment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPayment.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testPayment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceNo").value(hasItem(DEFAULT_INVOICE_NO)))
            .andExpect(jsonPath("$.[*].payDate").value(hasItem(DEFAULT_PAY_DATE.toString())))
            .andExpect(jsonPath("$.[*].payRefNo").value(hasItem(DEFAULT_PAY_REF_NO)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())))
            .andExpect(jsonPath("$.[*].ammount").value(hasItem(DEFAULT_AMMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].unusedAmmount").value(hasItem(DEFAULT_UNUSED_AMMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.invoiceNo").value(DEFAULT_INVOICE_NO))
            .andExpect(jsonPath("$.payDate").value(DEFAULT_PAY_DATE.toString()))
            .andExpect(jsonPath("$.payRefNo").value(DEFAULT_PAY_REF_NO))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()))
            .andExpect(jsonPath("$.ammount").value(DEFAULT_AMMOUNT.doubleValue()))
            .andExpect(jsonPath("$.unusedAmmount").value(DEFAULT_UNUSED_AMMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .invoiceNo(UPDATED_INVOICE_NO)
            .payDate(UPDATED_PAY_DATE)
            .payRefNo(UPDATED_PAY_REF_NO)
            .mode(UPDATED_MODE)
            .ammount(UPDATED_AMMOUNT)
            .unusedAmmount(UPDATED_UNUSED_AMMOUNT)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .updatedBy(UPDATED_UPDATED_BY);

        restPaymentMockMvc.perform(put("/api/payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayment)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getInvoiceNo()).isEqualTo(UPDATED_INVOICE_NO);
        assertThat(testPayment.getPayDate()).isEqualTo(UPDATED_PAY_DATE);
        assertThat(testPayment.getPayRefNo()).isEqualTo(UPDATED_PAY_REF_NO);
        assertThat(testPayment.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testPayment.getAmmount()).isEqualTo(UPDATED_AMMOUNT);
        assertThat(testPayment.getUnusedAmmount()).isEqualTo(UPDATED_UNUSED_AMMOUNT);
        assertThat(testPayment.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayment.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPayment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc.perform(put("/api/payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
