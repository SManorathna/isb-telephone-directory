package com.isb.telephonedirectory.exception.handler;

import com.isb.telephonedirectory.exception.InputValidationExceptionResponse;
import com.isb.telephonedirectory.exception.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice("com.isb.telephonedirectory.controller")
@Controller
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        // retrieve all the error messages for invalid input fields
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Set<String>> errorsMap =  fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );

        ExceptionResponse response = new InputValidationExceptionResponse(LocalDateTime.now().toString(), status.value(), status.name(), errorsMap);
        return new ResponseEntity(response, headers, status);
    }

}
