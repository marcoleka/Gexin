package it.contrader.gexin.service.impl;

import it.contrader.gexin.service.TimeZoneService;
import it.contrader.gexin.domain.TimeZone;
import it.contrader.gexin.repository.TimeZoneRepository;
import it.contrader.gexin.service.dto.TimeZoneDTO;
import it.contrader.gexin.service.mapper.TimeZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing TimeZone.
 */
@Service
@Transactional
public class TimeZoneServiceImpl implements TimeZoneService {

    private final Logger log = LoggerFactory.getLogger(TimeZoneServiceImpl.class);

    private final TimeZoneRepository timeZoneRepository;

    private final TimeZoneMapper timeZoneMapper;

    public TimeZoneServiceImpl(TimeZoneRepository timeZoneRepository, TimeZoneMapper timeZoneMapper) {
        this.timeZoneRepository = timeZoneRepository;
        this.timeZoneMapper = timeZoneMapper;
    }

    /**
     * Save a timeZone.
     *
     * @param timeZoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimeZoneDTO save(TimeZoneDTO timeZoneDTO) {
        log.debug("Request to save TimeZone : {}", timeZoneDTO);
        TimeZone timeZone = timeZoneMapper.toEntity(timeZoneDTO);
        timeZone = timeZoneRepository.save(timeZone);
        return timeZoneMapper.toDto(timeZone);
    }

    /**
     * Get all the timeZones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimeZoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TimeZones");
        return timeZoneRepository.findAll(pageable)
            .map(timeZoneMapper::toDto);
    }


    /**
     * Get one timeZone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TimeZoneDTO> findOne(Long id) {
        log.debug("Request to get TimeZone : {}", id);
        return timeZoneRepository.findById(id)
            .map(timeZoneMapper::toDto);
    }

    /**
     * Delete the timeZone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TimeZone : {}", id);
        timeZoneRepository.deleteById(id);
    }
}
