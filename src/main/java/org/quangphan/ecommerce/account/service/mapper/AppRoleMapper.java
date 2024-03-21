package org.quangphan.ecommerce.account.service.mapper;

import org.mapstruct.*;
import org.quangphan.ecommerce.account.domain.AppRole;
import org.quangphan.ecommerce.account.service.dto.AppRoleDTO;

/**
 * Mapper for the entity {@link AppRole} and its DTO {@link AppRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppRoleMapper extends EntityMapper<AppRoleDTO, AppRole> {}
