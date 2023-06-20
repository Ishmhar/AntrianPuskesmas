package com.example.antrianpuskesmas.data.service;

import com.example.antrianpuskesmas.data.model.Category;
import com.example.antrianpuskesmas.data.model.Queue;
import com.example.antrianpuskesmas.data.ref.QueueStatus;
import com.example.antrianpuskesmas.data.response.MyQueueResponse;
import com.example.antrianpuskesmas.data.response.QueueResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QueueService {

    @POST("antrian/queue/user")
    Call<QueueResponse> createQueue(@Header("Authorization") String token,
                                    @Body Category category);

    @GET("antrian/queue/mk/terkini")
    Call<QueueResponse> latestQueue();

    @GET("antrian/queue/user/terkini/{status}")
    Call<MyQueueResponse> getMyQueue(@Header("Authorization") String token,
                                     @Path("status") String status);
}
