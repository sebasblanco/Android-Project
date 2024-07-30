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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RefreshFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RefreshFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int selectedTime;

    public RefreshFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RefreshFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RefreshFragment newInstance(String param1, String param2) {
        RefreshFragment fragment = new RefreshFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_refresh, container, false);
        Spinner spinner = rootView.findViewById(R.id.frequency_selector);
        List<Task> tasks = TaskRepository.getInstance(requireContext()).getTasks();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.frequency_selection, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = (Integer)parent.getItemAtPosition(position);
                if (selectedTime == 1) {
                    for ( Task task : tasks) {
                        task.setIsChecked(1);
                        task.setTimeLeft(60);
                    }
                } else if (selectedTime == 2) {
                    for ( Task task : tasks) {
                        task.setIsChecked(2);
                        task.setTimeLeft(3600);
                    }
                } else if (selectedTime == 3) {
                    for ( Task task : tasks) {
                        task.setIsChecked(3);
                        task.setTimeLeft(86400);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTime = 1;
            }
        });

        Button okButton = rootView.findViewById(R.id.ok_button);
        okButton.setOnClickListener(
            (v) -> {
                NavController navController = findNavController(rootView);
                navController.popBackStack();
            }
        );

        Thread thread = new Thread(() -> {
            getActivity().runOnUiThread(() -> {
                for ( Task task : tasks) {
                    if (task.getTimeLeft() == 0) {
                        int positionToUncheck = tasks.indexOf(task);
                        spinner.setSelection(positionToUncheck);
                    }
                }
            });
        });
        thread.start();

        return rootView;
    }

}