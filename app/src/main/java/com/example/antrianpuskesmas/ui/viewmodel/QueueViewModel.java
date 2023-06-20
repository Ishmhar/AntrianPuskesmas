package com.example.antrianpuskesmas.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.antrianpuskesmas.data.Result;
import com.example.antrianpuskesmas.data.datastore.QueueDataSource;
import com.example.antrianpuskesmas.data.model.Category;
import com.example.antrianpuskesmas.data.model.Queue;
import com.example.antrianpuskesmas.data.ref.QueueStatus;
import com.example.antrianpuskesmas.data.repository.QueueRepository;
import com.example.antrianpuskesmas.data.response.MyQueueResponse;
import com.example.antrianpuskesmas.data.response.QueueResponse;
import com.example.antrianpuskesmas.util.Util;

public class QueueViewModel extends ViewModel {

    private static final String TAG = QueueViewModel.class.getSimpleName();

    private final MutableLiveData<QueueResponse> createQueue = new MutableLiveData<>();
    private final MutableLiveData<QueueResponse> latestQueue = new MutableLiveData<>();

    private final MutableLiveData<MyQueueResponse> myQueue = new MutableLiveData<>();
    private final QueueRepository queueRepository;

    public QueueViewModel() {
        queueRepository = QueueRepository.getInstance(new QueueDataSource());
    }

    public LiveData<QueueResponse> createQueueResult() {
        return createQueue;
    }

    public LiveData<QueueResponse> latestQueueResult() {
        return latestQueue;
    }

    public LiveData<MyQueueResponse> getMyQueueResult() {
        return myQueue;
    }

    public void createQueue(String token,
                            Category category) {
        queueRepository.createQueue(token, category, result -> {
//            Log.d(TAG, "createQueue result: " + result);
            if (result instanceof Result.Success) {
                Log.d(TAG, "createQueue: berhasil ");
                QueueResponse resp = ((Result.Success<QueueResponse>) result).getData();
                createQueue.setValue(resp);
            } else {
                Log.d(TAG, "createQueue: gagal" + Util.getGson().toJson(result));
                createQueue.setValue(null);
            }
        });
    }

    public void latestQueue() {
        queueRepository.latestQueue(result -> {
//            Log.d(TAG, "latestQueue result: " + result);
            if (result instanceof Result.Success) {
                Log.d(TAG, "latestQueue: berhasil ");
                QueueResponse resp = ((Result.Success<QueueResponse>) result).getData();
                latestQueue.setValue(resp);
            } else {
                Log.d(TAG, "latestQueue: gagal" + Util.getGson().toJson(result));
                latestQueue.setValue(null);
            }
        });
    }

    public void getMyQueue(String token,
                           String status) {
        queueRepository.getMyQueue(token, status, result -> {
//            Log.d(TAG, "latestQueue result: " + result);
            if (result instanceof Result.Success) {
                Log.d(TAG, "getMyQueue: berhasil ");
                MyQueueResponse resp = ((Result.Success<MyQueueResponse>) result).getData();
                myQueue.setValue(resp);
            } else {
                Log.d(TAG, "getMyQueue: gagal" + Util.getGson().toJson(result));
                myQueue.setValue(null);
            }
        });
    }
}
