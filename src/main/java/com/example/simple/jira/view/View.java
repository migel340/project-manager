/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.simple.jira.view;
import java.util.Scanner;
import com.example.simple.jira.model.Task;
import com.example.simple.jira.model.TaskStatus;
import com.example.simple.jira.model.Task;
import com.example.simple.jira.model.Project;
import java.util.List;

/**
 *
 * @author spacedesk2
 */
public class View {
    private Scanner scanner;
    
    public View() {
        this.scanner = new Scanner(System.in);
    }
    
    public void displayMessage(String string) {
        System.out.println(string);
    }
    
    public void displayInitMenu() {
        displayMessage("Hello, please create new project! ");
    }
    
    public void displayProjectList(List<Project> projects) {
        for (var project : projects) {
            displayMessage("ID: " + project.getProjectId() + " Name: " + project.getProjectName());
        }
    }
    


    public String getProjectName() {
        displayMessage("Enter project name: ");
        String projectName = scanner.next();
        return projectName;
    }

 
    public void displayTasks(List<Task> tasks) {
        
        displayMessage("TODO");
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.TODO) {
                System.out.println(t.getTaskId() + " " +   t.getTitle());
            }
        }

        displayMessage("\nIN_PROGRESS");
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.IN_PROGRESS) {
                System.out.println(t.getTaskId() + " " +   t.getTitle());
            }
        }

        displayMessage("\nDONE");
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.DONE) {
                System.out.println(t.getTaskId() + " " +   t.getTitle());
            }
        }

    }
    public void displayProjectMenu(Project project) {
        displayMessage("Project: " + project.getProjectName());
        displayMessage("1. Show tasks");
        displayMessage("2. Add tasks");
        displayMessage("3. Change task status");
        displayMessage("0. Return)");
    }

    public int getIntInput() {
            return scanner.nextInt();
        }
    
    public void closeScanner() {
        this.scanner.close();
    }
}
