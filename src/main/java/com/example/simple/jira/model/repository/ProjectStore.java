package com.example.simple.jira.model.repository;

import com.example.simple.jira.model.domain.Project;
import java.util.List;

/**
 * Persistence contract for projects
 *
 * @author michalkubina
 * @version 1.0
 */
public interface ProjectStore {
    /**
     * Return all projects known to the store
     *
     * @return list of projects (may be empty)
     */
    List<Project> findAll();

    /**
     * Find a project by its identifier
     *
     * @param projectId project identifier
     * @return project instance
     * @throws com.example.simple.jira.model.exceptions.ModelOperationException if not found
     */
    Project findById(int projectId);

    /**
     * Create a new project with the provided name
     *
     * @param projectName name of the new project
     * @return created project instance
     */
    Project create(String projectName);

    /**
     * Persist changes to an existing project
     *
     * @param project project to save
     * @return saved project instance
     */
    Project save(Project project);

    /**
     * Rename an existing project
     *
     * @param projectId id of the project to rename
     * @param newName new name for the project
     * @return renamed project instance
     */
    Project rename(int projectId, String newName);

    /**
     * Delete the project with the given id
     *
     * @param projectId id of the project to delete
     */
    void delete(int projectId);
}
