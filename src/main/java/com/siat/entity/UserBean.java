package com.siat.entity;

import java.util.HashSet;
import java.util.Set;

public class UserBean {

    private String username;

    private String password;

    private Set<String> roles = new HashSet<>();

    private Set<String> permissions = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }
}
