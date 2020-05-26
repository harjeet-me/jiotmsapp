package com.tms.v1.service.impl;

import com.tms.v1.service.TransactionsRecordService;
import com.tms.v1.domain.TransactionsRecord;
import com.tms.v1.repository.TransactionsRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionsRecord}.
 */
@Service
@Transactional
public class TransactionsRecordServiceImpl implements TransactionsRecordService {

    private final Logger log = LoggerFactory.getLogger(TransactionsRecordServiceImpl.class);

    private final TransactionsRecordRepository transactionsRecordRepository;

    public TransactionsRecordServiceImpl(TransactionsRecordRepository transactionsRecordRepository) {
        this.transactionsRecordRepository = transactionsRecordRepository;
    }

    /**
     * Save a transactionsRecord.
     *
     * @param transactionsRecord the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TransactionsRecord save(TransactionsRecord transactionsRecord) {
        log.debug("Request to save TransactionsRecord : {}", transactionsRecord);
        return transactionsRecordRepository.save(transactionsRecord);
    }

    /**
     * Get all the transactionsRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionsRecord> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionsRecords");
        return transactionsRecordRepository.findAll(pageable);
    }


    /**
     * Get one transactionsRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionsRecord> findOne(Long id) {
        log.debug("Request to get TransactionsRecord : {}", id);
        return transactionsRecordRepository.findById(id);
    }

    /**
     * Delete the transactionsRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionsRecord : {}", id);

        transactionsRecordRepository.deleteById(id);
    }
}
