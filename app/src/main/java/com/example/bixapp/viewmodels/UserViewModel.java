package com.example.bixapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.bixapp.model.ErrorResponse;
import com.example.bixapp.model.User;
import com.example.bixapp.model.ApiResponse;
import com.example.bixapp.network.RetrofitClientInstance;
import com.example.bixapp.network.UserInterface;
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

public class UserViewModel extends ViewModel {

    private MutableLiveData<List<User>> users;
    private MutableLiveData<ErrorResponse> error;
    private MutableLiveData<Integer> page;
    private MutableLiveData<Integer> totalPages;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> wasReset;
    private MutableLiveData<String> query;


    public UserViewModel() {
        this.users = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
        this.query = new MutableLiveData<>();
        this.page = new MutableLiveData<>();
        this.totalPages = new MutableLiveData<>();
        this.wasReset = new MutableLiveData<>();

        this.isLoading.setValue(true);
        this.page.setValue(1);
        this.query.setValue(null);
        this.wasReset.setValue(false);
        loadUsers();
    }

    public void setQuery(String query) {
        this.query.setValue(query);
    }

    public MutableLiveData<String> getQuery() {
        return query;
    }

    public MutableLiveData<Integer> getPage() {
        return page;
    }

    public LiveData<List<User>> getUsers() {
        return users;
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

    public boolean isQueryEmpty() {
        return query.getValue() == null || query.getValue().isEmpty();
    }

    public void loadUsers() {

        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        UserInterface pett = retrofitInstance.create(UserInterface.class);

        isLoading.setValue(true);
        Call<ResponseBody> call = pett.getAll("json", getPage().getValue(), "kD9BK2GcPjswMEKCgeIvGutSfviZqTapKhm7", this.query.getValue());
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseBody) {
                try {
                    String responseJson = responseBody.body().string();
                    Gson gson = new Gson();
                    if (responseBody.code() == 200) {
                        Type type = new TypeToken<ApiResponse<List<User>>>() {
                        }.getType();
                        ApiResponse<List<User>> response = gson.fromJson(responseJson, type);

                        totalPages.setValue(response._meta.pageCount);

                        if (page.getValue() == 1) {
                            users.setValue(new ArrayList<User>());
                            users.setValue(response.result);
                        } else {
                            List<User> copy = getUsers().getValue();
                            copy.addAll(response.result);
                            users.setValue(copy);
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

    public Integer loadNextPage() {
        Integer value = page.getValue() + 1;
        page.setValue(value);

        if(totalPages.getValue().compareTo(page.getValue()) > -1 ){
            loadUsers();
            return value -1;
        }
        return page.getValue();
    }

    public void resetPage() {
        wasReset.setValue(true);
        page.setValue(1);
    }
}
