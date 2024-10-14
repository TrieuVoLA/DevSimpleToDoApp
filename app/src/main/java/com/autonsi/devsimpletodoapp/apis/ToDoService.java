package com.autonsi.devsimpletodoapp.apis;

import com.autonsi.devsimpletodoapp.models.ToDoItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ToDoService {
    @GET("todos")
    Call<List<ToDoItem>> getToDoList();

    @POST("todos")
    Call<ToDoItem> addToDoItem(@Body ToDoItem newTask);

    @PUT("todos/{id}")
    Call<ToDoItem> updateToDoItem(@Path("id") int id, @Body ToDoItem updatedTask);

    @DELETE("todos/{id}")
    Call<Void> deleteToDoItem(@Path("id") int id);

}
