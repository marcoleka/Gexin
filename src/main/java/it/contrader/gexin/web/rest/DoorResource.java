package it.contrader.gexin.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.contrader.gexin.service.DoorService;
import it.contrader.gexin.web.rest.errors.BadRequestAlertException;
import it.contrader.gexin.web.rest.util.HeaderUtil;
import it.contrader.gexin.web.rest.util.PaginationUtil;
import it.contrader.gexin.service.dto.DoorDTO;
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
 * REST controller for managing Door.
 */
@RestController
@RequestMapping("/api")
public class DoorResource {

    private final Logger log = LoggerFactory.getLogger(DoorResource.class);

    private static final String ENTITY_NAME = "door";

    private final DoorService doorService;

    public DoorResource(DoorService doorService) {
        this.doorService = doorService;
    }

    /**
     * POST  /doors : Create a new door.
     *
     * @param doorDTO the doorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doorDTO, or with status 400 (Bad Request) if the door has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doors")
    @Timed
    public ResponseEntity<DoorDTO> createDoor(@RequestBody DoorDTO doorDTO) throws URISyntaxException {
        log.debug("REST request to save Door : {}", doorDTO);
        if (doorDTO.getId() != null) {
            throw new BadRequestAlertException("A new door cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoorDTO result = doorService.save(doorDTO);
        return ResponseEntity.created(new URI("/api/doors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doors : Updates an existing door.
     *
     * @param doorDTO the doorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doorDTO,
     * or with status 400 (Bad Request) if the doorDTO is not valid,
     * or with status 500 (Internal Server Error) if the doorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doors")
    @Timed
    public ResponseEntity<DoorDTO> updateDoor(@RequestBody DoorDTO doorDTO) throws URISyntaxException {
        log.debug("REST request to update Door : {}", doorDTO);
        if (doorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoorDTO result = doorService.save(doorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doors : get all the doors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doors in body
     */
    @GetMapping("/doors")
    @Timed
    public ResponseEntity<List<DoorDTO>> getAllDoors(Pageable pageable) {
        log.debug("REST request to get a page of Doors");
        Page<DoorDTO> page = doorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /doors/:id : get the "id" door.
     *
     * @param id the id of the doorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/doors/{id}")
    @Timed
    public ResponseEntity<DoorDTO> getDoor(@PathVariable Long id) {
        log.debug("REST request to get Door : {}", id);
        Optional<DoorDTO> doorDTO = doorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doorDTO);
    }

    /**
     * DELETE  /doors/:id : delete the "id" door.
     *
     * @param id the id of the doorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doors/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoor(@PathVariable Long id) {
        log.debug("REST request to delete Door : {}", id);
        doorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
