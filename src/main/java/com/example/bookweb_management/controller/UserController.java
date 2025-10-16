package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.userdto.UserCreateDTO;
import com.example.bookweb_management.dto.userdto.UserResponseDTO;
import com.example.bookweb_management.dto.userdto.UserUpdateDTO;
import com.example.bookweb_management.entity.User;
import com.example.bookweb_management.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public UserResponseDTO getUser(@PathVariable Long id)
    {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public UserResponseDTO createUser(@RequestBody @Valid UserCreateDTO dto)
    {
        System.out.println(dto);
        return userService.createUser(dto);
    }

    @PutMapping("/update/{id}")
    public UserResponseDTO update(@PathVariable Long id,@RequestBody @Valid UserUpdateDTO dto)
    {
        return userService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
    }

    @GetMapping("/search")
    public Page<UserResponseDTO> search(@RequestParam(defaultValue = "") String keyword,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size)
    {
        return userService.search(keyword, page, size);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        System.out.println(user);
        return userService.verify(user);
    }

    @GetMapping("/excel")
    public void excelExport(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//định dạng Excel 2007+

        //httpServletResponse.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=users-list.xlsx";

        httpServletResponse.setHeader(headerKey, headerValue);

        userService.usersExcelExport(httpServletResponse);
    }
}
