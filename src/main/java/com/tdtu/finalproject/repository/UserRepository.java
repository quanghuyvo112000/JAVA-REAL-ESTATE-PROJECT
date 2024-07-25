package com.tdtu.finalproject.repository;

import com.tdtu.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /*Kiểm tra Username có tồn tại không*/
    boolean existsByUsername(String username);

    /*Kiểm tra Email có tồn tại không*/
    boolean existsByEmail(String email);

    /* Lấy username */
    Optional<User> findByUsername(String username);

    /* Lấy user thông qua email */
    Optional<User> findByEmail(String email);
}
