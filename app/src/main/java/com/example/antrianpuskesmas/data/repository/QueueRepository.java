package com.example.antrianpuskesmas.data.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.antrianpuskesmas.data.RepositoryCallback;
import com.example.antrianpuskesmas.data.Result;
import com.example.antrianpuskesmas.data.datastore.QueueDataSource;
import com.example.antrianpuskesmas.data.model.Category;
import com.example.antrianpuskesmas.data.model.Queue;
import com.example.antrianpuskesmas.data.ref.QueueStatus;
import com.example.antrianpuskesmas.data.response.MyQueueResponse;
import com.example.antrianpuskesmas.data.response.QueueResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QueueRepository {

    private static final String TAG = QueueRepository.class.getSimpleName();
    private static volatile QueueRepository instance;

    private final Executor executor = Executors.newCachedThreadPool();
    private final Handler resultHandler = new Handler(Looper.getMainLooper());
    private final QueueDataSource dataSource;

    private QueueRepository(QueueDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static QueueRepository getInstance(QueueDataSource dataSource) {
        if (instance == null) {
            instance = new QueueRepository(dataSource);
        }
        return instance;
    }

    public void createQueue(final String token,
                            final Category category,
                            final RepositoryCallback<QueueResponse> callback) {
        executor.execute(() -> {
            Result<QueueResponse> result = dataSource.createQueue(token, category);
            if (result instanceof Result.Success) {
                Log.d(TAG, "createQueue: successful");
            }
            notifyQueueResult(result, callback);
        });
    }

    public void latestQueue(final RepositoryCallback<QueueResponse> callback) {
        executor.execute(() -> {
            Result<QueueResponse> result = dataSource.latestQueue();
            if (result instanceof Result.Success) {
                Log.d(TAG, "latestQueue: successful");
            }
            notifyQueueResult(result, callback);
        });
    }

    public void getMyQueue(final String token,
                           final String status,
                           final RepositoryCallback<MyQueueResponse> callback) {
        executor.execute(() -> {
            Result<MyQueueResponse> result = dataSource.getMyQueue(token, status);
            if (result instanceof Result.Success) {
                Log.d(TAG, "getMyQueue: successful");
            }
            notifyMyQueueResult(result, callback);
        });
    }

    private void notifyMyQueueResult(final Result<MyQueueResponse> result,
                                     final RepositoryCallback<MyQueueResponse> callback) {
        resultHandler.post(() -> callback.onComplete(result));
    }

    private void notifyQueueResult(final Result<QueueResponse> result,
                                   final RepositoryCallback<QueueResponse> callback) {
        resultHandler.post(() -> callback.onComplete(result));
    }


}
