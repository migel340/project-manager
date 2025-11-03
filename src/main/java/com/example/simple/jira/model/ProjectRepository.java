/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.simple.jira.model;
import com.example.simple.jira.model.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
        
/**
 *
 * @author spacedesk2
 */
public class ProjectRepository {
    
    private List<Project> projects = new ArrayList<>();
    private AtomicInteger nextId = new AtomicInteger(1);
    
    public List<Project> findAll() {
        return projects;
    }
    
    public Project findById(int projectId) {
            for (Project project : projects) {
                if (project.getProjectId() == projectId) {
                    return project;
                }
            }
            return null;
        }
    
    public void save(Project project) {
            int newId = nextId.getAndIncrement();
            project.setProjectId(newId);
            projects.add(project);
    }
}
