package com.example.antrianpuskesmas.data.response;

import com.example.antrianpuskesmas.data.model.User;

import java.io.Serializable;

public class RegisResponse implements Serializable {
    public boolean status;
    public String message;
    public User data;
//    public String jwtToken;
//    public User user;
}


