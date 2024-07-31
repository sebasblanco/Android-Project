package com.zybooks.todoapp;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class RefreshFragment extends Fragment {

    TaskRepository mTaskRepository;

    String mTempRefreshMode;

    public RefreshFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_refresh, container, false);
        Spinner spinner = rootView.findViewById(R.id.frequency_selector);
        mTaskRepository = TaskRepository.getInstance(requireContext());
        List<Task> tasks = mTaskRepository.getTasks();
        mTempRefreshMode = mTaskRepository.getRefreshMode();

        String[] refreshModes = getResources().getStringArray(R.array.frequency_selection);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, refreshModes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int currentModeIndex = Arrays.asList(refreshModes).indexOf(mTaskRepository.getRefreshMode());
        if (currentModeIndex != -1) {
            spinner.setSelection(currentModeIndex);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTempRefreshMode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button okButton = rootView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(
            (v) -> {
                mTaskRepository.setRefreshMode(mTempRefreshMode);
                NavController navController = findNavController(rootView);
                navController.popBackStack();
            }
        );

//        Thread thread = new Thread(() -> {
//            getActivity().runOnUiThread(() -> {
//                for ( Task task : tasks) {
//                    if (task.getTimeLeft() == 0) {
//                        int positionToUncheck = tasks.indexOf(task);
//                        spinner.setSelection(positionToUncheck);
//                    }
//                }
//            });
//        });
//        thread.start();

        return rootView;
    }

}