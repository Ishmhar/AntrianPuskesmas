package com.example.antrianpuskesmas.data;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
