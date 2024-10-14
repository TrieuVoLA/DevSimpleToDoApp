package com.autonsi.devsimpletodoapp.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.autonsi.devsimpletodoapp.apis.ToDoService;
import com.autonsi.devsimpletodoapp.models.ToDoItem;
import com.autonsi.devsimpletodoapp.util.Constants;

import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoRepository {
    private static final String TO_DO_SERVICE_BASE_URL = Constants.BASE_URL_HOST;

    private ToDoService toDoService;
    private MutableLiveData<List<ToDoItem>> todoListData ;

    public ToDoRepository() {
        todoListData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        toDoService = new retrofit2.Retrofit.Builder()
                .baseUrl(TO_DO_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ToDoService.class);
    }

    public void getToDoList() {
        toDoService.getToDoList().enqueue(new Callback<List<ToDoItem>>() {
            @Override
            public void onResponse(Call<List<ToDoItem>> call, Response<List<ToDoItem>> response) {
                if (response.isSuccessful()) {
                    todoListData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ToDoItem>> call, Throwable t) {
                Log.e("ToDoRepository", "Error fetching data", t);
            }
        });
    }

    public LiveData<List<ToDoItem>> getToDoLiveData() {
        return todoListData;
    }

    public void addToDoItem(ToDoItem newTask, Callback<ToDoItem> callback) {
        toDoService.addToDoItem(newTask).enqueue(new Callback<ToDoItem>() {
            @Override
            public void onResponse(Call<ToDoItem> call, Response<ToDoItem> response) {
                if (response.isSuccessful()) {
                   response.body();
                   getToDoList();
                }
            }

            @Override
            public void onFailure(Call<ToDoItem> call, Throwable t) {
                Log.e("ToDoRepository", "Error fetching data", t);
            }
        });
    }

    public void updateToDoItem(int id, ToDoItem updatedTask, Callback<ToDoItem> callback) {
        toDoService.updateToDoItem(id, updatedTask).enqueue(new Callback<ToDoItem>() {
            @Override
            public void onResponse(Call<ToDoItem> call, Response<ToDoItem> response) {
                if (response.isSuccessful()) {
                    response.body();
                    getToDoList();
                }
            }

            @Override
            public void onFailure(Call<ToDoItem> call, Throwable t) {
                Log.e("ToDoRepository", "Error fetching data", t);
            }
        });
    }

    public void deleteToDoItem(int id, Callback<Void> callback) {
        toDoService.deleteToDoItem(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    response.body();
                    getToDoList();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ToDoRepository", "Error fetching data", t);
            }
        });
    }
}

