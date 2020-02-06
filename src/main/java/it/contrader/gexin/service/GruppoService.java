package it.contrader.gexin.service;

import it.contrader.gexin.service.dto.GruppoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Gruppo.
 */
public interface GruppoService {

    /**
     * Save a gruppo.
     *
     * @param gruppoDTO the entity to save
     * @return the persisted entity
     */
    GruppoDTO save(GruppoDTO gruppoDTO);

    /**
     * Get all the gruppos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GruppoDTO> findAll(Pageable pageable);
    /**
     * Get all the GruppoDTO where Accesso is null.
     *
     * @return the list of entities
     */
    List<GruppoDTO> findAllWhereAccessoIsNull();

    /**
     * Get all the Gruppo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<GruppoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" gruppo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GruppoDTO> findOne(Long id);

    /**
     * Delete the "id" gruppo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
