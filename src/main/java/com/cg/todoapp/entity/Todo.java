package com.cg.todoapp.entity;

import java.time.LocalDateTime;


public class Todo {
    private long id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime dueDateTime;
    private boolean completed;
    private LocalDateTime createdAt;

    public Todo(long id, String title, String description, Priority priority, LocalDateTime dueDateTime) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setPriority(priority);
        setDueDateTime(dueDateTime);
        setCompleted(false);
        setCreatedAt(LocalDateTime.now());
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public LocalDateTime getDueDateTime() { return dueDateTime; }
    public void setDueDateTime(LocalDateTime dueDateTime) { this.dueDateTime = dueDateTime; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
