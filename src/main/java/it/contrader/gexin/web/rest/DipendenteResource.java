package it.contrader.gexin.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.contrader.gexin.service.DipendenteService;
import it.contrader.gexin.web.rest.errors.BadRequestAlertException;
import it.contrader.gexin.web.rest.util.HeaderUtil;
import it.contrader.gexin.web.rest.util.PaginationUtil;
import it.contrader.gexin.service.dto.DipendenteDTO;
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

/**
 * REST controller for managing Dipendente.
 */
@RestController
@RequestMapping("/api")
public class DipendenteResource {

    private final Logger log = LoggerFactory.getLogger(DipendenteResource.class);

    private static final String ENTITY_NAME = "dipendente";

    private final DipendenteService dipendenteService;

    public DipendenteResource(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    /**
     * POST  /dipendentes : Create a new dipendente.
     *
     * @param dipendenteDTO the dipendenteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dipendenteDTO, or with status 400 (Bad Request) if the dipendente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dipendentes")
    @Timed
    public ResponseEntity<DipendenteDTO> createDipendente(@RequestBody DipendenteDTO dipendenteDTO) throws URISyntaxException {
        log.debug("REST request to save Dipendente : {}", dipendenteDTO);
        if (dipendenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new dipendente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DipendenteDTO result = dipendenteService.save(dipendenteDTO);
        return ResponseEntity.created(new URI("/api/dipendentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dipendentes : Updates an existing dipendente.
     *
     * @param dipendenteDTO the dipendenteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dipendenteDTO,
     * or with status 400 (Bad Request) if the dipendenteDTO is not valid,
     * or with status 500 (Internal Server Error) if the dipendenteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dipendentes")
    @Timed
    public ResponseEntity<DipendenteDTO> updateDipendente(@RequestBody DipendenteDTO dipendenteDTO) throws URISyntaxException {
        log.debug("REST request to update Dipendente : {}", dipendenteDTO);
        if (dipendenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DipendenteDTO result = dipendenteService.save(dipendenteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dipendenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dipendentes : get all the dipendentes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dipendentes in body
     */
    @GetMapping("/dipendentes")
    @Timed
    public ResponseEntity<List<DipendenteDTO>> getAllDipendentes(Pageable pageable) {
        log.debug("REST request to get a page of Dipendentes");
        Page<DipendenteDTO> page = dipendenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dipendentes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dipendentes/:id : get the "id" dipendente.
     *
     * @param id the id of the dipendenteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dipendenteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dipendentes/{id}")
    @Timed
    public ResponseEntity<DipendenteDTO> getDipendente(@PathVariable Long id) {
        log.debug("REST request to get Dipendente : {}", id);
        Optional<DipendenteDTO> dipendenteDTO = dipendenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dipendenteDTO);
    }

    /**
     * DELETE  /dipendentes/:id : delete the "id" dipendente.
     *
     * @param id the id of the dipendenteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dipendentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteDipendente(@PathVariable Long id) {
        log.debug("REST request to delete Dipendente : {}", id);
        dipendenteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
