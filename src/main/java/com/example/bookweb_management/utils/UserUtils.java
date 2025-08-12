package com.example.bookweb_management.utils;

public class UserUtils {
    private UserUtils() {}

    //xử lý username: trim và bỏ space
    public static String normalizeUsername(String username)
    {
        if(username == null) return null;
        return username.trim().replaceAll("\\s+", "");
    }

    //xử lý password: trim
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
}
