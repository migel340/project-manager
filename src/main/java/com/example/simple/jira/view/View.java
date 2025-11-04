package com.example.simple.jira.view;

import com.example.simple.jira.model.domain.Project;
import com.example.simple.jira.model.domain.Task;
import com.example.simple.jira.model.domain.TaskStatus;

import java.util.List;

/**
 * Console based view responsible for presenting information to the user
 *
 * The view renders projects, tasks, and menus while delegating input
 * collection to the controller
 *
 * @author spacedesk2
 * @version 1.0
 */
public class View {

    /**
     * Prints a message to the standard output
     *
     * @param message text that should be displayed
     */
    public void displayMessage(final String message) {
        System.out.println(message);
    }

    /**
     * Prints an error message to the standard error stream
     *
     * @param message text describing the failure
     */
    public void displayError(final String message) {
        System.err.println(message);
    }

    /**
     * Shows a prompt to the user without reading input
     *
     * @param prompt text explaining what the user should enter
     */
    public void displayPrompt(final String prompt) {
        System.out.println(prompt);
    }

    /**
     * Displays the initial information for first time users
     */
    public void displayInitMenu() {
        displayMessage("No projects available. Please create a new project");
    }

    /**
     * Shows the list of available projects
     *
     * @param projects projects currently stored in the application
     */
    public void displayProjectList(final List<Project> projects) {
        displayMessage("==== Projects ====");
        for (Project project : projects) {
            displayMessage("ID: " + project.getProjectId() + " | Name: " + project.getProjectName());
        }
    displayMessage("\n#<id> - Open project (e.g., #1)");
    displayMessage("1 - Create a new project");
    displayMessage("2 - Rename a project");
    displayMessage("3 - Delete a project");
    displayMessage("0 - Exit");
    }

    /**
     * Displays tasks grouped by status
     *
     * @param tasks tasks associated with the selected project
     */
    public void displayTasks(final List<Task> tasks) {
        displayMessage("\n==== TODO ====");
        tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.TODO)
                .forEach(task -> displayMessage(formatTask(task)));

        displayMessage("\n==== IN_PROGRESS ====");
        tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .forEach(task -> displayMessage(formatTask(task)));

        displayMessage("\n==== DONE ====");
        tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .forEach(task -> displayMessage(formatTask(task)));
    }

    /**
     * Prints the menu for the currently selected project
     *
     * @param project project currently selected by the user
     */
    public void displayProjectMenu(final Project project) {
        displayMessage("\n\n==== Project: " + project.getProjectName() + " ====");
        displayMessage("1. Show tasks");
        displayMessage("2. Add task");
        displayMessage("3. Change task status");
        displayMessage("4. Change task priority");
        displayMessage("5. Rename task");
        displayMessage("6. Update task description");
        displayMessage("7. Delete task");
        displayMessage("0. Return to project list");
    }

    /**
     * Displays task status selection instructions.
     */
    public void displayStatusSelection() {
        displayMessage("\nSelect status: 1=TODO, 2=IN_PROGRESS, 3=DONE");
    }

    /**
     * Displays task priority selection instructions.
     */
    public void displayPrioritySelection() {
        displayMessage("\nSelect priority: 1=NOT_SET, 2=LOW, 3=MEDIUM, 4=HIGH");
    }

    /**
     * Formats task information for display
     *
     * @param task task to format
     * @return human readable representation of the task
     */
     private String formatTask(final Task task) {
        return "Task " + task.getTaskId() + " | " + task.getTitle() + " | " + task.getDescription() + " | Priority: " + task.getPriority();
    }
}
