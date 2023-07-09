package com.example.antrianpuskesmas.data.response;

import com.example.antrianpuskesmas.data.model.User;
import com.example.antrianpuskesmas.data.model.UserDetail;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    public boolean status;
    public String message;
    public User data;
//    public String jwtToken;
//    public UserDetail user;
}
