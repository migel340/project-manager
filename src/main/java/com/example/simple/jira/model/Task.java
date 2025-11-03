/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.simple.jira.model;
import com.example.simple.jira.model.TaskStatus;

/**
 *
 * @author spacedesk2
 */
public class Task {
    
    
    private int taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private int projectId;
    
    public Task() {
    
    }

    /**
     * 
     * @param taskId
     * @param title
     * @param description
     * @param status
     * @param projectId 
     */
    public Task(int taskId, String title, String description, TaskStatus status, int projectId) {
            this.taskId = taskId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.projectId = projectId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
    

    

