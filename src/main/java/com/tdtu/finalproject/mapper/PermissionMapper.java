package com.tdtu.finalproject.mapper;

import com.tdtu.finalproject.dto.request.permission.PermissionRequest;
import com.tdtu.finalproject.dto.response.permission.PermissionResponse;
import com.tdtu.finalproject.entity.Permission;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
