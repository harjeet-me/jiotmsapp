package com.tms.v1.service.impl;

import com.tms.v1.service.InvoiceReportService;
import com.tms.v1.domain.InvoiceReport;
import com.tms.v1.repository.InvoiceReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InvoiceReport}.
 */
@Service
@Transactional
public class InvoiceReportServiceImpl implements InvoiceReportService {

    private final Logger log = LoggerFactory.getLogger(InvoiceReportServiceImpl.class);

    private final InvoiceReportRepository invoiceReportRepository;

    public InvoiceReportServiceImpl(InvoiceReportRepository invoiceReportRepository) {
        this.invoiceReportRepository = invoiceReportRepository;
    }

    /**
     * Save a invoiceReport.
     *
     * @param invoiceReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceReport save(InvoiceReport invoiceReport) {
        log.debug("Request to save InvoiceReport : {}", invoiceReport);
        return invoiceReportRepository.save(invoiceReport);
    }

    /**
     * Get all the invoiceReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceReport> findAll() {
        log.debug("Request to get all InvoiceReports");
        return invoiceReportRepository.findAllWithEagerRelationships();
    }


    /**
     * Get all the invoiceReports with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<InvoiceReport> findAllWithEagerRelationships(Pageable pageable) {
        return invoiceReportRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one invoiceReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceReport> findOne(Long id) {
        log.debug("Request to get InvoiceReport : {}", id);
        return invoiceReportRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the invoiceReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceReport : {}", id);

        invoiceReportRepository.deleteById(id);
    }
}
