package com.example.antrianpuskesmas.data.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.antrianpuskesmas.data.RepositoryCallback;
import com.example.antrianpuskesmas.data.Result;
import com.example.antrianpuskesmas.data.datastore.UserDataSource;
import com.example.antrianpuskesmas.data.model.AuthenticateRequest;
import com.example.antrianpuskesmas.data.model.UserDetail;
import com.example.antrianpuskesmas.data.response.LoginResponse;
import com.example.antrianpuskesmas.data.response.RegisResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private static volatile UserRepository instance;

    private final Executor executor = Executors.newCachedThreadPool();
    private final Handler resultHandler = new Handler(Looper.getMainLooper());
    private final UserDataSource dataSource;

    private UserRepository(UserDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserRepository getInstance(UserDataSource dataSource) {
        if (instance == null) {
            instance = new UserRepository(dataSource);
        }
        return instance;
    }

    public void loginMobile(AuthenticateRequest request,
                            final RepositoryCallback<LoginResponse> callback) {
        executor.execute(() -> {
            Result<LoginResponse> result = dataSource.loginMobile(request);
            if (result instanceof Result.Success) {
                Log.d(TAG, "loginMobile: successful");
            }
            notifyLoginResponseResult(result, callback);
        });
    }

    public void regisMobile(UserDetail user,
                            final RepositoryCallback<RegisResponse> callback) {
        executor.execute(() -> {
            Result<RegisResponse> result = dataSource.regisMobile(user);
            if (result instanceof Result.Success) {
                Log.d(TAG, "regisMobile: successful");
            }
            notifyRegisResponseResult(result, callback);
        });
    }

    private void notifyLoginResponseResult(final Result<LoginResponse> result,
                                           final RepositoryCallback<LoginResponse> callback) {
        resultHandler.post(() -> callback.onComplete(result));
    }

    private void notifyRegisResponseResult(final Result<RegisResponse> result,
                                           final RepositoryCallback<RegisResponse> callback) {
        resultHandler.post(() -> callback.onComplete(result));
    }


}
