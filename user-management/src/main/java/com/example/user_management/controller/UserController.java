package com.example.user_management.controller;

import com.example.user_management.entity.User;
import com.example.user_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 📌 1. Hiển thị danh sách user
    @GetMapping
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;

        if (keyword != null && !keyword.isEmpty()) {
            userPage = userService.searchUsers(keyword, pageable);
            model.addAttribute("keyword", keyword); // giữ từ khóa khi quay lại trang
        } else {
            userPage = userService.getPaginatedUsers(pageable);
        }
        if (userPage.getContent().isEmpty()) {
            model.addAttribute("empty", true);
        }

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("size", size);
        return "user-list";
    }

    // 📌 2. Hiển thị form tạo user mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user"; // file create-user.html
    }

    // 📌 3. Tạo mới user
    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("message", "✅ Tạo người dùng thành công!");
        return "redirect:/users";
    }


    // 📌 4. Hiển thị form cập nhật
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "edit-user";
        } else {
            return "redirect:/users"; // hoặc redirect tới trang thông báo lỗi
        }
    }


    // 📌 5. Cập nhật user
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    // 📌 6. Xoá user
    @PostMapping("/delete/{id}")
    public String delUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.delUser(id);
        redirectAttributes.addFlashAttribute("message", "🗑 Xoá người dùng thành công!");
        return "redirect:/users";
    }


}
