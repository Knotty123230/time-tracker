package com.privat.timetracker.exception.dto;

import java.util.List;

/**
 * Represents the structure of an error response that will be sent to clients when an exception occurs.
 * This record encapsulates the error messages, the HTTP status code, and the timestamp of the error.
 */
public record ErrorResponse(
        /**
         * A list of error messages describing the issue(s) that occurred.
         * Each message provides details about the error and helps in understanding the cause.
         */
        List<String> message,

        /**
         * The HTTP status code associated with the error response.
         * This code indicates the type of error and is used to inform the client about the nature of the problem.
         */
        int statusCode,

        /**
         * The timestamp when the error occurred.
         * This is formatted as a string and provides the date and time of the error, helping to track when the issue happened.
         */
        String timestamp
) {

}