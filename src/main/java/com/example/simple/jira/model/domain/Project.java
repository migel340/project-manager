package com.example.simple.jira.model.domain;

import com.example.simple.jira.model.repository.TaskRepository;

/**
 * Immutable representation of a Jira-like project
 *
 * Project instances carry only identifier and name, while related tasks are
 * managed by {@link TaskRepository}
 *
 * @author spacedesk2
 * @version 1.0
 */
public final class Project {

    /** Identifier of the project */
    private final int projectId;
    /** Human readable project name */
    private final String projectName;

    /**
     * Creates a new immutable project instance
     *
     * @param projectId identifier assigned by the repository
     * @param projectName descriptive name of the project
     */
    public Project(final int projectId, final String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    /**
     * Returns the project identifier
     *
     * @return immutable project identifier
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Returns the project name
     *
     * @return immutable project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Creates a copy of the project with a new name
     *
     * @param newName updated project name
     * @return project with the new name applied
     */
    public Project withName(final String newName) {
        return new Project(projectId, newName);
    }
}
