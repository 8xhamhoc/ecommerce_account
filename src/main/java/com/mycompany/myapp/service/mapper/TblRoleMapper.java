package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TblRole;
import com.mycompany.myapp.service.dto.TblRoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TblRole} and its DTO {@link TblRoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface TblRoleMapper extends EntityMapper<TblRoleDTO, TblRole> {}
