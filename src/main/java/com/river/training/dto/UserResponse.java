package com.river.training.dto;

import com.river.training.entity.User;
import com.river.training.util.RoleUtils;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        String birthDate,
        String role
) {
    public static UserResponse from(User user) {
        String birth = user.getBirthDate() != null ? user.getBirthDate().toString() : null;
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                birth,
                RoleUtils.toSecurityRole(user)
        );
    }
}
