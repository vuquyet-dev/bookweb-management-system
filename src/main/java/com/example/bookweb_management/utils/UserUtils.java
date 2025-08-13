package com.example.bookweb_management.utils;

import com.example.bookweb_management.dto.UserCreateDTO;

public class UserUtils {
    private UserUtils() {}

    //xử lý username: trim và bỏ space
    public static String normalizeUsername(String username)
    {
        if(username == null) return null;
        return username.trim().replaceAll("\\s+", "");
    }

    //xử lý password: trim
    public static String normalizePassword(String password) {
        if (password == null) return null;
        return password.trim();
    }

    //xử lý full name: trim
    public static String normalizeFullname(String fullname)
    {
        if(fullname == null) return null;
        return fullname.trim();
    }

    //xử lý phone number: trim và bỏ space
    public static String normalizePhoneNumber(String phoneNumber)
    {
        if(phoneNumber == null) return null;
        return phoneNumber.trim().replaceAll("\\s+", "");
    }

    //xử lý identity number: trim và bỏ space
    public static String normalizeIdentityNumber(String identityNumber)
    {
        if(identityNumber == null) return null;
        return identityNumber.trim().replaceAll("\\s+", "");
    }

    //xử lý address: trim
    public static String normalizeAddress(String address)
    {
        if(address == null) return null;
        return address.trim();
    }

    //check username contain space
    public static boolean containWhiteSpace(String username)
    {
        return username != null && username.matches(".*\\s+.*");
    }

    //sửa dữ liệu đầu vào
    public static UserCreateDTO normalizeUserFields(UserCreateDTO dto)
    {
        dto.setUsername(UserUtils.normalizeUsername(dto.getUsername()));
        dto.setPassword(UserUtils.normalizeUsername(dto.getPassword()));
        dto.setFullname(UserUtils.normalizeUsername(dto.getFullname()));
        dto.setPhoneNumber(UserUtils.normalizeUsername(dto.getPhoneNumber()));
        dto.setIdentityNumber(UserUtils.normalizeUsername(dto.getIdentityNumber()));
        dto.setAddress(UserUtils.normalizeUsername(dto.getAddress()));

        return dto;
    }
}
