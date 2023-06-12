package com.example.antrianpuskesmas.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.antrianpuskesmas.R;
import com.example.antrianpuskesmas.SmartQueueApp;
import com.example.antrianpuskesmas.util.Util;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private SmartQueueApp app;
//    FirebaseAuth auth;
    Button btnLogout;
    TextView textView;
//    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = SmartQueueApp.getApplication(MainActivity.this);

//        auth = FirebaseAuth.getInstance();
        btnLogout = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
//        user = auth.getCurrentUser();
//        if (user == null) {
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//            finish();
            textView.setText("dummy");
//        } else {
//            textView.setText(user.getEmail());
//        }

        btnLogout.setOnClickListener(view -> {
            app.clearTokenPreferences();
            Util.goToLoginPage(this);


//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//            finish();


        });

    }
}