package com.tms.v1.service.impl;

import com.tms.v1.service.InvoiceItemService;
import com.tms.v1.domain.InvoiceItem;
import com.tms.v1.repository.InvoiceItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InvoiceItem}.
 */
@Service
@Transactional
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemServiceImpl.class);

    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    /**
     * Save a invoiceItem.
     *
     * @param invoiceItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InvoiceItem save(InvoiceItem invoiceItem) {
        log.debug("Request to save InvoiceItem : {}", invoiceItem);
        return invoiceItemRepository.save(invoiceItem);
    }

    /**
     * Get all the invoiceItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceItem> findAll() {
        log.debug("Request to get all InvoiceItems");
        return invoiceItemRepository.findAll();
    }


    /**
     * Get one invoiceItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceItem> findOne(Long id) {
        log.debug("Request to get InvoiceItem : {}", id);
        return invoiceItemRepository.findById(id);
    }

    /**
     * Delete the invoiceItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvoiceItem : {}", id);

        invoiceItemRepository.deleteById(id);
    }
}
