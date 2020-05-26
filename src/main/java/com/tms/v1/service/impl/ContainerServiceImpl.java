package com.tms.v1.service.impl;

import com.tms.v1.service.ContainerService;
import com.tms.v1.domain.Container;
import com.tms.v1.repository.ContainerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Container}.
 */
@Service
@Transactional
public class ContainerServiceImpl implements ContainerService {

    private final Logger log = LoggerFactory.getLogger(ContainerServiceImpl.class);

    private final ContainerRepository containerRepository;

    public ContainerServiceImpl(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    /**
     * Save a container.
     *
     * @param container the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Container save(Container container) {
        log.debug("Request to save Container : {}", container);
        return containerRepository.save(container);
    }

    /**
     * Get all the containers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Container> findAll() {
        log.debug("Request to get all Containers");
        return containerRepository.findAll();
    }


    /**
     * Get one container by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Container> findOne(Long id) {
        log.debug("Request to get Container : {}", id);
        return containerRepository.findById(id);
    }

    /**
     * Delete the container by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Container : {}", id);

        containerRepository.deleteById(id);
    }
}
