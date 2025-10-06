package com.example.bookweb_management.dto.borrowrecorddto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse{
    private Long id;
    private String username;
    private String fullname;
    private String phoneNumber;
    private String identityNumber;
    private int age;
    private LocalDate birthday;
    private String address;
}
