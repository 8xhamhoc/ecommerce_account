package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TblRole;
import com.mycompany.myapp.domain.TblUser;
import com.mycompany.myapp.service.dto.TblRoleDTO;
import com.mycompany.myapp.service.dto.TblUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TblUser} and its DTO {@link TblUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface TblUserMapper extends EntityMapper<TblUserDTO, TblUser> {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "tblRoleIdSet")
    TblUserDTO toDto(TblUser s);

    @Mapping(target = "removeRole", ignore = true)
    TblUser toEntity(TblUserDTO tblUserDTO);

    @Named("tblRoleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TblRoleDTO toDtoTblRoleId(TblRole tblRole);

    @Named("tblRoleIdSet")
    default Set<TblRoleDTO> toDtoTblRoleIdSet(Set<TblRole> tblRole) {
        return tblRole.stream().map(this::toDtoTblRoleId).collect(Collectors.toSet());
    }
}
