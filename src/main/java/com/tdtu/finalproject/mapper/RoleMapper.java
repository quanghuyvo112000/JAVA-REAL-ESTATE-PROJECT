package com.tdtu.finalproject.mapper;

import com.tdtu.finalproject.dto.request.role.RoleRequest;
import com.tdtu.finalproject.dto.response.role.RoleResponse;
import com.tdtu.finalproject.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permission", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
