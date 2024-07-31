package com.zybooks.todoapp;

public class Task {
    private int mId;
    private String mTitle;
    private String mDescription;
    private boolean isChecked;

    private long timeLeft;

    public Task() {}

    public Task(int id, String title, String description, boolean checked) {
        mId = id;
        mTitle = title;
        mDescription = description;
        isChecked = checked;
    }

    public Task(String title, String description) {
        mTitle = title;
        mDescription = description;
        isChecked = false;
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

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
    }

}
