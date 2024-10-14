package com.autonsi.devsimpletodoapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.autonsi.devsimpletodoapp.R;
import com.autonsi.devsimpletodoapp.models.ToDoItem;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoItem> toDoItems = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(ToDoItem item);

        void onDeleteClick(ToDoItem item);
    }

    public ToDoAdapter(List<ToDoItem> toDoItems, OnItemClickListener listener) {
        this.toDoItems = toDoItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoItem item = toDoItems.get(position);
        holder.taskName.setText(item.getTaskName());

        holder.editButton.setOnClickListener(v -> listener.onEditClick(item));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(item));
    }

    @Override
    public int getItemCount() {
        return toDoItems.size();
    }

    public void setToDoItems(List<ToDoItem> results) {
        this.toDoItems = results;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        View editButton;
        View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            editButton = itemView.findViewById(R.id.iv_edit);
            deleteButton = itemView.findViewById(R.id.iv_delete);
        }
    }
}

