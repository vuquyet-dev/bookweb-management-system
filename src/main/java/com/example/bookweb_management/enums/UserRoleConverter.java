package com.example.bookweb_management.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserRole role) {
        return role != null ? role.getValue() : null;
    }

    @Override
    public UserRole convertToEntityAttribute(Integer dbValue) {
        return dbValue != null ? UserRole.fromValue(dbValue) : null;
    }
}
