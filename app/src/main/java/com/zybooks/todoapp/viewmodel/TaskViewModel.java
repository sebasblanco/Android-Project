package com.zybooks.todoapp.viewmodel;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.zybooks.todoapp.R;
import com.zybooks.todoapp.model.Task;
import com.zybooks.todoapp.repo.TaskRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository mTaskRepo;
    private String mRefreshMode;
    private final ScheduledExecutorService executorService;

    public TaskViewModel( Application application) {
        super(application);
        mTaskRepo = TaskRepository.getInstance(application.getApplicationContext());
        executorService = Executors.newSingleThreadScheduledExecutor();
        Resources res = application.getResources();
        String[] refreshModes = res.getStringArray(R.array.frequency_selection);
        mRefreshMode = refreshModes[0];
    }

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
        Log.d("Message", "Outside reset tasks");
        List<Task> tasks = getTasks().getValue();
        if (tasks != null) {
            for (Task task : tasks) {
                task.setIsChecked(false);
                updateTask(task);
            }
            updateTaskList(tasks);
            Log.d("Message", "Entered reset tasks");
        }
    }

    public LiveData<Task> getTask(long taskId) {
        return mTaskRepo.getTask(taskId);
    }
    public LiveData<List<Task>> getTasks(){
        return mTaskRepo.getTasks();
    }

    public String getRefreshMode() {
        return mRefreshMode;
    }
    public void setRefreshMode(String refreshMode) {
        this.mRefreshMode = refreshMode;
    }

    public void addTask(Task t) {
        mTaskRepo.addTask(t);
    }

    public void updateTask(Task task) {
        mTaskRepo.updateTask(task);
    }

    public void updateTaskList(List<Task> tasks) {
        mTaskRepo.updateTaskList(tasks);
    }
}