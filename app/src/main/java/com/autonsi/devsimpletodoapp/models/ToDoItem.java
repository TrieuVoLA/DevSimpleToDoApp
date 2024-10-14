package com.autonsi.devsimpletodoapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ToDoItem implements Parcelable {
    @SerializedName("userId")
    private int userId;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String taskName;
    @SerializedName("completed")
    private int completed;

    // Constructors
    public ToDoItem(int userId, int id, String taskName, int completed) {
        this.userId = userId;
        this.id = id;
        this.taskName = taskName;
        this.completed = completed;
    }

    protected ToDoItem(Parcel in) {
        userId = in.readInt();
        id = in.readInt();
        taskName = in.readString();
        completed = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeString(taskName);
        dest.writeInt(completed);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToDoItem> CREATOR = new Creator<ToDoItem>() {
        @Override
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in);
        }

        @Override
        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int isCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
