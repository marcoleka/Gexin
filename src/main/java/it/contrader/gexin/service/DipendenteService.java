package it.contrader.gexin.service;

import it.contrader.gexin.service.dto.DipendenteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dipendente.
 */
public interface DipendenteService {

    /**
     * Save a dipendente.
     *
     * @param dipendenteDTO the entity to save
     * @return the persisted entity
     */
    DipendenteDTO save(DipendenteDTO dipendenteDTO);

    /**
     * Get all the dipendentes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DipendenteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dipendente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DipendenteDTO> findOne(Long id);

    /**
     * Delete the "id" dipendente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
