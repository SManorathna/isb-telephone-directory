package com.isb.telephonedirectory.exception.handler;

import com.isb.telephonedirectory.exception.ExceptionResponse;
import com.isb.telephonedirectory.exception.InputValidationExceptionResponse;
import com.isb.telephonedirectory.exception.ResultNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice("com.isb.telephonedirectory.controller")
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Set<String>> errorsMap =  fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );

        ExceptionResponse response = new InputValidationExceptionResponse(LocalDateTime.now(),
                                                                          status.toString(),
                                                                          ex.getParameter().getParameterName(),
                                                                          request.getDescription(false),
                                                                          errorsMap);
        return new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler(ResultNotFoundException.class)
    public ResponseEntity<Object> handleResultNotFoundException(final Exception ex, final WebRequest req)
    {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.toString(),
                ex.getMessage(),
                req.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IOException.class, InterruptedException.class, ExecutionException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleServerException(final Exception ex, final WebRequest req)
    {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ex.getMessage(),
                req.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
