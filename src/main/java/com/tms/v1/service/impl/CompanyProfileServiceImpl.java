package com.tms.v1.service.impl;

import com.tms.v1.service.CompanyProfileService;
import com.tms.v1.domain.CompanyProfile;
import com.tms.v1.repository.CompanyProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyProfile}.
 */
@Service
@Transactional
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileServiceImpl.class);

    private final CompanyProfileRepository companyProfileRepository;

    public CompanyProfileServiceImpl(CompanyProfileRepository companyProfileRepository) {
        this.companyProfileRepository = companyProfileRepository;
    }

    /**
     * Save a companyProfile.
     *
     * @param companyProfile the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompanyProfile save(CompanyProfile companyProfile) {
        log.debug("Request to save CompanyProfile : {}", companyProfile);
        return companyProfileRepository.save(companyProfile);
    }

    /**
     * Get all the companyProfiles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompanyProfile> findAll() {
        log.debug("Request to get all CompanyProfiles");
        return companyProfileRepository.findAll();
    }


    /**
     * Get one companyProfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyProfile> findOne(Long id) {
        log.debug("Request to get CompanyProfile : {}", id);
        return companyProfileRepository.findById(id);
    }

    /**
     * Delete the companyProfile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyProfile : {}", id);

        companyProfileRepository.deleteById(id);
    }
}
