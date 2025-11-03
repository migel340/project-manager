/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.simple.jira;
import com.example.simple.jira.controller.Controller;
import com.example.simple.jira.model.Task;
import com.example.simple.jira.view.View;
import com.example.simple.jira.model.TaskStatus;
import com.example.simple.jira.model.Project;
import com.example.simple.jira.model.ProjectRepository;

/**
 *
 * @author spacedesk2
 */
public class SimpleJira {

    public static void main(String[] args) {
    Task task = new Task();
    View view = new View();
    Project project = new Project();
    ProjectRepository projectRepository = new ProjectRepository();
    
    Controller controller = new Controller(view, project, task, projectRepository);
    controller.run();
     
        
        
    }
}
