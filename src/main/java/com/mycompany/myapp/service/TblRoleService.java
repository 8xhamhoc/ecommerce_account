package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TblRole;
import com.mycompany.myapp.repository.TblRoleRepository;
import com.mycompany.myapp.service.dto.TblRoleDTO;
import com.mycompany.myapp.service.mapper.TblRoleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TblRole}.
 */
@Service
@Transactional
public class TblRoleService {

    private final Logger log = LoggerFactory.getLogger(TblRoleService.class);

    private final TblRoleRepository tblRoleRepository;

    private final TblRoleMapper tblRoleMapper;

    public TblRoleService(TblRoleRepository tblRoleRepository, TblRoleMapper tblRoleMapper) {
        this.tblRoleRepository = tblRoleRepository;
        this.tblRoleMapper = tblRoleMapper;
    }

    /**
     * Save a tblRole.
     *
     * @param tblRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public TblRoleDTO save(TblRoleDTO tblRoleDTO) {
        log.debug("Request to save TblRole : {}", tblRoleDTO);
        TblRole tblRole = tblRoleMapper.toEntity(tblRoleDTO);
        tblRole = tblRoleRepository.save(tblRole);
        return tblRoleMapper.toDto(tblRole);
    }

    /**
     * Update a tblRole.
     *
     * @param tblRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public TblRoleDTO update(TblRoleDTO tblRoleDTO) {
        log.debug("Request to update TblRole : {}", tblRoleDTO);
        TblRole tblRole = tblRoleMapper.toEntity(tblRoleDTO);
        tblRole = tblRoleRepository.save(tblRole);
        return tblRoleMapper.toDto(tblRole);
    }

    /**
     * Partially update a tblRole.
     *
     * @param tblRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TblRoleDTO> partialUpdate(TblRoleDTO tblRoleDTO) {
        log.debug("Request to partially update TblRole : {}", tblRoleDTO);

        return tblRoleRepository
            .findById(tblRoleDTO.getId())
            .map(existingTblRole -> {
                tblRoleMapper.partialUpdate(existingTblRole, tblRoleDTO);

                return existingTblRole;
            })
            .map(tblRoleRepository::save)
            .map(tblRoleMapper::toDto);
    }

    /**
     * Get all the tblRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TblRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TblRoles");
        return tblRoleRepository.findAll(pageable).map(tblRoleMapper::toDto);
    }

    /**
     * Get one tblRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TblRoleDTO> findOne(Long id) {
        log.debug("Request to get TblRole : {}", id);
        return tblRoleRepository.findById(id).map(tblRoleMapper::toDto);
    }

    /**
     * Delete the tblRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TblRole : {}", id);
        tblRoleRepository.deleteById(id);
    }
}
