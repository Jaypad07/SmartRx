package com.sei.smartrx.repository;

import com.sei.smartrx.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    public User findUserByEmail(String email);
}
