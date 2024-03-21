package org.quangphan.ecommerce.account.service.mapper;

import org.mapstruct.*;
import org.quangphan.ecommerce.account.domain.UserRole;
import org.quangphan.ecommerce.account.service.dto.UserRoleDTO;

/**
 * Mapper for the entity {@link UserRole} and its DTO {@link UserRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserRoleMapper extends EntityMapper<UserRoleDTO, UserRole> {}
