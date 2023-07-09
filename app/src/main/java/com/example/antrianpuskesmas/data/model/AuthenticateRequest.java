package com.example.antrianpuskesmas.data.model;

public class AuthenticateRequest {

    private String username;
    private String password;

    public AuthenticateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}