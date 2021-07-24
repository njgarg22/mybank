package com.neeraj.mybank.web;

import com.neeraj.mybank.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        List<String> invalidFields = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(a -> a.getField())
                .collect(Collectors.toList());
        return new ErrorDto(exception.getMessage(), invalidFields);
    }

    // No need to handle ConstraintViolationException as of now.

}
