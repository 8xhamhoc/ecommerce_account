package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TblUserRepository;
import com.mycompany.myapp.service.TblUserService;
import com.mycompany.myapp.service.dto.TblUserDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.TblUser}.
 */
@RestController
@RequestMapping("/api")
public class TblUserResource {

    private final Logger log = LoggerFactory.getLogger(TblUserResource.class);

    private static final String ENTITY_NAME = "tblUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TblUserService tblUserService;

    private final TblUserRepository tblUserRepository;

    public TblUserResource(TblUserService tblUserService, TblUserRepository tblUserRepository) {
        this.tblUserService = tblUserService;
        this.tblUserRepository = tblUserRepository;
    }

    /**
     * {@code POST  /tbl-users} : Create a new tblUser.
     *
     * @param tblUserDTO the tblUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tblUserDTO, or with status {@code 400 (Bad Request)} if the tblUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbl-users")
    public ResponseEntity<TblUserDTO> createTblUser(@Valid @RequestBody TblUserDTO tblUserDTO) throws URISyntaxException {
        log.debug("REST request to save TblUser : {}", tblUserDTO);
        if (tblUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new tblUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TblUserDTO result = tblUserService.save(tblUserDTO);
        return ResponseEntity
            .created(new URI("/api/tbl-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tbl-users/:id} : Updates an existing tblUser.
     *
     * @param id the id of the tblUserDTO to save.
     * @param tblUserDTO the tblUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tblUserDTO,
     * or with status {@code 400 (Bad Request)} if the tblUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tblUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbl-users/{id}")
    public ResponseEntity<TblUserDTO> updateTblUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TblUserDTO tblUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TblUser : {}, {}", id, tblUserDTO);
        if (tblUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tblUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tblUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TblUserDTO result = tblUserService.update(tblUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tblUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbl-users/:id} : Partial updates given fields of an existing tblUser, field will ignore if it is null
     *
     * @param id the id of the tblUserDTO to save.
     * @param tblUserDTO the tblUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tblUserDTO,
     * or with status {@code 400 (Bad Request)} if the tblUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tblUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tblUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbl-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TblUserDTO> partialUpdateTblUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TblUserDTO tblUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TblUser partially : {}, {}", id, tblUserDTO);
        if (tblUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tblUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tblUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TblUserDTO> result = tblUserService.partialUpdate(tblUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tblUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tbl-users} : get all the tblUsers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tblUsers in body.
     */
    @GetMapping("/tbl-users")
    public ResponseEntity<List<TblUserDTO>> getAllTblUsers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of TblUsers");
        Page<TblUserDTO> page;
        if (eagerload) {
            page = tblUserService.findAllWithEagerRelationships(pageable);
        } else {
            page = tblUserService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tbl-users/:id} : get the "id" tblUser.
     *
     * @param id the id of the tblUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tblUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbl-users/{id}")
    public ResponseEntity<TblUserDTO> getTblUser(@PathVariable Long id) {
        log.debug("REST request to get TblUser : {}", id);
        Optional<TblUserDTO> tblUserDTO = tblUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tblUserDTO);
    }

    /**
     * {@code DELETE  /tbl-users/:id} : delete the "id" tblUser.
     *
     * @param id the id of the tblUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbl-users/{id}")
    public ResponseEntity<Void> deleteTblUser(@PathVariable Long id) {
        log.debug("REST request to delete TblUser : {}", id);
        tblUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
