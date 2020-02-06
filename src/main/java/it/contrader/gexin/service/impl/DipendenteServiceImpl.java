package it.contrader.gexin.service.impl;

import it.contrader.gexin.service.DipendenteService;
import it.contrader.gexin.domain.Dipendente;
import it.contrader.gexin.repository.DipendenteRepository;
import it.contrader.gexin.service.dto.DipendenteDTO;
import it.contrader.gexin.service.mapper.DipendenteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Dipendente.
 */
@Service
@Transactional
public class DipendenteServiceImpl implements DipendenteService {

    private final Logger log = LoggerFactory.getLogger(DipendenteServiceImpl.class);

    private final DipendenteRepository dipendenteRepository;

    private final DipendenteMapper dipendenteMapper;

    public DipendenteServiceImpl(DipendenteRepository dipendenteRepository, DipendenteMapper dipendenteMapper) {
        this.dipendenteRepository = dipendenteRepository;
        this.dipendenteMapper = dipendenteMapper;
    }

    /**
     * Save a dipendente.
     *
     * @param dipendenteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DipendenteDTO save(DipendenteDTO dipendenteDTO) {
        log.debug("Request to save Dipendente : {}", dipendenteDTO);
        Dipendente dipendente = dipendenteMapper.toEntity(dipendenteDTO);
        dipendente = dipendenteRepository.save(dipendente);
        return dipendenteMapper.toDto(dipendente);
    }

    /**
     * Get all the dipendentes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DipendenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dipendentes");
        return dipendenteRepository.findAll(pageable)
            .map(dipendenteMapper::toDto);
    }


    /**
     * Get one dipendente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DipendenteDTO> findOne(Long id) {
        log.debug("Request to get Dipendente : {}", id);
        return dipendenteRepository.findById(id)
            .map(dipendenteMapper::toDto);
    }

    /**
     * Delete the dipendente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dipendente : {}", id);
        dipendenteRepository.deleteById(id);
    }
}
