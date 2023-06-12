package com.example.antrianpuskesmas.data.datastore;

import android.util.Log;

import com.example.antrianpuskesmas.data.Result;
import com.example.antrianpuskesmas.data.model.AuthenticateRequest;
import com.example.antrianpuskesmas.data.model.User;
import com.example.antrianpuskesmas.data.response.LoginResponse;
import com.example.antrianpuskesmas.data.response.RegisResponse;
import com.example.antrianpuskesmas.util.Util;

import java.io.IOException;

import retrofit2.Response;

public class UserDataSource {
    private static final String TAG = UserDataSource.class.getSimpleName();

    public Result<LoginResponse> loginMobile(AuthenticateRequest request) {
        try {

            Response<LoginResponse> execute = Util.loginService().loginMobile(request)
                    .execute();
            LoginResponse loginResp = execute.body();

            Log.d(TAG, "loginMobile: loginResponse: " + Util.getGson().toJson(loginResp));

            if (loginResp != null) {
                Log.d(TAG, "loginMobile code: " + loginResp.jwtToken);
            }
            return new Result.Success<>(loginResp);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<RegisResponse> regisMobile(User user) {
        try {

            Response<RegisResponse> execute = Util.loginService().regisMobile(user)
                    .execute();
            RegisResponse regisResp = execute.body();

            Log.d(TAG, "regisMobile: regisResponse: " + Util.getGson().toJson(regisResp));

            if (regisResp != null) {
                Log.d(TAG, "regisMobile code: " + regisResp.jwtToken);
            }
            return new Result.Success<>(regisResp);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }
}
