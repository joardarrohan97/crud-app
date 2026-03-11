package com.example.crud.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crud.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
