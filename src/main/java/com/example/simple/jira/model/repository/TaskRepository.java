package com.example.simple.jira.model.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import com.example.simple.jira.model.domain.Task;
import com.example.simple.jira.model.domain.TaskPriority;
import com.example.simple.jira.model.domain.TaskStatus;
import com.example.simple.jira.model.exceptions.ModelOperationException;

/**
 * In-memory repository managing tasks assigned to projects
 *
 * The repository delegates project existence validation to
 * {@link ProjectRepository} and keeps task data immutable
 *
 * @author spacedesk2
 * @version 1.0
 */
public class TaskRepository {

    /** Storage for tasks grouped by project identifier. */
    private final Map<Integer, List<Task>> tasksByProject = new ConcurrentHashMap<>();
    /** Generator for task identifiers. */
    private final AtomicInteger nextTaskId = new AtomicInteger(1);
    /** Repository used to validate project existence. */
    private final ProjectRepository projectRepository;

    /**
     * Creates a task repository backed by the provided project repository
     *
     * @param projectRepository repository used to confirm project existence
     */
    public TaskRepository(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Retrieves tasks associated with the selected project
     *
     * @param projectId identifier of the owning project
     * @return immutable list of tasks belonging to the project
     * @throws ModelOperationException when the project does not exist
     */
    public List<Task> findByProject(final int projectId) {
        projectRepository.findById(projectId);
        return Collections.unmodifiableList(new ArrayList<>(tasksByProject.getOrDefault(projectId, Collections.emptyList())));
    }

    /**
     * Adds a new task to the selected project
     *
     * @param projectId identifier of the owning project
     * @param title task title provided by the user
     * @param description task description provided by the user
     * @return immutable task instance that was created
     * @throws ModelOperationException when the project does not exist
     */
    public Task addTask(final int projectId, final String title, final String description) {
        projectRepository.findById(projectId);
        final int taskId = nextTaskId.getAndIncrement();
        final Task task = new Task(taskId, title, description, TaskStatus.TODO, TaskPriority.NOT_SET, projectId);
        final List<Task> updated = new ArrayList<>(tasksByProject.getOrDefault(projectId, Collections.emptyList()));
        updated.add(task);
        tasksByProject.put(projectId, Collections.unmodifiableList(new ArrayList<>(updated)));
        return task;
    }

    /**
     * Updates the status of a task within the selected project
     *
     * @param projectId identifier of the owning project
     * @param taskId identifier of the task that should be updated
     * @param status new status to apply
     */
    public void updateTaskStatus(final int projectId, final int taskId, final TaskStatus status) {
        updateTask(projectId, taskId, task -> task.withStatus(status));
    }

    /**
     * Updates the priority of a task within the selected project
     *
     * @param projectId identifier of the owning project
     * @param taskId identifier of the task that should be updated
     * @param priority new priority to apply
     */
    public void updateTaskPriority(final int projectId, final int taskId, final TaskPriority priority) {
        updateTask(projectId, taskId, task -> task.withPriority(priority));
    }
    
    /**
     * Renames a task within the selected project
     *
     * @param projectId identifier of the owning project
     * @param taskId identifier of the task that should be updated
     * @param newTitle new title to apply
     */
    public void renameTask(final int projectId, final int taskId, final String newTitle) {
        updateTask(projectId, taskId, task -> task.withDetails(newTitle, task.getDescription()));
    }

    /**
     * Updates the description of a task within the selected project
     *
     * @param projectId identifier of the owning project
     * @param taskId identifier of the task that should be updated
     * @param newDescription new description to apply
     */
    public void updateTaskDescription(final int projectId, final int taskId, final String newDescription) {
        updateTask(projectId, taskId, task -> task.withDetails(task.getTitle(), newDescription));
    }

    /**
     * Ensures the selected project contains at least one task.
     *
     * @param projectId identifier of the owning project
     * @return immutable snapshot of current tasks
     * @throws ModelOperationException when the project has no tasks
     */
    public List<Task> requireTasks(final int projectId) {
        projectRepository.findById(projectId);
        final List<Task> existing = tasksByProject.get(projectId);
        if (existing == null || existing.isEmpty()) {
            throw new ModelOperationException("\nNo tasks found in project " + projectId);
        }
        return Collections.unmodifiableList(new ArrayList<>(existing));
    }

    /**
     * Removes a task from the selected project
     *
     * @param projectId identifier of the owning project
     * @param taskId identifier of the task to be removed
     * @throws ModelOperationException when the project or task cannot be found
     */
    public void deleteTask(final int projectId, final int taskId) {
        final List<Task> existing = requireTasks(projectId);
        final List<Task> updated = new ArrayList<>(existing.size());
        boolean removed = false;
        for (Task task : existing) {
            if (task.getTaskId() == taskId) {
                removed = true;
                continue;
            }
            updated.add(task);
        }
        if (!removed) {
            throw new ModelOperationException("Task with id " + taskId + " not found in project " + projectId);
        }
        if (updated.isEmpty()) {
            tasksByProject.remove(projectId);
        } else {
            tasksByProject.put(projectId, Collections.unmodifiableList(new ArrayList<>(updated)));
        }
    }

    /**
     * Updates a single task instance and stores the modified list back in the repository.
     */
    private void updateTask(final int projectId, final int taskId, final Function<Task, Task> transformer) {
        final List<Task> existing = requireTasks(projectId);

        final List<Task> updated = new ArrayList<>(existing.size());
        boolean replaced = false;
        for (Task task : existing) {
            if (task.getTaskId() == taskId) {
                updated.add(transformer.apply(task));
                replaced = true;
            } else {
                updated.add(task);
            }
        }
        if (!replaced) {
            throw new ModelOperationException("Task with id " + taskId + " not found in project " + projectId);
        }

        tasksByProject.put(projectId, Collections.unmodifiableList(new ArrayList<>(updated)));
    }

    /**
     * Removes all tasks for the specified project
     *
     * @param projectId identifier of the project whose tasks should be removed
     */
    public void deleteAllForProject(final int projectId) {
        tasksByProject.remove(projectId);
    }
}
