package com.example.multidatasoure.dto;

import com.example.multidatasoure.entity.primary.Role;

/**
 * Data transfer object for user information used in requests.
 * Explicit getters and setters are provided to avoid relying on
 * annotation processing during compilation.
 */
public class UserDto {
    private String username;
    private String email;
    private String password;
    private Role role;

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
