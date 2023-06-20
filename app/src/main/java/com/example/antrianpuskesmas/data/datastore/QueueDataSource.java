package com.example.antrianpuskesmas.data.datastore;

import android.util.Log;

import com.example.antrianpuskesmas.data.Result;
import com.example.antrianpuskesmas.data.model.Category;
import com.example.antrianpuskesmas.data.model.Queue;
import com.example.antrianpuskesmas.data.ref.QueueStatus;
import com.example.antrianpuskesmas.data.response.MyQueueResponse;
import com.example.antrianpuskesmas.data.response.QueueResponse;
import com.example.antrianpuskesmas.util.Util;

import java.io.IOException;

import retrofit2.Response;

public class QueueDataSource {
    private static final String TAG = QueueDataSource.class.getSimpleName();

    public Result<QueueResponse> createQueue(String token, Category category) {
        try {

            Response<QueueResponse> execute = Util.queueService().createQueue(Util.getAuthHeader(token), category)
                    .execute();
            QueueResponse queue = execute.body();
            QueueResponse resp = new QueueResponse();


            Log.d(TAG, "createQueue: token: " + Util.getGson().toJson(token));
            Log.d(TAG, "createQueue: queue1: " + Util.getGson().toJson(queue));
            Log.d(TAG, "createQueue: http code: " + Util.getGson().toJson(execute.code()));

            if (queue != null) {
                resp = queue;
                if (resp.status) {
                    Log.d(TAG, "createQueue number: " + resp.data.getNomorAntrian());
                }
            } else {
                resp.message = "Session Anda telah berakhir , Silahkan Login ulang";
            }
            return new Result.Success<>(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<QueueResponse> latestQueue() {
        try {

            Response<QueueResponse> execute = Util.queueService().latestQueue()
                    .execute();
            QueueResponse queue = execute.body();
            QueueResponse resp = new QueueResponse();

            Log.d(TAG, "latestQueue: queue: " + Util.getGson().toJson(queue));

            if (queue != null) {
                resp = queue;
                if (resp.status) {
                    Log.d(TAG, "latestQueue number: " + resp.data.getNomorAntrian());
                }
            } else {
                resp.message = "Session Anda telah berakhir , Silahkan Login ulang";
            }
            return new Result.Success<>(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<MyQueueResponse> getMyQueue(String token, String status) {
        try {

            Response<MyQueueResponse> execute = Util.queueService().getMyQueue(Util.getAuthHeader(token), status)
                    .execute();
            MyQueueResponse queue = execute.body();
            MyQueueResponse resp = new MyQueueResponse();

            Log.d(TAG, "getMyQueue: queue: " + Util.getGson().toJson(queue));

            if (queue != null) {
                resp = queue;
                if (resp.status) {
                    Log.d(TAG, "getMyQueue number index 0: " + resp.data.get(0).getNomorAntrian());
                }
            } else {
                resp.message = "Session Anda telah berakhir , Silahkan Login ulang";
            }
            return new Result.Success<>(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }
}
