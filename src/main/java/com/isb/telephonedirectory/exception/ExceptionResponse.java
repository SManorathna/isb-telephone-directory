package com.isb.telephonedirectory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String timestamp;
    private int errorCode;
    private String errorMessage;
}
