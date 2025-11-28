package com.example.todolist;

public class TaskModel {
    private String id;
    private String title;
    private String description;
    private String date;

    public TaskModel(String id, String title, String description, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
}
