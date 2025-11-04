package com.example.simple.jira.model.domain;

/**
 * Enumeration representing the possible statuses of a task
 * @author spacedesk2
 * @version 1.0
 */

public enum TaskStatus {
    /** Task has not been started yet. */
    TODO,
    /** Task is currently in progress. */
    IN_PROGRESS,
    /** Task has been completed. */
    DONE
}