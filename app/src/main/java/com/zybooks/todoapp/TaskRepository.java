package com.zybooks.todoapp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskRepository {

    private static TaskRepository instance;
    private List<Task> mTasks;

    private String mRefreshMode;

    private final ScheduledExecutorService executorService;
    private final Handler handler;

    public void startTimer() {
        long delay = 60 - Calendar.getInstance().get(Calendar.SECOND);
        executorService.scheduleWithFixedDelay(() -> {
            if (this.mRefreshMode.equals("Minute"))
                resetCheckedTasks();
            else if (this.mRefreshMode.equals("Hour") && Calendar.getInstance().get(Calendar.MINUTE) == 0)
                resetCheckedTasks();
            else if (this.mRefreshMode.equals("Day") && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 0 && Calendar.getInstance().get(Calendar.MINUTE) == 0)
                resetCheckedTasks();
        }, delay, 60, TimeUnit.SECONDS);
    }

    public void stopTimer() {
        executorService.shutdownNow();
    }

    private void resetCheckedTasks() {
        // Access and modify mTasks here
        if (mTasks != null) {
            for (Task task : mTasks) {
                task.setIsChecked(false);
            }
            // Update UI if necessary
//            handler.post(() -> {
//                // Notify UI about the changes
//            });
        }
    }

    public static TaskRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepository(context);
        }
        return instance;
    }

    private TaskRepository(Context context) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        handler = new Handler(Looper.getMainLooper());
        mTasks = new ArrayList<>();
        Resources res = context.getResources();
        String[] tasks = res.getStringArray(R.array.task_titles);
        String[] descriptions = res.getStringArray(R.array.task_descriptions);
        String[] refreshModes = res.getStringArray(R.array.frequency_selection);
        for (int i = 0; i < tasks.length; i++) {
            mTasks.add(new Task(i + 1, tasks[i], descriptions[i], false));
        }
        mRefreshMode = refreshModes[0];
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    public String getRefreshMode() {
        return mRefreshMode;
    }
    public void setRefreshMode(String refreshMode) {
        this.mRefreshMode = refreshMode;
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
