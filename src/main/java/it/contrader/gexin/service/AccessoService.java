package it.contrader.gexin.service;

import it.contrader.gexin.service.dto.AccessoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Accesso.
 */
public interface AccessoService {

    /**
     * Save a accesso.
     *
     * @param accessoDTO the entity to save
     * @return the persisted entity
     */
    AccessoDTO save(AccessoDTO accessoDTO);

    /**
     * Get all the accessos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AccessoDTO> findAll(Pageable pageable);
    /**
     * Get all the AccessoDTO where Dipendente is null.
     *
     * @return the list of entities
     */
    List<AccessoDTO> findAllWhereDipendenteIsNull();


    /**
     * Get the "id" accesso.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AccessoDTO> findOne(Long id);

    /**
     * Delete the "id" accesso.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
