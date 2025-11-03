/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.simple.jira.controller;

import com.example.simple.jira.model.Project;
import com.example.simple.jira.model.ProjectRepository;
import com.example.simple.jira.model.Task;
import com.example.simple.jira.model.TaskStatus;
import java.util.List;
import java.util.ArrayList;
import com.example.simple.jira.view.View;


/**
 *
 * @author spacedesk2
 */
public class Controller {
    private View view;
    private Project project;
    private Task task;
    private ProjectRepository projectRepository;
          
    public Controller(View view, Project project, Task task, ProjectRepository projectRepository) {
        this.view = view;
        this.project = project;
        this.task = task;
        this.projectRepository = projectRepository;
    }
    public void run() {
        while (true) {
            Project currentProject = selectProjectLoop(); 
            if (currentProject == null) {
                createNewProject();
            } else {
                manageProjectLoop(currentProject);
            }
        }

    }
    
    private Project selectProjectLoop() {
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()) {
            return null; 
        }
        view.displayProjectList(projects);
        int choice = view.getIntInput();
        if (choice == 0) {
            return null;
        }
        Project selected = projectRepository.findById(choice);
        
        if (selected == null) {
            return selectProjectLoop();
        }

        return selected;
    }
    
    
    private void createNewProject() {
        view.displayInitMenu();
        String projectName = view.getProjectName();
        Project newProject = new Project(projectName);
        projectRepository.save(newProject); 
        view.displayMessage("Project '" + newProject.getProjectName() + "' was created with ID " + newProject.getProjectId());
    }
    
    private void manageProjectLoop(Project project) {
        boolean running = true;
        while (running) {
            view.displayProjectMenu(project);
            int choice = view.getIntInput();

            switch (choice) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 0:
                    
                    running = false; 
                    break;
                default:
            }
        }
    }
}
