package com.tdtu.finalproject.service;

import com.tdtu.finalproject.dto.request.permission.PermissionRequest;
import com.tdtu.finalproject.dto.response.permission.PermissionResponse;
import com.tdtu.finalproject.entity.Permission;
import com.tdtu.finalproject.mapper.PermissionMapper;
import com.tdtu.finalproject.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        var permission = permissionRepository.findAll();
        return permission.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();

    }

    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
