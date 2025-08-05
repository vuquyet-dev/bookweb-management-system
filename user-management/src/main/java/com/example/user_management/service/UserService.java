package com.example.user_management.service;

import com.example.user_management.entity.User;
import com.example.user_management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getPaginatedUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    //Create user
    public User createUser(User user)
    {
        return userRepository.save(user);
    }

    //Update user
    public User updateUser(Long id, User user)
    {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(user.getUsername());
            existingUser.setAge(user.getAge());
            existingUser.setDob(user.getDob());
            return userRepository.save(existingUser);
        }).orElse(null);
    }

    //Delete user
    public User delUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
            return userOpt.get();
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> searchUsers(String keyword, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
    }

}
