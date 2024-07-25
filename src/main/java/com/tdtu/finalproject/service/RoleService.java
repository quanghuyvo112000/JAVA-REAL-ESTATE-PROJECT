package com.tdtu.finalproject.service;

import com.tdtu.finalproject.dto.request.role.RoleRequest;
import com.tdtu.finalproject.dto.response.role.RoleResponse;
import com.tdtu.finalproject.mapper.RoleMapper;
import com.tdtu.finalproject.repository.PermissionRepository;
import com.tdtu.finalproject.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);

        var permisston = permissionRepository.findAllById(request.getPermission());
        role.setPermission(new HashSet<>(permisston));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
