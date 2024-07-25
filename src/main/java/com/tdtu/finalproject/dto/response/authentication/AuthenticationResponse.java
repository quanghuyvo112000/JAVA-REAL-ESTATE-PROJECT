package com.tdtu.finalproject.dto.response.authentication;

import com.tdtu.finalproject.dto.response.role.RoleResponse;
import com.tdtu.finalproject.entity.Role;
import jakarta.persistence.ElementCollection;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    String id;
    boolean authenticated;
    Set<Role> role;
}
