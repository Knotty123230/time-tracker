package com.privat.timetracker.exception.constants;

/**
 * Class containing constant error message templates used throughout the application.
 * These messages are used for generating error responses and logging exceptions.
 */
public class ErrorMessages {
    /**
     * Error message template for when a task is not found.
     * The placeholder will be replaced with the task ID.
     */
    public static final String TASK_NOT_FOUND = "Task not found with id %d";

    /**
     * Error message template for unexpected errors.
     * The placeholder will be replaced with the details of the unexpected error.
     */
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred: %s";

    /**
     * Error message template for exceptions occurring while handling tasks.
     * The placeholders will be replaced with the action (e.g., start/stop) being performed,
     * the task ID, and the exception message.
     */
    public static final String TASK_TIME_EXCEPTION = "Error while %s task with id %d exception : %s";

    /**
     * Error message template for exceptions occurring during the automatic closure of tasks.
     * The placeholder will be replaced with the exception message.
     */
    public static final String TASK_AUTO_CLOSE_EXCEPTION = "Error while autoclose task  exception : %s";
}