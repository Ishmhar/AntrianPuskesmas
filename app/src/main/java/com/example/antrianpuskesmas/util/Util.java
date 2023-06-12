package com.example.antrianpuskesmas.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;

import com.example.antrianpuskesmas.data.service.UserService;
import com.example.antrianpuskesmas.ui.activity.LoginActivity;
import com.example.antrianpuskesmas.ui.activity.MainActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SimpleDateFormat")
public class Util {
    public static final String BASE_URL = "http://206.189.95.190:8080/api/v1/";  //SKF_ELA
    public static final DateFormat DTF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public static final DateFormat TF = new SimpleDateFormat("HH:mm");
    public static final DateFormat PARAM_DATEFORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static Retrofit retrofit;
    private static Gson gson;


    public static Gson getGson() {
        if (gson == null) {
            JsonSerializer<Date> ser = (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.getTime());
            JsonDeserializer<Date> deser = (json, typeOfT, context) -> json == null ? null : new Date(json.getAsLong());
            GsonBuilder builder = new GsonBuilder().registerTypeAdapter(Date.class, ser)
                    .registerTypeAdapter(Date.class, deser).disableHtmlEscaping();
            gson = builder.create();
        }
        return gson;
    }

    public static Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }
        return retrofit;
    }

    public static UserService loginService() {
        return retrofit().create(UserService.class);
    }


    @SuppressLint("SimpleDateFormat")
    public static void datePicker(TextInputEditText etDate, Button btnDate, FragmentManager getSupportFragmentManager) {
        MaterialDatePicker<Long> startDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("SELECT A DATE")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        btnDate.setOnClickListener(v -> {
            startDatePicker.show(getSupportFragmentManager, "Material_Date_Picker");
            startDatePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(selection);
                etDate.setText(simpleDateFormat.format(date));
            });
            startDatePicker.setCancelable(false);
        });
    }

    public static void goToHomePage(Activity activity) {
        Intent goToMainPage = new Intent(activity, MainActivity.class);
        activity.startActivity(goToMainPage);
        activity.finish();
    }

    public static void goToLoginPage(Activity activity) {
        Intent goToLoginPage = new Intent(activity, LoginActivity.class);
        activity.startActivity(goToLoginPage);
        activity.finish();
    }
}