package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.UserDTO;
import com.example.bookweb_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username)
    {
        return userService.getUser(username);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO dto)
    {
        return userService.createUser(dto);
    }

    @PutMapping("/{username}")
    public UserDTO update(@PathVariable String username,@RequestBody @Valid UserDTO dto)
    {
        return userService.update(username, dto);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username)
    {
        userService.deleteUser(username);
    }

    @GetMapping("/search")
    public Page<UserDTO> search(String keyword, int page, int size)
    {
        return userService.search(keyword, page, size);
    }
}
