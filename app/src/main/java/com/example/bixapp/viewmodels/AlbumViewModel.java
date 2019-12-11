package com.example.bixapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bixapp.constants.Constants;
import com.example.bixapp.model.Album;
import com.example.bixapp.model.ApiResponse;
import com.example.bixapp.model.ErrorResponse;
import com.example.bixapp.model.Post;
import com.example.bixapp.network.AlbumInterface;
import com.example.bixapp.network.RetrofitClientInstance;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AlbumViewModel extends ViewModel {

    private MutableLiveData<List<Album>> albums;
    private MutableLiveData<ErrorResponse> error;
    private MutableLiveData<Integer> page;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Integer> totalPages;

    public AlbumViewModel() {
        this.albums = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
        this.isLoading.setValue(false);
        this.page = new MutableLiveData<>();
        this.totalPages = new MutableLiveData<>();

        this.isLoading.setValue(false);
        this.page.setValue(1);
    }

    public LiveData<List<Album>> getAlbums() {
        return albums;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<ErrorResponse> getError() {
        return error;
    }

    public void loadPosts(int userId) {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        AlbumInterface pett = retrofitInstance.create(AlbumInterface.class);

        isLoading.setValue(true);
        Call<ResponseBody> call = pett.getAll("json", Constants.ACCESS_TOKEN, userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseBody) {
                try {
                    String responseJson = responseBody.body().string();
                    Gson gson = new Gson();
                    if (responseBody.code() == 200) {
                        Type type = new TypeToken<ApiResponse<List<Album>>>() {
                        }.getType();
                        ApiResponse<List<Album>> response = gson.fromJson(responseJson, type);

                        totalPages.setValue(response._meta.pageCount);

                        if (page.getValue() == 1) {
                            albums.setValue(new ArrayList<Album>());
                            albums.setValue(response.result);
                        } else {
                            List<Album> copy = getAlbums().getValue();
                            copy.addAll(response.result);
                            albums.setValue(copy);
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
                boolean debug = true;
                ErrorResponse localError = new ErrorResponse();
                localError.name = "Local Error";
                localError.message = debug ? t.getMessage() : "Lamentablemente no se pudo realizar la solicitud";
                error.setValue(localError);
                isLoading.setValue(false);
            }
        });
    }


    public void clearData() {
        albums.setValue(new ArrayList<Album>());
    }
}
