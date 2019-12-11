package com.example.bixapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bixapp.model.Photo;
import com.example.bixapp.model.ApiResponse;
import com.example.bixapp.model.ErrorResponse;
import com.example.bixapp.network.PhotoInterface;
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

public class PhotoViewModel extends ViewModel {

    private MutableLiveData<List<Photo>> photos;
    private MutableLiveData<ErrorResponse> error;
    private MutableLiveData<Boolean> isLoading;

    public PhotoViewModel() {
        this.photos = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
        this.isLoading.setValue(false);
    }

    public LiveData<List<Photo>> getPhotos() {
        return photos;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<ErrorResponse> getError() {
        return error;
    }

    public void loadPhotos(int albumId) {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        PhotoInterface pett = retrofitInstance.create(PhotoInterface.class);

        isLoading.setValue(true);
        Call<ResponseBody> call = pett.getAll( "json", "kD9BK2GcPjswMEKCgeIvGutSfviZqTapKhm7", albumId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseBody) {
                try {
                    String responseJson = responseBody.body().string();
                    Gson gson = new Gson();
                    if (responseBody.code() == 200) {
                        Type type = new TypeToken<ApiResponse<List<Photo>>>() {
                        }.getType();
                        ApiResponse<List<Photo>> response = gson.fromJson(responseJson, type);

                        if (getPhotos().getValue() == null) {
                            photos.setValue(response.result);
                        } else {
                            List<Photo> copy = getPhotos().getValue();
                            copy.addAll(response.result);
                            photos.setValue(copy);
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
}
