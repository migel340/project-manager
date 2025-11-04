package com.example.simple.jira.model.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.simple.jira.model.exceptions.ModelOperationException;
import com.example.simple.jira.model.domain.Project;

/**
 * In-memory repository responsible for managing projects
 *
 * Repository generates identifiers for projects and guarantees that returned
 * model objects remain immutable
 *
 * @author spacedesk2
 * @version 1.0
 */
public class ProjectRepository {

    /**
     * Explicit no-arg constructor. Present to provide a documented public
     * constructor so Javadoc does not emit a "default constructor" warning.
     */
    public ProjectRepository() {
        // Intentionally empty - repository is in-memory and needs no config.
    }

    /** Storage for projects indexed by identifier. */
    private final Map<Integer, Project> projects = new ConcurrentHashMap<>();
    /** Generator for project identifiers. */
    private final AtomicInteger nextProjectId = new AtomicInteger(1);

    /**
     * Retrieves an immutable snapshot of all projects
     *
     * @return unmodifiable list of projects currently stored in the repository
     */
    public List<Project> findAll() {
        final List<Project> result = new ArrayList<>(projects.values());
        result.sort((left, right) -> Integer.compare(left.getProjectId(), right.getProjectId()));
        return Collections.unmodifiableList(result);
    }

    /**
     * Retrieves a project by identifier.
     *
     * @param projectId identifier of the desired project
     * @return immutable project instance
     * @throws ModelOperationException when a project with the provided identifier is not found
     */
    public Project findById(final int projectId) {
        final Project project = projects.get(projectId);
        if (project == null) {
            throw new ModelOperationException("Project with id " + projectId + " not found");
        }
        return project;
    }

    /**
     * Creates a new project and persists it in the repository
     *
     * @param projectName human readable project name
     * @return immutable project registered in the repository
     */
    public Project create(final String projectName) {
        final int projectId = nextProjectId.getAndIncrement();
        final Project project = new Project(projectId, projectName);
        projects.put(projectId, project);
        return project;
    }

    /**
     * Persists the provided immutable project replacing any prior version
     *
     * @param project project instance that should be stored
     * @return stored project reference
     */
    public Project save(final Project project) {
        projects.put(project.getProjectId(), project);
        return project;
    }

    /**
     * Updates the human readable name of an existing project
     *
     * @param projectId identifier of the project to rename
     * @param newName new project name
     * @return renamed project instance
     * @throws ModelOperationException when the project does not exist
     */
    public Project rename(final int projectId, final String newName) {
        final Project project = findById(projectId);
        final Project renamed = project.withName(newName);
        return save(renamed);
    }

    /**
     * Removes a project from the repository
     *
     * @param projectId identifier of the project to be removed
     * @throws ModelOperationException when the project does not exist
     */
    public void delete(final int projectId) {
        if (projects.remove(projectId) == null) {
            throw new ModelOperationException("Project with id " + projectId + " not found");
        }
    }

}
