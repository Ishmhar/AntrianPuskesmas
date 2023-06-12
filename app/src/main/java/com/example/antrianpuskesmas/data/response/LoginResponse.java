package com.example.antrianpuskesmas.data.response;

import com.example.antrianpuskesmas.data.model.User;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public String jwtToken;
    public User user;
}
