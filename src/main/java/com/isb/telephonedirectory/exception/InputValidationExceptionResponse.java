package com.isb.telephonedirectory.exception;

import java.util.Map;
import java.util.Set;

public class InputValidationExceptionResponse extends ExceptionResponse {

    private final Map<String, Set<String>> errors;

    public InputValidationExceptionResponse(final String timestamp,
                                            final int errorCode,
                                            final String errorMessage,
                                            final Map<String, Set<String>> errors) {
        super(timestamp, errorCode, errorMessage);
        this.errors = errors;
    }

    public Map<String, Set<String>> getErrors() {
        return errors;
    }

}
