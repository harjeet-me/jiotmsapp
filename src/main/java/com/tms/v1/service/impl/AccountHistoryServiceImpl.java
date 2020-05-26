package com.tms.v1.service.impl;

import com.tms.v1.service.AccountHistoryService;
import com.tms.v1.domain.AccountHistory;
import com.tms.v1.repository.AccountHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountHistory}.
 */
@Service
@Transactional
public class AccountHistoryServiceImpl implements AccountHistoryService {

    private final Logger log = LoggerFactory.getLogger(AccountHistoryServiceImpl.class);

    private final AccountHistoryRepository accountHistoryRepository;

    public AccountHistoryServiceImpl(AccountHistoryRepository accountHistoryRepository) {
        this.accountHistoryRepository = accountHistoryRepository;
    }

    /**
     * Save a accountHistory.
     *
     * @param accountHistory the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccountHistory save(AccountHistory accountHistory) {
        log.debug("Request to save AccountHistory : {}", accountHistory);
        return accountHistoryRepository.save(accountHistory);
    }

    /**
     * Get all the accountHistories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountHistory> findAll() {
        log.debug("Request to get all AccountHistories");
        return accountHistoryRepository.findAll();
    }


    /**
     * Get one accountHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccountHistory> findOne(Long id) {
        log.debug("Request to get AccountHistory : {}", id);
        return accountHistoryRepository.findById(id);
    }

    /**
     * Delete the accountHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountHistory : {}", id);

        accountHistoryRepository.deleteById(id);
    }
}
