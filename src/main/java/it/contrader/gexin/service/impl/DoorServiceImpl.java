package it.contrader.gexin.service.impl;

import it.contrader.gexin.service.DoorService;
import it.contrader.gexin.domain.Door;
import it.contrader.gexin.repository.DoorRepository;
import it.contrader.gexin.service.dto.DoorDTO;
import it.contrader.gexin.service.mapper.DoorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Door.
 */
@Service
@Transactional
public class DoorServiceImpl implements DoorService {

    private final Logger log = LoggerFactory.getLogger(DoorServiceImpl.class);

    private final DoorRepository doorRepository;

    private final DoorMapper doorMapper;

    public DoorServiceImpl(DoorRepository doorRepository, DoorMapper doorMapper) {
        this.doorRepository = doorRepository;
        this.doorMapper = doorMapper;
    }

    /**
     * Save a door.
     *
     * @param doorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DoorDTO save(DoorDTO doorDTO) {
        log.debug("Request to save Door : {}", doorDTO);
        Door door = doorMapper.toEntity(doorDTO);
        door = doorRepository.save(door);
        return doorMapper.toDto(door);
    }

    /**
     * Get all the doors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DoorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Doors");
        return doorRepository.findAll(pageable)
            .map(doorMapper::toDto);
    }


    /**
     * Get one door by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DoorDTO> findOne(Long id) {
        log.debug("Request to get Door : {}", id);
        return doorRepository.findById(id)
            .map(doorMapper::toDto);
    }

    /**
     * Delete the door by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Door : {}", id);
        doorRepository.deleteById(id);
    }
}
