package com.tdtu.finalproject.dto.request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserChangePWRequest {
    String idUser;
    String oldPassword;
    String newPassword;
}
