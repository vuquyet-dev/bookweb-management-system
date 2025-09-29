package com.example.bookweb_management.dto.userdto;

import com.example.bookweb_management.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String fullname;
    private String phoneNumber;
    private String identityNumber;
    private int age;
    private LocalDate birthday;
    private String address;
    private Set<Role> roles; // tên tham số phải giống bên mapping là class user, nếu không thì phải @Mapping bên Mapper
}
