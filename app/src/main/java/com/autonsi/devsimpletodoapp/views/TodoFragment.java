package com.autonsi.devsimpletodoapp.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.autonsi.devsimpletodoapp.R;
import com.autonsi.devsimpletodoapp.adapters.ToDoAdapter;
import com.autonsi.devsimpletodoapp.databinding.FragmentTodoBinding;
import com.autonsi.devsimpletodoapp.models.ToDoItem;
import com.autonsi.devsimpletodoapp.viewmodels.ToDoViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    private FragmentTodoBinding binding;
    private ToDoViewModel viewModel;
    private ToDoAdapter adapter;

    List<ToDoItem> toDoItems = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ToDoAdapter(toDoItems, new ToDoAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(ToDoItem item) {
                gotoAddFragment("EDIT", item);
            }

            @Override
            public void onDeleteClick(ToDoItem item) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Warning!");
                alertDialog.setIcon(R.drawable.icons8_delete);
                alertDialog.setMessage("Do you want delete?"); //"The data you entered does not exist on the server !!!");

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteToDoItem(item.getId(), null);
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
        });

        viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
       // new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.init();

        viewModel.getTodoListData().observe(this, new Observer<List<ToDoItem>>() {
            @Override
            public void onChanged(List<ToDoItem> toDoItems) {
                if (toDoItems != null) {
                    adapter.setToDoItems(toDoItems);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("To Do Lists");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentTodoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        // Load List todo
        viewModel.getToDoList();

        // Listener Click ADD
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddFragment("ADD", null);
            }
        });

        // SetOnRefreshListener on SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
                viewModel.getToDoList();
            }
        });

        return root;
    }

    private void gotoAddFragment(String stateAddEdit, ToDoItem toDoItem) {
        Bundle bundle = new Bundle();
        bundle.putString("STATE", stateAddEdit);
        bundle.putParcelable("task", toDoItem);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_todo_to_addedit, bundle);
    }

}

