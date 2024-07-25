package com.zybooks.todoapp;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ToDoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_to_do, container, false);

        View.OnClickListener onClickListener = itemView -> {

            int selectedTaskId = (int) itemView.getTag();
            Bundle args = new Bundle();
            args.putInt(TaskDetailsFragment.ARG_TASK_ID, selectedTaskId);

            // Replace list with details
            Navigation.findNavController(itemView).navigate(R.id.show_task_details, args);
        };

        ImageButton addTaskBtn = rootView.findViewById(R.id.add_task_btn);

        addTaskBtn.setOnClickListener(
                (v) -> Navigation.findNavController(rootView).navigate(R.id.show_add_task_page)
        );

        RecyclerView recyclerView = rootView.findViewById(R.id.task_list);
        List<Task> tasks = TaskRepository.getInstance(requireContext()).getTasks();
        recyclerView.setAdapter(new TaskAdapter(tasks, onClickListener));

        return rootView;
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private final List<Task> mTasks;
        private final View.OnClickListener mOnClickListener;

        public TaskAdapter(List<Task> tasks, View.OnClickListener onClickListener) {
            mTasks = tasks;
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bind(task);
            holder.itemView.setTag(task.getId());
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    private static class TaskHolder extends RecyclerView.ViewHolder {

        private final TextView mTaskTextView;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.task_item, parent, false));
            mTaskTextView = itemView.findViewById(R.id.task_text);
        }

        public void bind(Task task) {
            mTaskTextView.setText(task.getTitle());
        }
    }
}