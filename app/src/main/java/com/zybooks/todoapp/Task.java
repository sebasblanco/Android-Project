package com.zybooks.todoapp;

public class Task {
    private int mId;
    private String mTitle;
    private String mDescription;
    private int isChecked;
    // 1 - minute
    // 2 - hour
    // 3 - day

    private long timeLeft;

    public Task() {}

    public Task(int id, String title, String description) {
        mId = id;
        mTitle = title;
        mDescription = description;
        isChecked = 1;
    }

    public Task(String title, String description) {
        mTitle = title;
        mDescription = description;
        isChecked = 1;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String name) {
        this.mTitle = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int checked) {
        this.isChecked = checked;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long time) { this.timeLeft = time; }
}
