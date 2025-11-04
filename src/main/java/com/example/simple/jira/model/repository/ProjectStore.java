package com.example.simple.jira.model.repository;

import com.example.simple.jira.model.domain.Project;
import java.util.List;

/**
 * Persistence contract for projects
 */
public interface ProjectStore {
    List<Project> findAll();
    Project findById(int projectId);
    Project create(String projectName);
    Project save(Project project);
    Project rename(int projectId, String newName);
    void delete(int projectId);
}
