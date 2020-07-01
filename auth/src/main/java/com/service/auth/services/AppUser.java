package com.service.auth.services;

import lombok.Data;

@Data
public class AppUser {
    private Integer id;
    private String password;
    private String username;
    private String role;

    public AppUser(Integer id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
