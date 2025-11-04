/**
 * Repository implementations for the simple Jira application.
 *
 * <p>This package contains interfaces and in-memory implementations that provide
 * persistence-like access to domain objects used by the application. Typical
 * responsibilities include creating, updating and deleting {@link
 * com.example.simple.jira.model.domain.Project} and {@link
 * com.example.simple.jira.model.domain.Task} instances.
 *
 * <p>Primary classes and interfaces:
 * <ul>
 *   <li>{@link com.example.simple.jira.model.repository.ProjectRepository} - in-memory
 *       project store
 *   <li>{@link com.example.simple.jira.model.repository.TaskRepository} - in-memory
 *       task store
 *   <li>Interfaces: {@link com.example.simple.jira.model.repository.ProjectStore}
 *       and {@link com.example.simple.jira.model.repository.TaskStore}
 * </ul>
 *
 * <p>Current implementations are intentionally simple and use thread-safe
 * collections to allow easy testing and demonstration. If you later switch to
 * a persistent store (JDBC, JPA, etc.), adapt the concrete implementations to
 * the corresponding storage APIs and keep the interfaces as the contract.
 */
package com.example.simple.jira.model.repository;
