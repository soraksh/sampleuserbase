package com.soraksh.sampleuserbase.rest;

import com.soraksh.sampleuserbase.rest.exception.EntityNotFoundException;
import com.soraksh.sampleuserbase.rest.exception.ExceptionInfo;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ExceptionInfo> handleEntityNotFoundException(EntityNotFoundException e) {
        ExceptionInfo info = new ExceptionInfo("EntityNotFoundException", e.getMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(PersistenceException.class)
    ResponseEntity<ExceptionInfo> handlePersistenceException(PersistenceException e) {
        ExceptionInfo info = new ExceptionInfo(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ExceptionInfo> handleEntityNotFoundException(ConstraintViolationException e) {
        ExceptionInfo info = new ExceptionInfo("ConstraintViolationException",
                "Database constraint violation. More info: " + e.getMessage());
        return new ResponseEntity<>(info, HttpStatus.BAD_REQUEST);
    }
}
