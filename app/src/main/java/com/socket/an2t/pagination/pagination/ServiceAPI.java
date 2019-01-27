package com.socket.an2t.pagination.pagination;

import com.socket.an2t.pagination.pagination.model.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceAPI {

    @GET("everything?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348")
    Observable<Response> getNews(@Query("page") int page, @Query("pageSize") int pageSize);
}
