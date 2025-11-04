package com.example.simple.jira.model.repository;

import com.example.simple.jira.model.domain.Task;
import com.example.simple.jira.model.domain.TaskPriority;
import com.example.simple.jira.model.domain.TaskStatus;
import java.util.List;

/**
 * Persistence contract for tasks
 */
public interface TaskStore {
    List<Task> findByProject(int projectId);
    Task addTask(int projectId, String title, String description);
    void updateTaskStatus(int projectId, int taskId, TaskStatus status);
    void updateTaskPriority(int projectId, int taskId, TaskPriority priority);
    void renameTask(int projectId, int taskId, String newTitle);
    void updateTaskDescription(int projectId, int taskId, String newDescription);
    void deleteTask(int projectId, int taskId);
    void deleteAllForProject(int projectId);
}
