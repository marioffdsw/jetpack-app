package com.example.bixapp.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlbumInterface {

    @GET("albums")
    Call<ResponseBody> getAll(@Query("_format") String format,
                                   @Query("access-token") String token,
                                   @Query("user_id") int userId
    );

}
