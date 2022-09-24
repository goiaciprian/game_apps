package com.gameapi.Models;

public enum Role {
    ROLE_ADMIN ("ROLE_ADMIN"),
    ROLE_USER ("ROLE_USER");

    private final String _roleName;
    Role(String role) {
        _roleName = role;
    }

    public String getRoleString() {
        return _roleName;
    }

}
