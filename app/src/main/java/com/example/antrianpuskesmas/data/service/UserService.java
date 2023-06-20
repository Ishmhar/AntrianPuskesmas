package com.example.antrianpuskesmas.data.service;

import com.example.antrianpuskesmas.data.model.AuthenticateRequest;
import com.example.antrianpuskesmas.data.model.UserDetail;
import com.example.antrianpuskesmas.data.response.LoginResponse;
import com.example.antrianpuskesmas.data.response.RegisResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/login")
    Call<LoginResponse> loginMobile(@Body AuthenticateRequest request);

    @POST("user/regis")
    Call<RegisResponse> regisMobile(@Body UserDetail regis);

}
