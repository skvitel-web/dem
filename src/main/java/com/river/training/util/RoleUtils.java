package com.river.training.util;

import com.river.training.entity.User;

public final class RoleUtils {

    private RoleUtils() {
    }

    /** Роль для Spring Security и фронтенда (ROLE_ADMIN / ROLE_USER). */
    public static String toSecurityRole(User user) {
        if (user.isSuperuser() || user.isStaff()) {
            return "ROLE_ADMIN";
        }
        String role = user.getRole();
        if (role == null) {
            return "ROLE_USER";
        }
        if ("ADMIN".equalsIgnoreCase(role) || "ROLE_ADMIN".equalsIgnoreCase(role)) {
            return "ROLE_ADMIN";
        }
        return "ROLE_USER";
    }
}
