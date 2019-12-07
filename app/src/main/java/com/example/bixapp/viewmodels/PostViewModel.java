package com.example.bixapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bixapp.model.ApiResponse;
import com.example.bixapp.model.ErrorResponse;
import com.example.bixapp.model.Post;
import com.example.bixapp.network.PostInterface;
import com.example.bixapp.network.RetrofitClientInstance;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostViewModel extends ViewModel {
    private MutableLiveData<List<Post>> posts;
    private MutableLiveData<ErrorResponse> error;
    private MutableLiveData<Boolean> isLoading;

    public PostViewModel() {
        this.posts = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
        this.isLoading.setValue(false);
        loadPosts();
    }

    public LiveData<List<Post>> getUsers() {
        return posts;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<ErrorResponse> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }


    private void loadPosts() {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        PostInterface pett = retrofitInstance.create(PostInterface.class);

        isLoading.setValue(true);
        Call<ResponseBody> call = pett.getAllUsers("json", "kD9BK2GcPjswMEKCgeIvGutSfviZqTapKhm7");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseBody) {
                try {
                    String responseJson = responseBody.body().string();
                    Gson gson = new Gson();
                    if (responseBody.code() == 200) {
                        Type type = new TypeToken<ApiResponse<List<Post>>>() {
                        }.getType();
                        ApiResponse<List<Post>> response = gson.fromJson(responseJson, type);

                        if (getUsers().getValue() == null) {
                            posts.setValue(response.result);
                        } else {
                            List<Post> copy = getUsers().getValue();
                            copy.addAll(response.result);
                            posts.setValue(copy);
                        }
                    } else {
                        Type type = new TypeToken<ApiResponse<ErrorResponse>>() {
                        }.getType();
                        ApiResponse<ErrorResponse> response = gson.fromJson(responseJson, type);
                        error.setValue(response.result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    boolean debug = true;
                    ErrorResponse localError = new ErrorResponse();
                    localError.name = "Local Error";
                    localError.message = debug ? e.getMessage() : "Lamentablemente no se pudo realizar la solicitud";
                    error.setValue(localError);
                }

                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
