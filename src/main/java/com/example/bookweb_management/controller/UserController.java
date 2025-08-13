package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public UserResponseDTO getUser(@PathVariable String username)
    {
        return userService.getUser(username);
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserCreateDTO dto)
    {
        return userService.createUser(dto);
    }

    @PutMapping("/{username}")
    public UserResponseDTO update(@PathVariable String username,@RequestBody @Valid UserUpdateDTO dto)
    {
        return userService.update(username, dto);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username)
    {
        userService.deleteUser(username);
    }

    @GetMapping("/search")
    public Page<UserResponseDTO> search(String keyword, int page, int size)
    {
        return userService.search(keyword, page, size);
    }
}
