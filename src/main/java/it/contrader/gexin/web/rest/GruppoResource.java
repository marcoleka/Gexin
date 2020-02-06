package it.contrader.gexin.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.contrader.gexin.service.GruppoService;
import it.contrader.gexin.web.rest.errors.BadRequestAlertException;
import it.contrader.gexin.web.rest.util.HeaderUtil;
import it.contrader.gexin.web.rest.util.PaginationUtil;
import it.contrader.gexin.service.dto.GruppoDTO;
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
 * REST controller for managing Gruppo.
 */
@RestController
@RequestMapping("/api")
public class GruppoResource {

    private final Logger log = LoggerFactory.getLogger(GruppoResource.class);

    private static final String ENTITY_NAME = "gruppo";

    private final GruppoService gruppoService;

    public GruppoResource(GruppoService gruppoService) {
        this.gruppoService = gruppoService;
    }

    /**
     * POST  /gruppos : Create a new gruppo.
     *
     * @param gruppoDTO the gruppoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gruppoDTO, or with status 400 (Bad Request) if the gruppo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gruppos")
    @Timed
    public ResponseEntity<GruppoDTO> createGruppo(@RequestBody GruppoDTO gruppoDTO) throws URISyntaxException {
        log.debug("REST request to save Gruppo : {}", gruppoDTO);
        if (gruppoDTO.getId() != null) {
            throw new BadRequestAlertException("A new gruppo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GruppoDTO result = gruppoService.save(gruppoDTO);
        return ResponseEntity.created(new URI("/api/gruppos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gruppos : Updates an existing gruppo.
     *
     * @param gruppoDTO the gruppoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gruppoDTO,
     * or with status 400 (Bad Request) if the gruppoDTO is not valid,
     * or with status 500 (Internal Server Error) if the gruppoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gruppos")
    @Timed
    public ResponseEntity<GruppoDTO> updateGruppo(@RequestBody GruppoDTO gruppoDTO) throws URISyntaxException {
        log.debug("REST request to update Gruppo : {}", gruppoDTO);
        if (gruppoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GruppoDTO result = gruppoService.save(gruppoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gruppoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gruppos : get all the gruppos.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of gruppos in body
     */
    @GetMapping("/gruppos")
    @Timed
    public ResponseEntity<List<GruppoDTO>> getAllGruppos(Pageable pageable, @RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("accesso-is-null".equals(filter)) {
            log.debug("REST request to get all Gruppos where accesso is null");
            return new ResponseEntity<>(gruppoService.findAllWhereAccessoIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Gruppos");
        Page<GruppoDTO> page;
        if (eagerload) {
            page = gruppoService.findAllWithEagerRelationships(pageable);
        } else {
            page = gruppoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/gruppos?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /gruppos/:id : get the "id" gruppo.
     *
     * @param id the id of the gruppoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gruppoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gruppos/{id}")
    @Timed
    public ResponseEntity<GruppoDTO> getGruppo(@PathVariable Long id) {
        log.debug("REST request to get Gruppo : {}", id);
        Optional<GruppoDTO> gruppoDTO = gruppoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gruppoDTO);
    }

    /**
     * DELETE  /gruppos/:id : delete the "id" gruppo.
     *
     * @param id the id of the gruppoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gruppos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGruppo(@PathVariable Long id) {
        log.debug("REST request to delete Gruppo : {}", id);
        gruppoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
