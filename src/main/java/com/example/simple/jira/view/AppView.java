package com.example.simple.jira.view;

import com.example.simple.jira.model.domain.Project;
import com.example.simple.jira.model.domain.Task;
import java.util.List;

/**
 * UI contract used by Controller
 */
public interface AppView {
    /**
     * Display an informational message to the user
     *
     * @param message text to show
     */
    void displayMessage(String message);

    /**
     * Display an error message to the user
     *
     * @param message error text to show
     */
    void displayError(String message);

    /**
     * Display a single-line prompt before reading user input
     *
     * @param prompt prompt text to show
     */
    void displayPrompt(String prompt);

    /**
     * Display the initial menu shown when no projects exist yet
     */
    void displayInitMenu();

    /**
     * Display a list of available projects
     *
     * @param projects list of projects to display
     */
    void displayProjectList(List<Project> projects);

    /**
     * Display tasks associated with the currently selected project
     *
     * @param tasks list of tasks to display
     */
    void displayTasks(List<Task> tasks);

    /**
     * Display the project-specific menu for the provided project
     *
     * @param project currently selected project
     */
    void displayProjectMenu(Project project);

    /**
     * Display the available task status options (TODO, IN_PROGRESS, DONE)
     */
    void displayStatusSelection();

    /**
     * Display the available task priority options (NOT_SET, LOW, MEDIUM, HIGH)
     */
    void displayPrioritySelection();
}
