package com.sei.smartrx.repository;

import com.sei.smartrx.models.Prescription;
import com.sei.smartrx.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    public User findUserByEmail(String email);

    public List<Prescription> getAllById(Long id);

}
