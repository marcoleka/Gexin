package it.contrader.gexin.service.impl;

import it.contrader.gexin.service.AccessoService;
import it.contrader.gexin.domain.Accesso;
import it.contrader.gexin.repository.AccessoRepository;
import it.contrader.gexin.service.dto.AccessoDTO;
import it.contrader.gexin.service.mapper.AccessoMapper;
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
 * Service Implementation for managing Accesso.
 */
@Service
@Transactional
public class AccessoServiceImpl implements AccessoService {

    private final Logger log = LoggerFactory.getLogger(AccessoServiceImpl.class);

    private final AccessoRepository accessoRepository;

    private final AccessoMapper accessoMapper;

    public AccessoServiceImpl(AccessoRepository accessoRepository, AccessoMapper accessoMapper) {
        this.accessoRepository = accessoRepository;
        this.accessoMapper = accessoMapper;
    }

    /**
     * Save a accesso.
     *
     * @param accessoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AccessoDTO save(AccessoDTO accessoDTO) {
        log.debug("Request to save Accesso : {}", accessoDTO);
        Accesso accesso = accessoMapper.toEntity(accessoDTO);
        accesso = accessoRepository.save(accesso);
        return accessoMapper.toDto(accesso);
    }

    /**
     * Get all the accessos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accessos");
        return accessoRepository.findAll(pageable)
            .map(accessoMapper::toDto);
    }



    /**
     *  get all the accessos where Dipendente is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AccessoDTO> findAllWhereDipendenteIsNull() {
        log.debug("Request to get all accessos where Dipendente is null");
        return StreamSupport
            .stream(accessoRepository.findAll().spliterator(), false)
            .filter(accesso -> accesso.getDipendente() == null)
            .map(accessoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one accesso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccessoDTO> findOne(Long id) {
        log.debug("Request to get Accesso : {}", id);
        return accessoRepository.findById(id)
            .map(accessoMapper::toDto);
    }

    /**
     * Delete the accesso by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accesso : {}", id);
        accessoRepository.deleteById(id);
    }
}
