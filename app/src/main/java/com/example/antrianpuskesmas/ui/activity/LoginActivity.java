package com.example.antrianpuskesmas.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antrianpuskesmas.R;
import com.example.antrianpuskesmas.SmartQueueApp;
import com.example.antrianpuskesmas.data.model.AuthenticateRequest;
import com.example.antrianpuskesmas.data.model.User;
import com.example.antrianpuskesmas.ui.viewmodel.UserViewModel;
import com.example.antrianpuskesmas.util.CustomProgressDialog;
import com.example.antrianpuskesmas.util.Util;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private CustomProgressDialog progressDialog;
    private UserViewModel userViewModel;
    ProgressBar progressBar;
    TextView tvRegisNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressbar);
        tvRegisNow = findViewById(R.id.registerNow);
        tvRegisNow.setOnClickListener(view -> goToRegis());

        TextInputLayout ilUsername = findViewById(R.id.il_username_login);
        TextInputLayout ilPassword = findViewById(R.id.il_password_login);
        TextInputEditText etUsername = findViewById(R.id.et_username_login);
        TextInputEditText etPassword = findViewById(R.id.et_password_login);

        progressDialog = new CustomProgressDialog(LoginActivity.this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getLoginResult().observe(this, loginResp -> {
            progressDialog.dismiss();
            if (loginResp != null) {
//                Toast.makeText(this, loginResp.jwtToken, Toast.LENGTH_SHORT).show();

                if (loginResp.status) {
                    User data = loginResp.data;
                    String password = userViewModel.getPassword();
                    SmartQueueApp.getApplication(LoginActivity.this).setLoginPref(data.user, data.jwtToken, password);
                    Util.goToHomePage(this);
                } else {
                    Toast.makeText(this, loginResp.message, Toast.LENGTH_SHORT).show();
                }
            } else {
//                Toast.makeText(this, "Username atau Password salah, silahkan periksan kembali", Toast.LENGTH_LONG).show();
            }
        });

        Button buttonByPass = findViewById(R.id.btn_by_pass);
        buttonByPass.setOnClickListener(v -> Util.goToHomePage(this));

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            String username = Objects.requireNonNull(etUsername.getText()).toString().trim();
            String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

            if (username.isEmpty()) {
                ilUsername.setHelperText("*Harus diisi");
            } else {
                ilUsername.setHelperTextEnabled(false);
            }

            if (password.isEmpty()) {
                ilPassword.setHelperText("*Harus diisi");
            } else {
                ilPassword.setHelperTextEnabled(false);
            }

            if (!username.isEmpty() && !password.isEmpty()) {
                AuthenticateRequest request = new AuthenticateRequest(username, password);
                userViewModel.loginMobile(request);
                progressDialog.show();

                Log.d(TAG, "Login with Username and Password");
                Log.d(TAG, "login: username: " + username +
                        ", password: " + password);
            }
        });
    }

    private void goToRegis() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}