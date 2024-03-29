package com.example.bixapp.network;

import com.example.bixapp.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostInterface {

    @GET("posts")
    Call<ResponseBody> getAll(@Query("_format") String format,
                                   @Query("access-token") String token,
                                   @Query("user_id") int userId
    );

}
