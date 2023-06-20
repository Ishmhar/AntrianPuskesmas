package com.example.antrianpuskesmas.data.response;

import com.example.antrianpuskesmas.data.model.Queue;

import java.io.Serializable;

public class QueueResponse implements Serializable {
    public boolean status;
    public String message;
    public Queue data;

}
