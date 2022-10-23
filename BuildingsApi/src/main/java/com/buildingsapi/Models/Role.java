package com.buildingsapi.Models;

public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_PROFESOR ("PROFESOR"),
    ROLE_USER ("USER");

    private final String _roleName;
    Role(String role) {
        _roleName = role;
    }

    public String getRoleString() {
        return _roleName;
    }

}