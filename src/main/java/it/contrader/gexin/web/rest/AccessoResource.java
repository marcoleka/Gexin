package it.contrader.gexin.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.contrader.gexin.service.AccessoService;
import it.contrader.gexin.web.rest.errors.BadRequestAlertException;
import it.contrader.gexin.web.rest.util.HeaderUtil;
import it.contrader.gexin.web.rest.util.PaginationUtil;
import it.contrader.gexin.service.dto.AccessoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Accesso.
 */
@RestController
@RequestMapping("/api")
public class AccessoResource {

    private final Logger log = LoggerFactory.getLogger(AccessoResource.class);

    private static final String ENTITY_NAME = "accesso";

    private final AccessoService accessoService;

    public AccessoResource(AccessoService accessoService) {
        this.accessoService = accessoService;
    }

    /**
     * POST  /accessos : Create a new accesso.
     *
     * @param accessoDTO the accessoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accessoDTO, or with status 400 (Bad Request) if the accesso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accessos")
    @Timed
    public ResponseEntity<AccessoDTO> createAccesso(@RequestBody AccessoDTO accessoDTO) throws URISyntaxException {
        log.debug("REST request to save Accesso : {}", accessoDTO);
        if (accessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new accesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccessoDTO result = accessoService.save(accessoDTO);
        return ResponseEntity.created(new URI("/api/accessos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accessos : Updates an existing accesso.
     *
     * @param accessoDTO the accessoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accessoDTO,
     * or with status 400 (Bad Request) if the accessoDTO is not valid,
     * or with status 500 (Internal Server Error) if the accessoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accessos")
    @Timed
    public ResponseEntity<AccessoDTO> updateAccesso(@RequestBody AccessoDTO accessoDTO) throws URISyntaxException {
        log.debug("REST request to update Accesso : {}", accessoDTO);
        if (accessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccessoDTO result = accessoService.save(accessoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accessoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /accessos : get all the accessos.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of accessos in body
     */
    @GetMapping("/accessos")
    @Timed
    public ResponseEntity<List<AccessoDTO>> getAllAccessos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("dipendente-is-null".equals(filter)) {
            log.debug("REST request to get all Accessos where dipendente is null");
            return new ResponseEntity<>(accessoService.findAllWhereDipendenteIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Accessos");
        Page<AccessoDTO> page = accessoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accessos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /accessos/:id : get the "id" accesso.
     *
     * @param id the id of the accessoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accessoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/accessos/{id}")
    @Timed
    public ResponseEntity<AccessoDTO> getAccesso(@PathVariable Long id) {
        log.debug("REST request to get Accesso : {}", id);
        Optional<AccessoDTO> accessoDTO = accessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accessoDTO);
    }

    /**
     * DELETE  /accessos/:id : delete the "id" accesso.
     *
     * @param id the id of the accessoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accessos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccesso(@PathVariable Long id) {
        log.debug("REST request to delete Accesso : {}", id);
        accessoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
