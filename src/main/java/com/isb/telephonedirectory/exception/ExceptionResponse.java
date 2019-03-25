package com.isb.telephonedirectory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private String timestamp;
    private int errorCode;
    private String errorMessage;
}
