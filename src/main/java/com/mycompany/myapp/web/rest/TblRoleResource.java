package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TblRoleRepository;
import com.mycompany.myapp.service.TblRoleService;
import com.mycompany.myapp.service.dto.TblRoleDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TblRole}.
 */
@RestController
@RequestMapping("/api")
public class TblRoleResource {

    private final Logger log = LoggerFactory.getLogger(TblRoleResource.class);

    private static final String ENTITY_NAME = "tblRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TblRoleService tblRoleService;

    private final TblRoleRepository tblRoleRepository;

    public TblRoleResource(TblRoleService tblRoleService, TblRoleRepository tblRoleRepository) {
        this.tblRoleService = tblRoleService;
        this.tblRoleRepository = tblRoleRepository;
    }

    /**
     * {@code POST  /tbl-roles} : Create a new tblRole.
     *
     * @param tblRoleDTO the tblRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tblRoleDTO, or with status {@code 400 (Bad Request)} if the tblRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tbl-roles")
    public ResponseEntity<TblRoleDTO> createTblRole(@Valid @RequestBody TblRoleDTO tblRoleDTO) throws URISyntaxException {
        log.debug("REST request to save TblRole : {}", tblRoleDTO);
        if (tblRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new tblRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TblRoleDTO result = tblRoleService.save(tblRoleDTO);
        return ResponseEntity
            .created(new URI("/api/tbl-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tbl-roles/:id} : Updates an existing tblRole.
     *
     * @param id the id of the tblRoleDTO to save.
     * @param tblRoleDTO the tblRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tblRoleDTO,
     * or with status {@code 400 (Bad Request)} if the tblRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tblRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tbl-roles/{id}")
    public ResponseEntity<TblRoleDTO> updateTblRole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TblRoleDTO tblRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TblRole : {}, {}", id, tblRoleDTO);
        if (tblRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tblRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tblRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TblRoleDTO result = tblRoleService.update(tblRoleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tblRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tbl-roles/:id} : Partial updates given fields of an existing tblRole, field will ignore if it is null
     *
     * @param id the id of the tblRoleDTO to save.
     * @param tblRoleDTO the tblRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tblRoleDTO,
     * or with status {@code 400 (Bad Request)} if the tblRoleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tblRoleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tblRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tbl-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TblRoleDTO> partialUpdateTblRole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TblRoleDTO tblRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TblRole partially : {}, {}", id, tblRoleDTO);
        if (tblRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tblRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tblRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TblRoleDTO> result = tblRoleService.partialUpdate(tblRoleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tblRoleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tbl-roles} : get all the tblRoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tblRoles in body.
     */
    @GetMapping("/tbl-roles")
    public ResponseEntity<List<TblRoleDTO>> getAllTblRoles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TblRoles");
        Page<TblRoleDTO> page = tblRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tbl-roles/:id} : get the "id" tblRole.
     *
     * @param id the id of the tblRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tblRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tbl-roles/{id}")
    public ResponseEntity<TblRoleDTO> getTblRole(@PathVariable Long id) {
        log.debug("REST request to get TblRole : {}", id);
        Optional<TblRoleDTO> tblRoleDTO = tblRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tblRoleDTO);
    }

    /**
     * {@code DELETE  /tbl-roles/:id} : delete the "id" tblRole.
     *
     * @param id the id of the tblRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tbl-roles/{id}")
    public ResponseEntity<Void> deleteTblRole(@PathVariable Long id) {
        log.debug("REST request to delete TblRole : {}", id);
        tblRoleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
