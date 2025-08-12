package com.example.bookweb_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "identity_number", nullable = false, unique = true)
    private String identityNumber;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String address;

//    public User() {
//    }
//
//    public User(String username, String password, String fullname, String phoneNumber, String identityNumber, int age, LocalDate birthday, String address) {
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
