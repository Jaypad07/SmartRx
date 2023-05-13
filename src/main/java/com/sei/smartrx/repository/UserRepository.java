package com.sei.smartrx.repository;

import com.sei.smartrx.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findUserByEmail(String email);
}
