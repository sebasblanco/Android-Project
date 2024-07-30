package com.zybooks.todoapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskDetailsFragment extends Fragment {

    public static final String ARG_TASK_ID = "task_id";
    private Task mTask;
    private int taskId;

    public TaskDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            taskId = args.getInt(ARG_TASK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);

        if (mTask != null) {
            TextView titleTextView = rootView.findViewById(R.id.task_title);
            titleTextView.setText(mTask.getTitle());

            TextView descriptionTextView = rootView.findViewById(R.id.task_description);
            descriptionTextView.setText(mTask.getDescription());
        }

        Thread thread = new Thread(() -> {
            mTask = TaskRepository.getInstance(requireContext()).getTask(taskId);
            requireActivity().runOnUiThread(() -> {
                TextView titleTextView = rootView.findViewById(R.id.task_title);
                TextView descriptionTextView = rootView.findViewById(R.id.task_description);
                titleTextView.setText(mTask.getTitle());
                descriptionTextView.setText(mTask.getDescription());
            });
        });
        thread.start();

        return rootView;
    }
}
