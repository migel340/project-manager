package com.example.simple.jira.controller;

import com.example.simple.jira.model.domain.Project;
import com.example.simple.jira.model.domain.Task;
import com.example.simple.jira.model.domain.TaskPriority;
import com.example.simple.jira.model.domain.TaskStatus;
import com.example.simple.jira.model.exceptions.ModelOperationException;
import com.example.simple.jira.model.repository.TaskRepository;
import com.example.simple.jira.model.repository.ProjectRepository;
import com.example.simple.jira.view.View;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Coordinates the flow between the view and the domain model
 *
 * Controller keeps the application running, handles user decisions, and
 * reacts to domain errors exposed via {@link ModelOperationException}
 *
 * @author spacedesk2
 * @version 1.0
 */
public class Controller {

    /** View responsible for user interaction. */
    private final View view;
    /** Repository providing access to projects. */
    private final ProjectRepository projectRepository;
    /** Repository providing access to tasks. */
    private final TaskRepository taskRepository;
    /** Scanner reading user input from the console. */
    private final Scanner scanner;

    /**
     * Creates a new controller instance
     *
     * @param view view used for interaction
     * @param projectRepository repository used to access projects
     * @param taskRepository repository used to access tasks
     */
    public Controller(final View view, final ProjectRepository projectRepository, final TaskRepository taskRepository) {
        this.view = view;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    /**
     * Runs the main controller loop until the user terminates the application
     */
    public void run() {
        boolean running = true;
        while (running) {
            final Project selectedProject = selectProjectLoop();
            if (selectedProject == null) {
                running = false;
            } else {
                manageProjectLoop(selectedProject);
            }
        }
    }

    /**
     * Presents available projects, allowing the user to select or create one
     *
     * @return project chosen by the user
     */
    private Project selectProjectLoop() {
        while (true) {
            final List<Project> projects = projectRepository.findAll();
            if (projects.isEmpty()) {
                view.displayInitMenu();
                return createNewProject();
            }

            view.displayProjectList(projects);
            final String input = readLine("Enter project id or menu option:");
            switch (input) {
                case "1":
                    return createNewProject();
                case "2":
                    renameProjectFromList();
                    break;
                case "3":
                    deleteProjectFromList();
                    break;
                case "0":
                    return null;
                default:
                    try {
                        final int projectId = parseProjectId(input);
                        return projectRepository.findById(projectId);
                    } catch (NumberFormatException ex) {
                        view.displayError("Please enter a valid option or project id.");
                    } catch (ModelOperationException ex) {
                        view.displayError(ex.getMessage());
                    }
            }
        }
    }

    /**
     * Creates a new project based on user input
     *
     * @return newly created project
     */
    private Project createNewProject() {
        final String projectName = readNonEmptyLine("Enter project name:");
        final Project newProject = projectRepository.create(projectName);
        view.displayMessage("Project '" + newProject.getProjectName() + "' created with ID " + newProject.getProjectId());
        return newProject;
    }

    /**
     * Manages project specific actions until the user returns to the project list
     *
     * @param project project selected by the user
     */
    private void manageProjectLoop(Project project) {
        boolean running = true;
        while (running) {
            view.displayProjectMenu(project);
            final int choice = readInt("Select option:");

            switch (choice) {
                case 1:
                    displayTasks(project);
                    break;
                case 2:
                    project = addTask(project);
                    break;
                case 3:
                    project = changeTaskStatus(project);
                    break;
                case 4:
                    project = changeTaskPriority(project);
                    break;
                case 5:
                    project = renameTask(project);
                    break;
                case 6:
                    project = changeTaskDescription(project);
                    break;
                case 7:
                    project = deleteTask(project);
                    break;
                case 0:
                    running = false;
                    view.displayMessage("Returning to project list");
                    break;
                default:
                    view.displayError("Unknown option selected");
            }
        }
    }

    /**
     * Displays tasks associated with the project
     *
     * @param project project whose tasks will be displayed
     */
    private void displayTasks(final Project project) {
        final List<Task> tasks = taskRepository.findByProject(project.getProjectId());
        if (tasks.isEmpty()) {
            view.displayMessage("No tasks available for this project.");
        } else {
            view.displayTasks(tasks);
        }
    }

    /**
     * Adds a new task to the selected project
     *
     * @param project currently selected project
     * @return updated project instance
     */
    private Project addTask(final Project project) {
        final String title = readNonEmptyLine("Enter task title:");
        final String description = readLine("Enter task description:");
        taskRepository.addTask(project.getProjectId(), title, description);
        view.displayMessage("Task created successfully.");
        return project;
    }

    /**
     * Updates the status of a task
     *
     * @param project currently selected project
     * @return updated project instance
     */
    private Project changeTaskStatus(final Project project) {
        try {
            taskRepository.requireTasks(project.getProjectId());
            final int taskId = readInt("Enter task id:");
            final TaskStatus status = readStatus();
            taskRepository.updateTaskStatus(project.getProjectId(), taskId, status);
            view.displayMessage("Task status updated.");
        } catch (ModelOperationException ex) {
            view.displayError(ex.getMessage());
        }
        return project;
    }


    private Project changeTaskPriority(final Project project) {
        try {
            taskRepository.requireTasks(project.getProjectId());
            final int taskId = readInt("Enter task id:");
            final TaskPriority priority = readPriority();
            taskRepository.updateTaskPriority(project.getProjectId(), taskId, priority);
            view.displayMessage("Task priority updated.");
        } catch (ModelOperationException ex) {
            view.displayError(ex.getMessage());
        }
        return project;
    }


    private Project renameTask(final Project project) {
        try {
            taskRepository.requireTasks(project.getProjectId());
            final int taskId = readInt("Enter task id to rename:");
            final String newTitle = readNonEmptyLine("Enter new task title:");
            taskRepository.renameTask(project.getProjectId(), taskId, newTitle);
            view.displayMessage("Task renamed.");
        } catch (ModelOperationException ex) {
            view.displayError(ex.getMessage());
        }
        return project;
    }

    private Project changeTaskDescription(final Project project) {
        try {
            taskRepository.requireTasks(project.getProjectId());
            final int taskId = readInt("Enter task id to change description:");
            final String newDescription = readLine("Enter new task description:");
            taskRepository.updateTaskDescription(project.getProjectId(), taskId, newDescription);
            view.displayMessage("Task description updated.");
        } catch (ModelOperationException ex) {
            view.displayError(ex.getMessage());
        }
        return project;
    }

    /**
     * Deletes a task from the project
     *
     * @param project currently selected project
     * @return updated project instance
     */
    private Project deleteTask(final Project project) {
        try {
            taskRepository.requireTasks(project.getProjectId());
            final int taskId = readInt("Enter task id to delete:");
            taskRepository.deleteTask(project.getProjectId(), taskId);
            view.displayMessage("Task removed.");
        } catch (ModelOperationException ex) {
            view.displayError(ex.getMessage());
        }
        return project;
    }
    /**
     * Reads a non-empty line from the input stream
     *
     * @param prompt message displayed before reading input
     * @return trimmed user input
     */
    private String readNonEmptyLine(final String prompt) {
        while (true) {
            final String line = readLine(prompt);
            if (!line.isEmpty()) {
                return line;
            }
            view.displayError("Value cannot be empty.");
        }
    }

    /**
     * Reads a line (which may be empty) from the input stream
     *
     * @param prompt message displayed before reading
     * @return trimmed user input
     */
    private String readLine(final String prompt) {
        view.displayPrompt(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Reads an integer value from the input stream
     *
     * @param prompt message displayed before reading
     * @return integer value provided by the user
     */
    private int readInt(final String prompt) {
        while (true) {
            view.displayPrompt(prompt);
            try {
                final int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                view.displayError("Please provide a valid number");
            }
        }
    }

    /**
     * Reads task status selection from the input stream.
     *
     * @return task status specified by the user
     */
    private TaskStatus readStatus() {
        view.displayStatusSelection();
        while (true) {
            final int choice = readInt("Enter status number:");
            switch (choice) {
                case 1:
                    return TaskStatus.TODO;
                case 2:
                    return TaskStatus.IN_PROGRESS;
                case 3:
                    return TaskStatus.DONE;
                default:
                    view.displayError("Unknown option. Please choose between 1 and 3.");
            }
        }
    }

    private TaskPriority readPriority() {
        view.displayPrioritySelection();
        while (true) {
            final int choice = readInt("Enter priority number:");
            switch (choice) {
                case 1:
                    return TaskPriority.NOT_SET;
                case 2:
                    return TaskPriority.LOW;
                case 3:
                    return TaskPriority.MEDIUM;
                case 4:
                    return TaskPriority.HIGH;
                default:
                    view.displayError("Unknown option. Please choose between 1 and 4.");
            }
        }
    }
    /**
     * Renames a project selected from the project list menu
     */
    private void renameProjectFromList() {
        final int projectId = readInt("Enter project id to rename:");
        try {
            final Project renamed = projectRepository.rename(projectId, readNonEmptyLine("Enter new project name:"));
            view.displayMessage("Project renamed to '" + renamed.getProjectName() + "'.");
        } catch (ModelOperationException ex) {
            view.displayError(ex.getMessage());
        }
    }

    /**
     * Deletes a project selected from the project list menu
     */
    private void deleteProjectFromList() {
        final int projectId = readInt("Enter project id to delete:");
        try {
            projectRepository.delete(projectId);
            taskRepository.deleteAllForProject(projectId);
            view.displayMessage("Project deleted");
        } catch (ModelOperationException ex) {
            view.displayError(ex.getMessage());
        }
    }

    /**
     * Parses a project identifier from user input allowing plain numbers or #prefixed values
     *
     * @param input raw user input
     * @return parsed project identifier
     * @throws NumberFormatException when the value cannot be parsed as an identifier
     */
    private int parseProjectId(final String input) {
        final String normalized = input.startsWith("#") ? input.substring(1) : input;
        if (normalized.isEmpty()) {
            throw new NumberFormatException("Empty project identifier");
        }
        return Integer.parseInt(normalized);
    }
}
