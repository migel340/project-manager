package com.example.simple.jira.model;

/**
 * Signals issues while manipulating immutable model objects
 *
 * @author spacedesk2
 * @version 1.0
 */
public class ModelOperationException extends RuntimeException {

    /** Serialization identifier. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new exception containing the provided message
     *
     * @param message detail describing what prevented the operation
     */
    public ModelOperationException(final String message) {
        super(message);
    }

    /**
     * Creates a new exception containing the provided message and cause
     *
     * @param message detail describing what prevented the operation
     * @param cause root cause of the failure
     */
    public ModelOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
