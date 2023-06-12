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
import com.example.antrianpuskesmas.ui.viewmodel.UserViewModel;
import com.example.antrianpuskesmas.util.CustomProgressDialog;
import com.example.antrianpuskesmas.util.Util;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
//    TextInputEditText editTextEmail, editTextPassword;
//    FirebaseAuth mAuth;
//    Button buttonLogin;

    private static final String TAG = LoginActivity.class.getSimpleName();
    private CustomProgressDialog progressDialog;
    private UserViewModel userViewModel;
    ProgressBar progressBar;
    TextView tvRegisNow;

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        mAuth = FirebaseAuth.getInstance();
//        editTextEmail = findViewById(R.id.email);
//        editTextPassword = findViewById(R.id.password);

//        buttonLogin = findViewById(R.id.btn_login);
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
                Toast.makeText(this, loginResp.jwtToken, Toast.LENGTH_SHORT).show();
                String password = userViewModel.getPassword();
                SmartQueueApp.getApplication(LoginActivity.this).setLoginPref(loginResp.user, loginResp.jwtToken, password);
                Util.goToHomePage(this);
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

//        buttonLogin.setOnClickListener(view -> {
//            progressBar.setVisibility(View.VISIBLE);
//            String email, password;
//            email = String.valueOf(editTextEmail.getText());
//            password = String.valueOf(editTextPassword.getText());
//
//            if (TextUtils.isEmpty(email)) {
//                Toast.makeText(LoginActivity.this, "Silahkan masukkan Email.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (TextUtils.isEmpty(password)) {
//                Toast.makeText(LoginActivity.this, "Silahkan masukkan Password.", Toast.LENGTH_SHORT).show();
//                return;
//            }

//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(task -> {
//                        progressBar.setVisibility(View.GONE);
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Masuk Akun berhasil.", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Gagal Masuk Akun.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        });
    }

//    private void goToHomePage() {
//        Intent goToMainPage = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(goToMainPage);
//        finish();
//    }

    private void goToRegis() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}