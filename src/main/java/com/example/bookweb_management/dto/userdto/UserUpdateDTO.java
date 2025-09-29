package com.example.bookweb_management.dto.userdto;

import com.example.bookweb_management.constant.UserConstants;
import com.example.bookweb_management.entity.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
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

    @NotEmpty(message = "Role must be not empty")
    private Set<Long> roleIds = new HashSet<>();
}
