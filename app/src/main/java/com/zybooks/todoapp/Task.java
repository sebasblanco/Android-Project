package com.zybooks.todoapp;

public class Task {
    private int mId;
    private String mTitle;
    private String mDescription;

    public Task() {}

    public Task(int id, String title, String description) {
        mId = id;
        mTitle = title;
        mDescription = description;
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
}
