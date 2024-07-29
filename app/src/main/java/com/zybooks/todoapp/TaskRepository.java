package com.zybooks.todoapp;

import android.content.Context;
import android.content.res.Resources;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static TaskRepository instance;
    private List<Task> mTasks;

    public static TaskRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepository(context);
        }
        return instance;
    }

    private TaskRepository(Context context) {
        mTasks = new ArrayList<>();
        Resources res = context.getResources();
        String[] tasks = res.getStringArray(R.array.task_titles);
        String[] descriptions = res.getStringArray(R.array.task_descriptions);
        for (int i = 0; i < tasks.length; i++) {
            mTasks.add(new Task(i + 1, tasks[i], descriptions[i]));
        }
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public Task getTask(int taskId) {
        for (Task task : mTasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public void addTask(Task t) {
        if (t != null)
            mTasks.add(t);
    }
}