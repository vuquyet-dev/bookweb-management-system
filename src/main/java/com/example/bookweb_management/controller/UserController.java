package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.entity.User;
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

    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id)
    {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public UserResponseDTO createUser(@RequestBody UserCreateDTO dto)
    {
        return userService.createUser(dto);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id,@RequestBody @Valid UserUpdateDTO dto)
    {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }

    @GetMapping("/search")
    public Page<UserResponseDTO> search(String keyword, int page, int size)
    {
        return userService.search(keyword, page, size);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        System.out.println(user);
        return userService.verify(user);
    }
}
