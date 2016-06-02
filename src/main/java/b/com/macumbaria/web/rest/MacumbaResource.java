package b.com.macumbaria.web.rest;

import com.codahale.metrics.annotation.Timed;
import b.com.macumbaria.domain.Macumba;
import b.com.macumbaria.service.MacumbaService;
import b.com.macumbaria.web.rest.util.HeaderUtil;
import b.com.macumbaria.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Macumba.
 */
@RestController
@RequestMapping("/api")
public class MacumbaResource {

    private final Logger log = LoggerFactory.getLogger(MacumbaResource.class);
        
    @Inject
    private MacumbaService macumbaService;
    
    /**
     * POST  /macumbas -> Create a new macumba.
     */
    @RequestMapping(value = "/macumbas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Macumba> createMacumba(@Valid @RequestBody Macumba macumba) throws URISyntaxException {
        log.debug("REST request to save Macumba : {}", macumba);
        if (macumba.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("macumba", "idexists", "A new macumba cannot already have an ID")).body(null);
        }
        Macumba result = macumbaService.save(macumba);
        return ResponseEntity.created(new URI("/api/macumbas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("macumba", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /macumbas -> Updates an existing macumba.
     */
    @RequestMapping(value = "/macumbas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Macumba> updateMacumba(@Valid @RequestBody Macumba macumba) throws URISyntaxException {
        log.debug("REST request to update Macumba : {}", macumba);
        if (macumba.getId() == null) {
            return createMacumba(macumba);
        }
        Macumba result = macumbaService.save(macumba);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("macumba", macumba.getId().toString()))
            .body(result);
    }

    /**
     * GET  /macumbas -> get all the macumbas.
     */
    @RequestMapping(value = "/macumbas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Macumba>> getAllMacumbas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Macumbas");
        Page<Macumba> page = macumbaService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/macumbas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /macumbas/:id -> get the "id" macumba.
     */
    @RequestMapping(value = "/macumbas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Macumba> getMacumba(@PathVariable Long id) {
        log.debug("REST request to get Macumba : {}", id);
        Macumba macumba = macumbaService.findOne(id);
        return Optional.ofNullable(macumba)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /macumbas/:id -> delete the "id" macumba.
     */
    @RequestMapping(value = "/macumbas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMacumba(@PathVariable Long id) {
        log.debug("REST request to delete Macumba : {}", id);
        macumbaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("macumba", id.toString())).build();
    }
}
