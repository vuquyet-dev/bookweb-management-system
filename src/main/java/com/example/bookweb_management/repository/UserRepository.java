package com.example.bookweb_management.repository;

import com.example.bookweb_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByIdentityNumber(String identityNumber);
    Optional<User> findByUsernameOrIdentityNumber(String username, String identityNumber);

    @Query("SELECT u FROM User u " +
            "WHERE LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.identityNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<User> searchByFullnameOrIdentityNumber(@Param("keyword") String keyword, Pageable pageable);

    Optional<User> findByUsername(String username);

}
