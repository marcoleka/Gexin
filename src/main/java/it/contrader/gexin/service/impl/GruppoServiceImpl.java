package it.contrader.gexin.service.impl;

import it.contrader.gexin.service.GruppoService;
import it.contrader.gexin.domain.Gruppo;
import it.contrader.gexin.repository.GruppoRepository;
import it.contrader.gexin.service.dto.GruppoDTO;
import it.contrader.gexin.service.mapper.GruppoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Service Implementation for managing Gruppo.
 */
@Service
@Transactional
public class GruppoServiceImpl implements GruppoService {

    private final Logger log = LoggerFactory.getLogger(GruppoServiceImpl.class);

    private final GruppoRepository gruppoRepository;

    private final GruppoMapper gruppoMapper;

    public GruppoServiceImpl(GruppoRepository gruppoRepository, GruppoMapper gruppoMapper) {
        this.gruppoRepository = gruppoRepository;
        this.gruppoMapper = gruppoMapper;
    }

    /**
     * Save a gruppo.
     *
     * @param gruppoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GruppoDTO save(GruppoDTO gruppoDTO) {
        log.debug("Request to save Gruppo : {}", gruppoDTO);
        Gruppo gruppo = gruppoMapper.toEntity(gruppoDTO);
        gruppo = gruppoRepository.save(gruppo);
        return gruppoMapper.toDto(gruppo);
    }

    /**
     * Get all the gruppos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GruppoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gruppos");
        return gruppoRepository.findAll(pageable)
            .map(gruppoMapper::toDto);
    }

    /**
     * Get all the Gruppo with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<GruppoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return gruppoRepository.findAllWithEagerRelationships(pageable).map(gruppoMapper::toDto);
    }
    


    /**
     *  get all the gruppos where Accesso is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<GruppoDTO> findAllWhereAccessoIsNull() {
        log.debug("Request to get all gruppos where Accesso is null");
        return StreamSupport
            .stream(gruppoRepository.findAll().spliterator(), false)
            .filter(gruppo -> gruppo.getAccesso() == null)
            .map(gruppoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one gruppo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GruppoDTO> findOne(Long id) {
        log.debug("Request to get Gruppo : {}", id);
        return gruppoRepository.findOneWithEagerRelationships(id)
            .map(gruppoMapper::toDto);
    }

    /**
     * Delete the gruppo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gruppo : {}", id);
        gruppoRepository.deleteById(id);
    }
}
