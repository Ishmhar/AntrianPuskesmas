package com.example.antrianpuskesmas.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.antrianpuskesmas.data.Result;
import com.example.antrianpuskesmas.data.datastore.UserDataSource;
import com.example.antrianpuskesmas.data.model.AuthenticateRequest;
import com.example.antrianpuskesmas.data.model.User;
import com.example.antrianpuskesmas.data.repository.UserRepository;
import com.example.antrianpuskesmas.data.response.LoginResponse;
import com.example.antrianpuskesmas.data.response.RegisResponse;
import com.example.antrianpuskesmas.util.Util;

public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();

    private final MutableLiveData<LoginResponse> loginResult = new MutableLiveData<>();
    private final MutableLiveData<RegisResponse> regisResult = new MutableLiveData<>();
    private final UserRepository userRepository;
    private String username;
    private String password;

    public UserViewModel() {
        userRepository = UserRepository.getInstance(new UserDataSource());
    }

    public LiveData<LoginResponse> getLoginResult() {
        return loginResult;
    }

    public LiveData<RegisResponse> getRegisResult() {
        return regisResult;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void loginMobile(AuthenticateRequest request) {
        this.username = String.valueOf(request.getUsername());
        this.password = String.valueOf(request.getPassword());
        userRepository.loginMobile(request, result -> {
            Log.d(TAG, "loginMobile result: " + result);
            if (result instanceof Result.Success) {
                Log.d(TAG, "loginMobile: berhasil ");
                LoginResponse resp = ((Result.Success<LoginResponse>) result).getData();
                loginResult.setValue(resp);
            } else {
                Log.d(TAG, "loginMobile: gagal" + Util.getGson().toJson(result));
                loginResult.setValue(null);
            }
        });
    }


    public void regisMobile(User user) {
        userRepository.regisMobile(user, result -> {
            Log.d(TAG, "regisMobile result: " + result);
            if (result instanceof Result.Success) {
                Log.d(TAG, "regisMobile: berhasil ");
                RegisResponse resp = ((Result.Success<RegisResponse>) result).getData();
                regisResult.setValue(resp);
            } else {
                Log.d(TAG, "regisMobile: gagal" + Util.getGson().toJson(result));
                regisResult.setValue(null);
            }
        });
    }
}
