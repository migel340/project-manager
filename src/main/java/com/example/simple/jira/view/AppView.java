package com.example.simple.jira.view;

import com.example.simple.jira.model.domain.Project;
import com.example.simple.jira.model.domain.Task;
import java.util.List;

/**
 * UI contract used by Controller
 */
public interface AppView {
    void displayMessage(String message);
    void displayError(String message);
    void displayPrompt(String prompt);
    void displayInitMenu();
    void displayProjectList(List<Project> projects);
    void displayTasks(List<Task> tasks);
    void displayProjectMenu(Project project);
    void displayStatusSelection();
    void displayPrioritySelection();
}
