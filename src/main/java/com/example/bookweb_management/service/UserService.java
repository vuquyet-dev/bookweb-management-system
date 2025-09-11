package com.example.bookweb_management.service;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public List<UserResponseDTO> getAllUsers();
    public UserResponseDTO getUser(Long id);
    public UserResponseDTO createUser(UserCreateDTO dto);
    public UserResponseDTO update(Long id, UserUpdateDTO dto);
    public void deleteUser(Long id);
    public Page<UserResponseDTO> search(String keyword, int page, int size);

    String verify(User user);

    //Excel export
    public void usersExcelExport(HttpServletResponse httpServletResponse) throws IOException;
}