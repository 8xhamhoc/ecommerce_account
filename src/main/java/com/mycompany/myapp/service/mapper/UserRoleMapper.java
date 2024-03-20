package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.UserRole;
import com.mycompany.myapp.service.dto.UserRoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserRole} and its DTO {@link UserRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserRoleMapper extends EntityMapper<UserRoleDTO, UserRole> {}
