package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserRole;
import com.mycompany.myapp.repository.UserRoleRepository;
import com.mycompany.myapp.service.dto.UserRoleDTO;
import com.mycompany.myapp.service.mapper.UserRoleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserRole}.
 */
@Service
@Transactional
public class UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleService.class);

    private final UserRoleRepository userRoleRepository;

    private final UserRoleMapper userRoleMapper;

    public UserRoleService(UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * Save a userRole.
     *
     * @param userRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public UserRoleDTO save(UserRoleDTO userRoleDTO) {
        log.debug("Request to save UserRole : {}", userRoleDTO);
        UserRole userRole = userRoleMapper.toEntity(userRoleDTO);
        userRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDto(userRole);
    }

    /**
     * Update a userRole.
     *
     * @param userRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public UserRoleDTO update(UserRoleDTO userRoleDTO) {
        log.debug("Request to update UserRole : {}", userRoleDTO);
        UserRole userRole = userRoleMapper.toEntity(userRoleDTO);
        userRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDto(userRole);
    }

    /**
     * Partially update a userRole.
     *
     * @param userRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserRoleDTO> partialUpdate(UserRoleDTO userRoleDTO) {
        log.debug("Request to partially update UserRole : {}", userRoleDTO);

        return userRoleRepository
            .findById(userRoleDTO.getId())
            .map(existingUserRole -> {
                userRoleMapper.partialUpdate(existingUserRole, userRoleDTO);

                return existingUserRole;
            })
            .map(userRoleRepository::save)
            .map(userRoleMapper::toDto);
    }

    /**
     * Get all the userRoles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserRoleDTO> findAll() {
        log.debug("Request to get all UserRoles");
        return userRoleRepository.findAll().stream().map(userRoleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserRoleDTO> findOne(Long id) {
        log.debug("Request to get UserRole : {}", id);
        return userRoleRepository.findById(id).map(userRoleMapper::toDto);
    }

    /**
     * Delete the userRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserRole : {}", id);
        userRoleRepository.deleteById(id);
    }
}
