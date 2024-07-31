package com.zybooks.todoapp.repo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import com.zybooks.todoapp.R;
import com.zybooks.todoapp.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class TaskRepository {

    private static TaskRepository instance;
    private final TaskDao mTaskDao;
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService mDatabaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TaskRepository(context);
        }
        return instance;
    }

    private TaskRepository(Context context) {
        RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }
        };
        TaskDatabase database = Room.databaseBuilder(context, TaskDatabase.class, "task.db")
                .addCallback(databaseCallback)
                .build();
        mTaskDao = database.taskDAO();
    }

    public LiveData<Task> getTask(long taskId){
        return mTaskDao.getTask(taskId);
    }

    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getTasks();
    }

    public void addTask(Task t) {
        mDatabaseExecutor.execute(() -> {
            long taskId = mTaskDao.addTask(t);
            t.setId(taskId);
        });
    }

    public void updateTask(Task task) {
        mDatabaseExecutor.execute(() -> {
            mTaskDao.updateTask(task);
        });
    }

    public void updateTaskList(List<Task> tasks) {
        mDatabaseExecutor.execute(() -> {
            Task[] taskArray = tasks.toArray(new Task[0]);
            mTaskDao.updateTaskList(taskArray);
        });
    }
}