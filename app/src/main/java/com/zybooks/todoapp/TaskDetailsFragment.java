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

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int taskId = 1;

        Bundle args = getArguments();
        if (args != null) {
            taskId = args.getInt(ARG_TASK_ID);
        }

        // Get the selected Task
        mTask = TaskRepository.getInstance(requireContext()).getTask(taskId);
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

        return rootView;
    }
}
