package com.autonsi.devsimpletodoapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.autonsi.devsimpletodoapp.models.ToDoItem;
import com.autonsi.devsimpletodoapp.repositories.ToDoRepository;

import java.util.List;

import retrofit2.Callback;

public class ToDoViewModel extends AndroidViewModel {
    private ToDoRepository toDoRepository;
    public LiveData<List<ToDoItem>> todoListData;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        toDoRepository = new ToDoRepository();
        todoListData = toDoRepository.getToDoLiveData();
    }

    public void getToDoList() {
        toDoRepository.getToDoList();
    }

    public LiveData<List<ToDoItem>> getTodoListData() {
        return todoListData;
    }

    public void addToDoItem(ToDoItem newTask, Callback<ToDoItem> callback) {
        toDoRepository.addToDoItem(newTask, callback);
    }

    public void updateToDoItem(int id, ToDoItem updatedTask, Callback<ToDoItem> callback) {
        toDoRepository.updateToDoItem(id, updatedTask, callback);
    }

    public void deleteToDoItem(int id, Callback<Void> callback) {
        toDoRepository.deleteToDoItem(id, callback);
    }
}

