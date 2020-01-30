package com.filip.springbootrestdocstddapi.errorhandling;

import com.filip.springbootrestdocstddapi.errorhandling.exceptions.UserNotFoundException;
import com.filip.springbootrestdocstddapi.errorhandling.models.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("handleMethodArgumentNotValid in RestExceptionHandler class.");

        List<ApiError> collect = ex.getBindingResult()
                .getAllErrors().stream()
                .map(err -> new ApiError(err.getCodes(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(collect, headers, HttpStatus.BAD_REQUEST);

        // return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public List<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        return ex.getBindingResult()
//                .getAllErrors().stream()
//                .map(err -> new ApiError(err.getCodes(), err.getDefaultMessage()))
//                .collect(Collectors.toList());
//    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ApiError> handleValidationExceptions(ConstraintViolationException ex) {
        System.out.println("handleValidationExceptions in RestExceptionHandler class.");

        return ex.getConstraintViolations()
                .stream()
                .map(err -> new ApiError(err.getPropertyPath().toString(), err.getMessage()))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public List<ApiError> handleNotFoundExceptions(UserNotFoundException ex) {
        System.out.println("handleNotFoundException in RestExceptionHandler class.");
        return Collections.singletonList(new ApiError("user.notfound", ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public List<ApiError> handleOtherException(Exception ex) {
        System.out.println("handleOtherException in RestExceptionHandler class.");
        return Collections.singletonList(new ApiError(ex.getClass().getCanonicalName(), ex.getMessage()));
    }

}
