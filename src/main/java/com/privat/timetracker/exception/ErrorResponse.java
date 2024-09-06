package com.privat.timetracker.exception;

public record ErrorResponse(
        String message,
        int statusCode,
        String timestamp
) {

}
