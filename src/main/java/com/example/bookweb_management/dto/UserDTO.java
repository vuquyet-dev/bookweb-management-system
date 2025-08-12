package com.example.bookweb_management.dto;

import com.example.bookweb_management.constant.UserConstants;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = UserConstants.MSG_USERNAME_BLANK)
    @Size(min = 4, max = 30, message = UserConstants.MSG_USERNAME_SIZE)
    @Pattern(regexp = UserConstants.USERNAME_PATTERN, message = UserConstants.MSG_USERNAME_SIZE)
    private String username;

    @NotBlank(message = UserConstants.MSG_PASSWORD_BLANK)
    @Size(min = 8, max = 50, message = UserConstants.MSG_PASSWORD_SIZE)
    @Pattern(regexp = UserConstants.PASSWORD_PATTERN, message = UserConstants.MSG_PASSWORD_SIZE)
    private String password;

    @NotBlank(message = "Full name is not blank")
    private String fullname;

    @NotBlank(message = UserConstants.MSG_PHONE_BLANK)
    @Pattern(regexp = UserConstants.PHONE_PATTERN, message = UserConstants.MSG_PHONE_INVALID)
    private String phoneNumber;

    @NotBlank(message = UserConstants.MSG_IDENTITY_BLANK)
    @Pattern(regexp = UserConstants.IDENTITY_NUMBER_PATTERN, message = UserConstants.MSG_IDENTITY_INVALID)
    private String identityNumber;

    @Min(value = 1, message = UserConstants.MSG_AGE_MIN)
    @Max(value = 150, message = UserConstants.MSG_AGE_MAX)
    @NotNull(message = UserConstants.MSG_AGE_MIN)
    private int age;

    @NotNull(message = UserConstants.MSG_BIRTHDAY_NULL)
    private LocalDate birthday;

    @NotBlank(message = UserConstants.MSG_ADDRESS_BLANK)
    private String address;
}
