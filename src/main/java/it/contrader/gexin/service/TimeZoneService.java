package it.contrader.gexin.service;

import it.contrader.gexin.service.dto.TimeZoneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TimeZone.
 */
public interface TimeZoneService {

    /**
     * Save a timeZone.
     *
     * @param timeZoneDTO the entity to save
     * @return the persisted entity
     */
    TimeZoneDTO save(TimeZoneDTO timeZoneDTO);

    /**
     * Get all the timeZones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimeZoneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" timeZone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TimeZoneDTO> findOne(Long id);

    /**
     * Delete the "id" timeZone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
