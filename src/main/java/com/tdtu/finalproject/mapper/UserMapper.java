package com.tdtu.finalproject.mapper;

import com.tdtu.finalproject.dto.request.user.UserCreationRequest;
import com.tdtu.finalproject.dto.request.user.UserUpdateRequest;
import com.tdtu.finalproject.dto.response.user.UserResponse;
import com.tdtu.finalproject.entity.User;
import com.tdtu.finalproject.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    default Set<Role> mapRoles(Set<String> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(roleName -> Role.builder().name(roleName).build())
                .collect(Collectors.toSet());
    }

    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    List<UserResponse> toListUserResponse(List<User> all);
}
