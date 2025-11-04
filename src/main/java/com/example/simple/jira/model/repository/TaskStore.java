package com.example.simple.jira.model.repository;

import com.example.simple.jira.model.domain.Task;
import com.example.simple.jira.model.domain.TaskPriority;
import com.example.simple.jira.model.domain.TaskStatus;
import java.util.List;

/**
 * Persistence contract for tasks
 *
 * @author michalkubina
 * @version 1.0
 */
public interface TaskStore {
    /**
     * Return tasks belonging to the specified project
     *
     * @param projectId project identifier
     * @return list of tasks (may be empty)
     */
    List<Task> findByProject(int projectId);

    /**
     * Create and add a new task to a project
     *
     * @param projectId target project id
     * @param title task title
     * @param description task description
     * @return created task
     */
    Task addTask(int projectId, String title, String description);

    /**
     * Update the status of a task
     *
     * @param projectId project id containing the task
     * @param taskId task identifier
     * @param status new status
     */
    void updateTaskStatus(int projectId, int taskId, TaskStatus status);

    /**
     * Update the priority of a task
     *
     * @param projectId project id containing the task
     * @param taskId task identifier
     * @param priority new priority
     */
    void updateTaskPriority(int projectId, int taskId, TaskPriority priority);

    /**
     * Rename an existing task
     *
     * @param projectId project id containing the task
     * @param taskId task identifier
     * @param newTitle new task title
     */
    void renameTask(int projectId, int taskId, String newTitle);

    /**
     * Update the textual description of a task
     *
     * @param projectId project id containing the task
     * @param taskId task identifier
     * @param newDescription new description text
     */
    void updateTaskDescription(int projectId, int taskId, String newDescription);

    /**
     * Remove a task from a project
     *
     * @param projectId project id containing the task
     * @param taskId task identifier
     */
    void deleteTask(int projectId, int taskId);

    /**
     * Remove all tasks that belong to the specified project
     *
     * @param projectId project identifier
     */
    void deleteAllForProject(int projectId);
}
