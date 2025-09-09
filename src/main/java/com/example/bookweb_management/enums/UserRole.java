package com.example.bookweb_management.enums;

public enum UserRole {
    ADMIN(0), USER(1);

    private final int value;


    UserRole(int value) {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static UserRole fromValue(int value)
    {
        return (value == 0) ? ADMIN : USER;
    }
}
