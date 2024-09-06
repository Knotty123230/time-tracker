package com.privat.timetracker.exception.dto;

import java.util.List;

public record ErrorResponse(
        List<String> message,
        int statusCode,
        String timestamp
) {

}
