package com.isb.telephonedirectory.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Getter
public class InputValidationExceptionResponse extends ExceptionResponse {

    private final Map<String, Set<String>> errors;

    public InputValidationExceptionResponse(final LocalDateTime timestamp,
                                            final String errorCode,
                                            final String errorMessage,
                                            final String description,
                                            final Map<String, Set<String>> errors) {
        super(timestamp, errorCode, errorMessage, description);
        this.errors = errors;
    }
}
