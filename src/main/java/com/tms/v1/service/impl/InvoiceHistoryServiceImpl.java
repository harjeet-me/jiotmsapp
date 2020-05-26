package com.tms.v1.service.impl;

import com.tms.v1.service.InvoiceHistoryService;
import com.tms.v1.domain.InvoiceHistory;
import com.tms.v1.repository.InvoiceHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InvoiceHistory}.
 */
@Service
@Transactional
public class InvoiceHistoryServiceImpl implements InvoiceHistoryService {

    private final Logger log = LoggerFactory.getLogger(InvoiceHistoryServiceImpl.class);

    private final InvoiceHistoryRepository invoiceHistoryRepository;

    public InvoiceHistoryServiceImpl(InvoiceHistoryRepository invoiceHistoryRepository) {
        this.invoiceHistoryRepository = invoiceHistoryRepository;
    }

    /**
     * Save a invoiceHistory.
     *
     * @param invoiceHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceHistory save(InvoiceHistory invoiceHistory) {
        log.debug("Request to save InvoiceHistory : {}", invoiceHistory);
        return invoiceHistoryRepository.save(invoiceHistory);
    }

    /**
     * Get all the invoiceHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceHistory> findAll() {
        log.debug("Request to get all InvoiceHistories");
        return invoiceHistoryRepository.findAll();
    }


    /**
     * Get one invoiceHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceHistory> findOne(Long id) {
        log.debug("Request to get InvoiceHistory : {}", id);
        return invoiceHistoryRepository.findById(id);
    }

    /**
     * Delete the invoiceHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceHistory : {}", id);

        invoiceHistoryRepository.deleteById(id);
    }
}
