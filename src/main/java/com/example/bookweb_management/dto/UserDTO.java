package com.example.bookweb_management.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Username is not blank")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

    @NotBlank(message = "Password is not blank")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    @NotBlank(message = "Full name is not blank")
    private String fullname;

    @NotBlank(message = "Phone number is not blank")
    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must be 10 digits and start with 0")
    private String phoneNumber;

    @NotBlank(message = "Identity number is not blank")
    @Pattern(regexp = "\\d{12}$", message = "Citizen identification card must have 12 numbers")
    private String identityNumber;

    @Min(value = 1, message = "Age is not negative")
    @Max(value = 150, message = "Age is not valid")
    @NotNull(message = "Age is not null")
    private int age;

    @NotNull(message = "Birthday is not null")
    private LocalDate birthday;

    @NotBlank(message = "Address is not blank")
    private String address;

//    public UserDTO() {
//    }
//
//    public UserDTO(String username, String password, String fullname, String phoneNumber, String identityNumber, int age, LocalDate birthday, String address) {
//        this.username = username;
//        this.password = password;
//        this.fullname = fullname;
//        this.phoneNumber = phoneNumber;
//        this.identityNumber = identityNumber;
//        this.age = age;
//        this.birthday = birthday;
//        this.address = address;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFullname() {
//        return fullname;
//    }
//
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getIdentityNumber() {
//        return identityNumber;
//    }
//
//    public void setIdentityNumber(String identityNumber) {
//        this.identityNumber = identityNumber;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public LocalDate getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(LocalDate birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
}
