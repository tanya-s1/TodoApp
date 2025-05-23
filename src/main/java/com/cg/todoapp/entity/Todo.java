package com.cg.todoapp.entity;

import java.time.LocalDate;

public class Todo {
    private long id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private boolean completed;
    private LocalDate createdAt;

    public Todo(long id, String title, String description, Priority priority, LocalDate dueDate) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setPriority(priority);
        setDueDate(dueDate);
        setCompleted(false);
        setCreatedAt(LocalDate.now());
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
}
