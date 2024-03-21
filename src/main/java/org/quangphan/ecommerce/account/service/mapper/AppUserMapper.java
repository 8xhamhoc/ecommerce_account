package org.quangphan.ecommerce.account.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.quangphan.ecommerce.account.domain.AppRole;
import org.quangphan.ecommerce.account.domain.AppUser;
import org.quangphan.ecommerce.account.service.dto.AppRoleDTO;
import org.quangphan.ecommerce.account.service.dto.AppUserDTO;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "appRoleIdSet")
    AppUserDTO toDto(AppUser s);

    @Mapping(target = "removeRole", ignore = true)
    AppUser toEntity(AppUserDTO appUserDTO);

    @Named("appRoleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppRoleDTO toDtoAppRoleId(AppRole appRole);

    @Named("appRoleIdSet")
    default Set<AppRoleDTO> toDtoAppRoleIdSet(Set<AppRole> appRole) {
        return appRole.stream().map(this::toDtoAppRoleId).collect(Collectors.toSet());
    }
}
