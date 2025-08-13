package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    public List<UserResponseDTO> getAllUsers();
    public UserResponseDTO getUser(String username);
    public UserResponseDTO createUser(UserCreateDTO dto);
    public UserResponseDTO update(String username, UserUpdateDTO dto);
    public void deleteUser(String username);
    public Page<UserResponseDTO> search(String keyword, int page, int size);
}