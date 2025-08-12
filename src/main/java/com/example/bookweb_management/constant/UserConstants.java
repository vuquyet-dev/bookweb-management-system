package com.example.bookweb_management.constant;

public final class UserConstants {

    private UserConstants() {
        // Ngăn không cho khởi tạo class này
    }

    // ====== Message constants ======
    public static final String MSG_USERNAME_BLANK = "Username must not be blank";
    public static final String MSG_USERNAME_SIZE = "Username must be between 4 and 30 characters";
    public static final String MSG_PASSWORD_BLANK = "Password must not be blank";
    public static final String MSG_PASSWORD_SIZE = "Password must be between 8 and 50 characters";
    public static final String MSG_PHONE_BLANK = "Phone number must not be blank";
    public static final String MSG_PHONE_INVALID = "Phone number must be 10 digits and start with 0";
    public static final String MSG_IDENTITY_BLANK = "Identity number must not be blank";
    public static final String MSG_IDENTITY_INVALID = "Citizen identification card must have 12 numbers";
    public static final String MSG_AGE_MIN = "Age is not negative";
    public static final String MSG_AGE_MAX = "Age is not valid";
    public static final String MSG_BIRTHDAY_NULL = "Birthday must not be null";
    public static final String MSG_ADDRESS_BLANK = "Address must not be blank";

    // ====== Pattern constants ======
    public static final String USERNAME_PATTERN = "^[^\\s]{4,30}$"; // Không chứa khoảng trắng, 4-30 ký tự
    public static final String PASSWORD_PATTERN = "^[^\\s]{8,50}$"; // Không chứa khoảng trắng, 8-50 ký tự
    public static final String PHONE_PATTERN = "^0\\d{9}$"; // 10 số, bắt đầu bằng 0
    public static final String IDENTITY_NUMBER_PATTERN = "^\\d{12}$"; // 12 số
}
