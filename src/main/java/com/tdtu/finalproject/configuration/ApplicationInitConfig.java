package com.tdtu.finalproject.configuration;

import com.tdtu.finalproject.entity.Role;
import com.tdtu.finalproject.entity.User;
import com.tdtu.finalproject.repository.RoleRepository;
import com.tdtu.finalproject.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
/*
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            // Khởi tạo role "ADMIN" nếu chưa tồn tại
            Role adminRole = roleRepository.findById("ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));

            // Tạo người dùng "admin" nếu chưa tồn tại
            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(Collections.singleton(adminRole))
                        .build();
                userRepository.save(adminUser);
            }
        };
    }*/
}
