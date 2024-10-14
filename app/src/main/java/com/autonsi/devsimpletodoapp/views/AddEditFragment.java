package com.autonsi.devsimpletodoapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.autonsi.devsimpletodoapp.R;
import com.autonsi.devsimpletodoapp.adapters.ToDoAdapter;
import com.autonsi.devsimpletodoapp.databinding.FragmentAddEditBinding;
import com.autonsi.devsimpletodoapp.models.ToDoItem;
import com.autonsi.devsimpletodoapp.viewmodels.ToDoViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddEditFragment extends Fragment {
    private FragmentAddEditBinding binding;
    private ToDoViewModel viewModel;
    private ToDoItem task;
    private String state;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);  // Báo cho Fragment biết rằng có menu option

        viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        viewModel.init();

        // Get data form Arguments;
        state = getArguments().getString("STATE");
        task = getArguments().getParcelable("task");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        binding = FragmentAddEditBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Bật nút "Up" (Back)
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle(task != null ? "Edit Task" : "New Task");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Lắng nghe sự kiện khi nhấn vào nút "Back"
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Xử lý khi người dùng nhấn nút back
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        if (task != null) {
            binding.taskNameEditText.setText(task.getTaskName());
            binding.saveButton.setText("Update");
        }

        binding.saveButton.setOnClickListener(v -> {
            String taskName = binding.taskNameEditText.getText().toString();
            if (task != null) { // Edit
                task.setTaskName(taskName);
                viewModel.updateToDoItem(task.getId(), task, null);
            } else { // Add
                ToDoItem newTask = new ToDoItem(1, 0, taskName, 0);
                viewModel.addToDoItem(newTask, null);
            }
            // đóng fragment
            Navigation.findNavController(getView()).navigateUp();
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Xử lý khi người dùng nhấn nút back
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(getView()).popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

