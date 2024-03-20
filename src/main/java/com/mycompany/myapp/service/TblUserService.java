package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TblUser;
import com.mycompany.myapp.repository.TblUserRepository;
import com.mycompany.myapp.service.dto.TblUserDTO;
import com.mycompany.myapp.service.mapper.TblUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TblUser}.
 */
@Service
@Transactional
public class TblUserService {

    private final Logger log = LoggerFactory.getLogger(TblUserService.class);

    private final TblUserRepository tblUserRepository;

    private final TblUserMapper tblUserMapper;

    public TblUserService(TblUserRepository tblUserRepository, TblUserMapper tblUserMapper) {
        this.tblUserRepository = tblUserRepository;
        this.tblUserMapper = tblUserMapper;
    }

    /**
     * Save a tblUser.
     *
     * @param tblUserDTO the entity to save.
     * @return the persisted entity.
     */
    public TblUserDTO save(TblUserDTO tblUserDTO) {
        log.debug("Request to save TblUser : {}", tblUserDTO);
        TblUser tblUser = tblUserMapper.toEntity(tblUserDTO);
        tblUser = tblUserRepository.save(tblUser);
        return tblUserMapper.toDto(tblUser);
    }

    /**
     * Update a tblUser.
     *
     * @param tblUserDTO the entity to save.
     * @return the persisted entity.
     */
    public TblUserDTO update(TblUserDTO tblUserDTO) {
        log.debug("Request to update TblUser : {}", tblUserDTO);
        TblUser tblUser = tblUserMapper.toEntity(tblUserDTO);
        tblUser = tblUserRepository.save(tblUser);
        return tblUserMapper.toDto(tblUser);
    }

    /**
     * Partially update a tblUser.
     *
     * @param tblUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TblUserDTO> partialUpdate(TblUserDTO tblUserDTO) {
        log.debug("Request to partially update TblUser : {}", tblUserDTO);

        return tblUserRepository
            .findById(tblUserDTO.getId())
            .map(existingTblUser -> {
                tblUserMapper.partialUpdate(existingTblUser, tblUserDTO);

                return existingTblUser;
            })
            .map(tblUserRepository::save)
            .map(tblUserMapper::toDto);
    }

    /**
     * Get all the tblUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TblUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TblUsers");
        return tblUserRepository.findAll(pageable).map(tblUserMapper::toDto);
    }

    /**
     * Get all the tblUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TblUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tblUserRepository.findAllWithEagerRelationships(pageable).map(tblUserMapper::toDto);
    }

    /**
     * Get one tblUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TblUserDTO> findOne(Long id) {
        log.debug("Request to get TblUser : {}", id);
        return tblUserRepository.findOneWithEagerRelationships(id).map(tblUserMapper::toDto);
    }

    /**
     * Delete the tblUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TblUser : {}", id);
        tblUserRepository.deleteById(id);
    }
}
