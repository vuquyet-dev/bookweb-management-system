package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    public List<UserDTO> getAllUsers();
    public UserDTO getUser(String username);
    public UserDTO createUser(UserDTO dto);
    public UserDTO update(String username, UserDTO dto);
    public void deleteUser(String username);
    public Page<UserDTO> search(String keyword, int page, int size);
}
