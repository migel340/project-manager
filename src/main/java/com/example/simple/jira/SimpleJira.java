package com.example.simple.jira;
import com.example.simple.jira.controller.Controller;
import com.example.simple.jira.model.repository.ProjectRepository;
import com.example.simple.jira.model.domain.Project;
import com.example.simple.jira.model.repository.TaskRepository;
import com.example.simple.jira.view.View;

/**
 * Application entry point creating dependencies and launching the controller.
 *
 * Command-line parameters order:
 * 
 * args [0] – optional project name created on startup.
 * args [1] – optional initial task title (requires project name).
 * args [2] – optional initial task description (requires task title).
 * 
 *
 * If parameters are missing the application switches to interactive mode and
 * asks the user for the required data.
 *
 * @author spacedesk2
 * @version 1.0
 * @param args optional command line parameters described above
 */
public final class SimpleJira {

    private SimpleJira() {
    }

    /**
     * Bootstraps the MVC stack and starts the controller loop, args parser
     *
     * @param args optional command line arguments described in the class documentation
     */
    public static void main(final String[] args) {
        final View view = new View();
    final ProjectRepository projectRepository = new ProjectRepository();
    final TaskRepository taskRepository = new TaskRepository(projectRepository);
    final Controller controller = new Controller(view, projectRepository, taskRepository);

        if (args.length > 0) {
            final String projectName = args[0];
            final Project project = projectRepository.create(projectName);
            view.displayMessage("Project '" + projectName + "' created from command line arguments\n");

            if (args.length > 1) {
                final String taskTitle = args[1];
                final String taskDescription = args.length > 2 ? args[2] : "";
                taskRepository.addTask(project.getProjectId(), taskTitle, taskDescription);
                view.displayMessage("Initial task '" + taskTitle + "' created from command line arguments\n");
            }
        } else {
            view.displayMessage("No command line arguments provided. Use interactive mode to create projects\n");
        }

        controller.run();
    }
}
