package com.example.bookweb_management.repository;

import com.example.bookweb_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
    boolean existsByIdentityNumber(String identityNumber);
    Optional<User> findByUsernameOrIdentityNumber(String username, String identityNumber);

}
