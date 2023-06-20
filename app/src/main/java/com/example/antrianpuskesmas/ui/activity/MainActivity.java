package com.example.antrianpuskesmas.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.example.antrianpuskesmas.R;
import com.example.antrianpuskesmas.SmartQueueApp;
import com.example.antrianpuskesmas.data.model.Category;
import com.example.antrianpuskesmas.data.model.Queue;
import com.example.antrianpuskesmas.data.ref.QueueStatus;
import com.example.antrianpuskesmas.ui.viewmodel.QueueViewModel;
import com.example.antrianpuskesmas.util.CustomProgressDialog;
import com.example.antrianpuskesmas.util.Util;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SmartQueueApp app;
    private CustomProgressDialog progressDialog;
    private QueueViewModel queueViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = SmartQueueApp.getApplication(MainActivity.this);
        String token = app.getToken();


        Button btnLogout = findViewById(R.id.logout);
        TextView tvUser = findViewById(R.id.user_details);
        CardView cvDentalQueue = findViewById(R.id.cv_dental_queue);
        CardView cvGeneralQueue = findViewById(R.id.cv_general_queue);
        CardView cvChildQueue = findViewById(R.id.cv_child_queue);
        CardView cvShowQueue = findViewById(R.id.cv_show_queue);
        CardView cvProfile = findViewById(R.id.cv_profile);
        CardView cvMyTicket = findViewById(R.id.cv_my_ticket);
        View viewStatus = findViewById(R.id.view_status);

        tvUser.setText(String.format("%s %s", app.getFirstName(), app.getLastName()));

        progressDialog = new CustomProgressDialog(MainActivity.this);
        queueViewModel = new ViewModelProvider(this).get(QueueViewModel.class);
        queueViewModel.createQueueResult().observe(this, queue -> {
            progressDialog.dismiss();
            if (queue != null) {
//                showQueueDialog(queue.getNomorAntrian(), message);
//                app.setMyTicket(queue.getNomorAntrian());
//                app.setMyTicketDate(Util.parseDate(queue.getInsertDate()));
                if (queue.status) {

                    String message = getString(R.string.your_queue);
                    Queue data = queue.data;
                    showQueueDialog(data.getNomorAntrian(), message);
//                    app.setMyTicket(data.getNomorAntrian());
//                    viewStatus.setBackgroundColor(getResources().getColor(R.color.primary_dark, getTheme()));
//                    app.setMyTicketDate(Util.parseDate(data.getInsertDate()));
                } else {
                    Toast.makeText(this, queue.message, Toast.LENGTH_LONG).show();
                }

            } else {
//                Toast.makeText(this, "Token Expired\nSilihkan login ulang", Toast.LENGTH_SHORT).show();
            }
        });

        queueViewModel.latestQueueResult().observe(this, queue -> {
            progressDialog.dismiss();
            if (queue != null) {
//                String message = "Antrian saat ini adalah...";
//                showQueueDialog(queue.getNomorAntrian(), message);
                if (queue.status) {
                    Queue data = queue.data;
                    String message = "Antrian saat ini adalah...";
                    showQueueDialog(data.getNomorAntrian(), message);
                } else {
                    Toast.makeText(this, queue.message, Toast.LENGTH_LONG).show();
                }
            }
        });

        queueViewModel.getMyQueueResult().observe(this, myQueueResp -> {
            progressDialog.dismiss();
            if (myQueueResp != null) {
                if (myQueueResp.status) {
                    List<Queue> data = myQueueResp.data;
                    data.sort(Comparator.comparing(Queue::getId).reversed());
                    Queue lastData = data.get(0);

                    String title = String.format("Nomor antrian terakhir yang anda miliki pada Tanggal %s adalah",
                            Util.parseDate(lastData.getInsertDate()));
                    showQueueDialog(lastData.getNomorAntrian(), title);
                } else {
                    Toast.makeText(this, myQueueResp.message, Toast.LENGTH_LONG).show();
                }
            }
        });

        cvDentalQueue.setOnClickListener(v -> {
            Category cat = new Category();
            cat.setKategori("GIGI");
            confirmationQueueDialog(token, cat);
        });

        cvGeneralQueue.setOnClickListener(v -> {
            Category cat = new Category();
            cat.setKategori("UMUM");
            confirmationQueueDialog(token, cat);
        });

        cvChildQueue.setOnClickListener(v -> {
            Category cat = new Category();
            cat.setKategori("ANAK");
            confirmationQueueDialog(token, cat);
        });

        cvShowQueue.setOnClickListener(v -> {
            queueViewModel.latestQueue();
            progressDialog.show();
        });

        cvProfile.setOnClickListener(v -> {
            showProfileDialog(app);
        });

        cvMyTicket.setOnClickListener(v -> {
            queueViewModel.getMyQueue(token, QueueStatus.WAIT.getValue());
            progressDialog.show();
        });


//        if (!app.getMyTicket().isEmpty()) {
//            viewStatus.setBackgroundColor(getResources().getColor(R.color.primary_dark, getTheme()));
//            Log.d(TAG, "onResume: ticket not empty");
//        } else {
//            viewStatus.setBackgroundColor(getResources().getColor(R.color.white, getTheme()));
//            Log.d(TAG, "onResume: ticket empty");
//        }

        btnLogout.setOnClickListener(view -> logout());
    }

    @Override
    protected void onResume() {
        super.onResume();
/*        View viewStatus = findViewById(R.id.view_status);
        if (!app.getMyTicket().isEmpty()) {
            viewStatus.setBackgroundColor(getResources().getColor(R.color.primary_dark, getTheme()));
            Log.d(TAG, "onResume: ticket not empty");
        } else {
            viewStatus.setBackgroundColor(getResources().getColor(R.color.white, getTheme()));
        }

        CardView cvMyTicket = findViewById(R.id.cv_my_ticket);
        cvMyTicket.setOnClickListener(v -> {
            String myTicketDate = app.getMyTicketDate();
            String myTicket = app.getMyTicket();
            if (myTicket.isEmpty()) {
                myTicket = "Belum ada";
                myTicketDate = "";
            }
//            String message = getString(R.string.your_queue);
            String message = "Nomor antrian Anda pada Tanggal " + myTicketDate + " adalah";
            showQueueDialog(myTicket, message);
        });*/
    }

    private void showQueueDialog(String queue, String message) {
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_queue, null);
        TextView tvMessage = dialogView.findViewById(R.id.tv_message_dialog);
        TextView tvContent = dialogView.findViewById(R.id.tv_content_dialog);
        tvMessage.setText(message);
        tvContent.setText(queue);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Pemberitahuan!!!")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    private void confirmationQueueDialog(String token, Category cat) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Pemberitahuan!!!")
                .setMessage("Anda yakin ingin mengambil tiket antrian " + cat.getKategori() + "?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    queueViewModel.createQueue(token, cat);
                    progressDialog.show();
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    private void showProfileDialog(SmartQueueApp app) {
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_profile, null);
        TextInputEditText etFirstName = dialogView.findViewById(R.id.et_first_name_profile);
        TextInputEditText etLastName = dialogView.findViewById(R.id.et_last_name_profile);
        TextInputEditText etUsername = dialogView.findViewById(R.id.et_username_profile);
        TextInputEditText etEmail = dialogView.findViewById(R.id.et_email_profile);

        etFirstName.setText(app.getFirstName());
        etLastName.setText(app.getLastName());
        etUsername.setText(app.getUsername());
        etEmail.setText(app.getEmail());

        new MaterialAlertDialogBuilder(this)
                .setTitle("My Profile")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    private void logout() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage("Anda yakin ingin keluar dari aplikasi?")
                .setPositiveButton("Ya", (dialog, id) -> {
                    app.clearTokenPreferences();
                    Util.goToLoginPage(this);
                    dialog.dismiss();
                })
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel())
                .setCancelable(false)
                .show();
    }
}