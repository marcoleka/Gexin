package it.contrader.gexin.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.contrader.gexin.service.TimeZoneService;
import it.contrader.gexin.web.rest.errors.BadRequestAlertException;
import it.contrader.gexin.web.rest.util.HeaderUtil;
import it.contrader.gexin.web.rest.util.PaginationUtil;
import it.contrader.gexin.service.dto.TimeZoneDTO;
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
 * REST controller for managing TimeZone.
 */
@RestController
@RequestMapping("/api")
public class TimeZoneResource {

    private final Logger log = LoggerFactory.getLogger(TimeZoneResource.class);

    private static final String ENTITY_NAME = "timeZone";

    private final TimeZoneService timeZoneService;

    public TimeZoneResource(TimeZoneService timeZoneService) {
        this.timeZoneService = timeZoneService;
    }

    /**
     * POST  /time-zones : Create a new timeZone.
     *
     * @param timeZoneDTO the timeZoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeZoneDTO, or with status 400 (Bad Request) if the timeZone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-zones")
    @Timed
    public ResponseEntity<TimeZoneDTO> createTimeZone(@RequestBody TimeZoneDTO timeZoneDTO) throws URISyntaxException {
        log.debug("REST request to save TimeZone : {}", timeZoneDTO);
        if (timeZoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeZone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeZoneDTO result = timeZoneService.save(timeZoneDTO);
        return ResponseEntity.created(new URI("/api/time-zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-zones : Updates an existing timeZone.
     *
     * @param timeZoneDTO the timeZoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeZoneDTO,
     * or with status 400 (Bad Request) if the timeZoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the timeZoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-zones")
    @Timed
    public ResponseEntity<TimeZoneDTO> updateTimeZone(@RequestBody TimeZoneDTO timeZoneDTO) throws URISyntaxException {
        log.debug("REST request to update TimeZone : {}", timeZoneDTO);
        if (timeZoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeZoneDTO result = timeZoneService.save(timeZoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeZoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-zones : get all the timeZones.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timeZones in body
     */
    @GetMapping("/time-zones")
    @Timed
    public ResponseEntity<List<TimeZoneDTO>> getAllTimeZones(Pageable pageable) {
        log.debug("REST request to get a page of TimeZones");
        Page<TimeZoneDTO> page = timeZoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-zones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /time-zones/:id : get the "id" timeZone.
     *
     * @param id the id of the timeZoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeZoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/time-zones/{id}")
    @Timed
    public ResponseEntity<TimeZoneDTO> getTimeZone(@PathVariable Long id) {
        log.debug("REST request to get TimeZone : {}", id);
        Optional<TimeZoneDTO> timeZoneDTO = timeZoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeZoneDTO);
    }

    /**
     * DELETE  /time-zones/:id : delete the "id" timeZone.
     *
     * @param id the id of the timeZoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-zones/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimeZone(@PathVariable Long id) {
        log.debug("REST request to delete TimeZone : {}", id);
        timeZoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
