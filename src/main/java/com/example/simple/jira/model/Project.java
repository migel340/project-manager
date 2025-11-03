/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.simple.jira.model;
import com.example.simple.jira.model.Task;
import java.util.List;
import java.util.ArrayList;
// TODO auto project id incrementatnion based on project list,
/**
 *
 * @author spacedesk2
 */
public class Project {
    private int projectId;
    private String projectName;
    private List<Task> tasks = new ArrayList();
    
    public Project() {
    }
    
    /**
     * 
     * @param projectName 
     */
    public Project(String projectName) {
        this.projectName = projectName;
    }
        
    /**
     * 
     * @param projectId
     * @param projectName 
     */
    public Project(int projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }
    
    // Gettars && Setters
    
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public void updateProjectTasks(Task task) {
        this.tasks.add(task);
                
    }
    
    // List setters && getters
    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
