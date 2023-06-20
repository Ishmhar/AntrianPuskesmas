package com.example.antrianpuskesmas.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.antrianpuskesmas.R;
import com.example.antrianpuskesmas.SmartQueueApp;
import com.example.antrianpuskesmas.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SmartQueueApp app = SmartQueueApp.getApplication(SplashActivity.this);
//        checkDate(app);

        new Handler().postDelayed(() -> {
            if (app.getToken() != null && !app.getToken().isEmpty()) {
                Util.goToHomePage(this);
            } else {
                Log.d(TAG, "onCreate: token length size : " + app.getToken().length());
                Util.goToLoginPage(this);
            }
        }, 2000);
    }

    /*@SuppressLint("SimpleDateFormat")
    private void checkDate(SmartQueueApp app) {
        Date dateNow = new Date();
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String dateNowString = outputFormat.format(dateNow);
        String dateNowString = Util.PARAM_DATE_FORMAT.format(dateNow);

        if (!app.getMyTicketDate().equals(dateNowString)) {
            Log.d(TAG, "onCreate: Tanggal tidak sama");
            app.setMyTicket("");
        } else {
            Log.d(TAG, "onCreate: Tanggal sama");
        }
    }*/
}