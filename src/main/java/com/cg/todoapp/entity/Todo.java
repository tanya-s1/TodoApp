package com.cg.todoapp.entity;

import java.time.LocalDateTime;

/**
 * Represents a Todo task with properties like id, title, description, priority,
 * due date/time, completion status, and creation timestamp.
 */
public class Todo {
    // Unique identifier for the Todo
    private long id;
    
    // Title or short summary of the Todo task
    private String title;
    
    // Detailed description of the task
    private String description;
    
    // Priority level of the task (e.g., LOW, MEDIUM, HIGH)
    private Priority priority;
    
    // Date and time by which the task should be completed
    private LocalDateTime dueDateTime;
    
    // Status indicating whether the task is completed or not
    private boolean completed;
    
    // Timestamp when the Todo was created
    private LocalDateTime createdAt;

    /**
     * Constructs a new Todo object with given parameters.
     * Automatically sets the creation timestamp to the current time and
     * marks the task as not completed.
     *
     * @param id           Unique identifier for the Todo
     * @param title        Title or name of the Todo
     * @param description  Detailed description of the Todo
     * @param priority     Priority level of the Todo
     * @param dueDateTime  Deadline for the Todo task
     */
    public Todo(long id, String title, String description, Priority priority, LocalDateTime dueDateTime) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setPriority(priority);
        setDueDateTime(dueDateTime);
        setCompleted(false); // New tasks are incomplete by default
        setCreatedAt(LocalDateTime.now()); // Set creation time to now
    }

    /**
     * Returns the unique identifier of the Todo.
     *
     * @return Todo id
     */
    public long getId() { return id; }

    /**
     * Sets the unique identifier of the Todo.
     *
     * @param id Todo id to set
     */
    public void setId(long id) { this.id = id; }

    /**
     * Returns the title of the Todo.
     *
     * @return Todo title
     */
    public String getTitle() { return title; }

    /**
     * Sets the title of the Todo.
     *
     * @param title Title to set
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Returns the description of the Todo.
     *
     * @return Todo description
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the Todo.
     *
     * @param description Description to set
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Returns the priority level of the Todo.
     *
     * @return Priority of the Todo
     */
    public Priority getPriority() { return priority; }

    /**
     * Sets the priority level of the Todo.
     *
     * @param priority Priority to set
     */
    public void setPriority(Priority priority) { this.priority = priority; }

    /**
     * Returns the due date and time of the Todo.
     *
     * @return Due date and time
     */
    public LocalDateTime getDueDateTime() { return dueDateTime; }

    /**
     * Sets the due date and time of the Todo.
     *
     * @param dueDateTime Due date and time to set
     */
    public void setDueDateTime(LocalDateTime dueDateTime) { this.dueDateTime = dueDateTime; }

    /**
     * Returns whether the Todo is completed.
     *
     * @return true if completed, false otherwise
     */
    public boolean isCompleted() { return completed; }

    /**
     * Sets the completion status of the Todo.
     *
     * @param completed Completion status to set
     */
    public void setCompleted(boolean completed) { this.completed = completed; }

    /**
     * Returns the timestamp when the Todo was created.
     *
     * @return Creation timestamp
     */
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Sets the timestamp when the Todo was created.
     *
     * @param createdAt Creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
