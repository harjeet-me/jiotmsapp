package com.tms.v1.service.impl;

import com.tms.v1.service.EquipmentService;
import com.tms.v1.domain.Equipment;
import com.tms.v1.repository.EquipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Equipment}.
 */
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    private final Logger log = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    /**
     * Save a equipment.
     *
     * @param equipment the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Equipment save(Equipment equipment) {
        log.debug("Request to save Equipment : {}", equipment);
        return equipmentRepository.save(equipment);
    }

    /**
     * Get all the equipment.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Equipment> findAll() {
        log.debug("Request to get all Equipment");
        return equipmentRepository.findAll();
    }


    /**
     * Get one equipment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Equipment> findOne(Long id) {
        log.debug("Request to get Equipment : {}", id);
        return equipmentRepository.findById(id);
    }

    /**
     * Delete the equipment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Equipment : {}", id);

        equipmentRepository.deleteById(id);
    }
}
