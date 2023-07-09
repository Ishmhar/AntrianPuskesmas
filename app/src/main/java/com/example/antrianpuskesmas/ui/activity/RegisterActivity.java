package com.example.antrianpuskesmas.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antrianpuskesmas.R;
import com.example.antrianpuskesmas.SmartQueueApp;
import com.example.antrianpuskesmas.data.model.User;
import com.example.antrianpuskesmas.data.model.UserDetail;
import com.example.antrianpuskesmas.ui.viewmodel.UserViewModel;
import com.example.antrianpuskesmas.util.CustomProgressDialog;
import com.example.antrianpuskesmas.util.Util;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private UserViewModel userViewModel;
    private CustomProgressDialog progressDialog;
    private TextInputLayout ilFirstName;
    private TextInputLayout ilLastName;
    private TextInputLayout ilEmail;
    private TextInputLayout ilUsername;
    private TextInputLayout ilPassword;
    private TextInputLayout ilPasswordConfirm;
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etEmail;
    private TextInputEditText etUsername;
    private TextInputEditText etPasswordRegis;
    private TextInputEditText etPasswordRegisConfirm;

    private TextView tvGoToLogin;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;
        progressDialog = new CustomProgressDialog(RegisterActivity.this);

        ilFirstName = findViewById(R.id.il_first_name);
        ilLastName = findViewById(R.id.il_last_name);
        ilEmail = findViewById(R.id.il_email);
        ilUsername = findViewById(R.id.il_username_regis);
        ilPassword = findViewById(R.id.il_password_regis);
        ilPasswordConfirm = findViewById(R.id.il_password_regis_confirm);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etUsername = findViewById(R.id.et_username_regis);
        etPasswordRegis = findViewById(R.id.et_password_regis);
        etPasswordRegisConfirm = findViewById(R.id.et_password_regis_confirm);
        tvGoToLogin = findViewById(R.id.login_now);

        String textLoginNow = getString(R.string.Kembali_Login);
        SpannableString ss = new SpannableString(textLoginNow);
        ClickableSpan csAccount = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Util.goToLoginPage((Activity) context);
            }
        };


        ss.setSpan(csAccount, 11, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvGoToLogin.setText(ss);
        tvGoToLogin.setMovementMethod(LinkMovementMethod.getInstance());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getRegisResult().observe(this, register -> {
            progressDialog.dismiss();
            if (register != null) {
//                Toast.makeText(this, register.jwtToken, Toast.LENGTH_SHORT).show();

                if (register.status) {
                    User data = register.data;
                    String password = userViewModel.getPassword();
                    SmartQueueApp.getApplication(RegisterActivity.this).setLoginPref(data.user, data.jwtToken, password);
                    Util.goToHomePage(this);
                } else {
                    Toast.makeText(context, register.message, Toast.LENGTH_SHORT).show();
                }
            } else {
//                Toast.makeText(context, "Silahkan Register ulang, email sudah terdaftar", Toast.LENGTH_LONG).show();
            }
        });

        Button btnRegis = findViewById(R.id.btn_register);
        btnRegis.setOnClickListener(v -> submitListener());

    }

    private void submitListener() {
        String strFirstName = Objects.requireNonNull(etFirstName.getText()).toString().trim();
        String strLastName = Objects.requireNonNull(etLastName.getText()).toString().trim();
        String strEmail = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String strUsername = Objects.requireNonNull(etUsername.getText()).toString().trim();
        String strPasswordRegis = Objects.requireNonNull(etPasswordRegis.getText()).toString().trim();
        String strPasswordRegisConfirm = Objects.requireNonNull(etPasswordRegisConfirm.getText()).toString().trim();

        if (strFirstName.isEmpty()) {
            ilFirstName.setHelperText(getResources().getString(R.string.must_be_filled));
        } else {
            ilFirstName.setHelperTextEnabled(false);
        }

        if (strLastName.isEmpty()) {
            ilLastName.setHelperText(getResources().getString(R.string.must_be_filled));
        } else {
            ilLastName.setHelperTextEnabled(false);
        }


        if (strEmail.isEmpty()) {
            ilEmail.setHelperText(getResources().getString(R.string.must_be_filled));
        } else if (!strEmail.isEmpty() && !isValidEmailId(strEmail)) {
            ilEmail.setHelperText("*Alamat Email tidak sesuai");
        } else {
            ilEmail.setHelperTextEnabled(false);
        }

        if (strUsername.isEmpty()) {
            ilUsername.setHelperText(getResources().getString(R.string.must_be_filled));
        } else {
            ilUsername.setHelperTextEnabled(false);
        }

        if (strPasswordRegis.isEmpty()) {
            ilPassword.setHelperText(getResources().getString(R.string.must_be_filled));
        } else {
            ilPassword.setHelperTextEnabled(false);
        }

        if (strPasswordRegisConfirm.isEmpty()) {
            ilPasswordConfirm.setHelperText(getResources().getString(R.string.must_be_filled));
        } else if (!strPasswordRegisConfirm.equals(strPasswordRegis)) {
            ilPasswordConfirm.setHelperText("*Password tidak sesuai");
        } else {
            ilPasswordConfirm.setHelperTextEnabled(false);
        }

        if (!strFirstName.isEmpty()
                && !strLastName.isEmpty()
                && !strUsername.isEmpty()
                && !strEmail.isEmpty() && isValidEmailId(strEmail)
                && !strPasswordRegis.isEmpty() && !strPasswordRegisConfirm.isEmpty()
                && strPasswordRegis.equals(strPasswordRegisConfirm)) {
            new MaterialAlertDialogBuilder(RegisterActivity.this)
                    .setTitle("Buat Akun")
                    .setMessage("Apakah anda yakin data anda sudah benar?")
                    .setPositiveButton("Ya", (dialog, which) -> {

                        UserDetail regis = new UserDetail();
                        regis.setFirstname(strFirstName);
                        regis.setLastname(strLastName);
                        regis.setEmail(strEmail);
                        regis.setUsername(strUsername);
                        regis.setPassword(strPasswordRegis);

                        Log.d(TAG, "submitListener: req : " + Util.getGson().toJson(regis));

                        userViewModel.regisMobile(regis);
                        progressDialog.show();
                    })
                    .setNegativeButton("Tidak", (dialog, which) -> dialog.cancel())
                    .show();
        }
    }

    private boolean isValidEmailId(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}