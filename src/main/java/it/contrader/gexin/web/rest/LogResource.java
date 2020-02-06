package it.contrader.gexin.web.rest;

import com.codahale.metrics.annotation.Timed;
import it.contrader.gexin.service.LogService;
import it.contrader.gexin.web.rest.errors.BadRequestAlertException;
import it.contrader.gexin.web.rest.util.HeaderUtil;
import it.contrader.gexin.web.rest.util.PaginationUtil;
import it.contrader.gexin.service.dto.LogDTO;
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
 * REST controller for managing Log.
 */
@RestController
@RequestMapping("/api")
public class LogResource {

    private final Logger log = LoggerFactory.getLogger(LogResource.class);

    private static final String ENTITY_NAME = "log";

    private final LogService logService;

    public LogResource(LogService logService) {
        this.logService = logService;
    }

    /**
     * POST  /logs : Create a new log.
     *
     * @param logDTO the logDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logDTO, or with status 400 (Bad Request) if the log has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logs")
    @Timed
    public ResponseEntity<LogDTO> createLog(@RequestBody LogDTO logDTO) throws URISyntaxException {
        log.debug("REST request to save Log : {}", logDTO);
        if (logDTO.getId() != null) {
            throw new BadRequestAlertException("A new log cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogDTO result = logService.save(logDTO);
        return ResponseEntity.created(new URI("/api/logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logs : Updates an existing log.
     *
     * @param logDTO the logDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logDTO,
     * or with status 400 (Bad Request) if the logDTO is not valid,
     * or with status 500 (Internal Server Error) if the logDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logs")
    @Timed
    public ResponseEntity<LogDTO> updateLog(@RequestBody LogDTO logDTO) throws URISyntaxException {
        log.debug("REST request to update Log : {}", logDTO);
        if (logDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogDTO result = logService.save(logDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logs : get all the logs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logs in body
     */
    @GetMapping("/logs")
    @Timed
    public ResponseEntity<List<LogDTO>> getAllLogs(Pageable pageable) {
        log.debug("REST request to get a page of Logs");
        Page<LogDTO> page = logService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /logs/:id : get the "id" log.
     *
     * @param id the id of the logDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logDTO, or with status 404 (Not Found)
     */
    @GetMapping("/logs/{id}")
    @Timed
    public ResponseEntity<LogDTO> getLog(@PathVariable Long id) {
        log.debug("REST request to get Log : {}", id);
        Optional<LogDTO> logDTO = logService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logDTO);
    }

    /**
     * DELETE  /logs/:id : delete the "id" log.
     *
     * @param id the id of the logDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        log.debug("REST request to delete Log : {}", id);
        logService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
