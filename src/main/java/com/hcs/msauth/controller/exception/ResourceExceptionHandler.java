package com.hcs.msauth.controller.exception;

import com.hcs.msauth.services.exceptions.DataBaseException;
import com.hcs.msauth.services.exceptions.ResourceNotFoundException;
import com.hcs.msauth.services.exceptions.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
            StandardError err = new StandardError();
            err.setTimestamp(Instant.now());
            err.setError("Resource Not Found!");
            err.setStatus(HttpStatus.NOT_FOUND.value());
            err.setMessage(e.getMessage());
            err.setPath(request.getRequestURI());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }

        @ExceptionHandler(DataBaseException.class)
        public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request){
            StandardError err = new StandardError();
            err.setTimestamp(Instant.now());
            err.setError("Database Exception!");
            err.setStatus(HttpStatus.BAD_REQUEST.value());
            err.setMessage(e.getMessage());
            err.setPath(request.getRequestURI());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
            ValidationError err = new ValidationError();
            err.setTimestamp(Instant.now());
            err.setError("Validation Exception!");
            err.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            err.setMessage(e.getMessage());
            err.setPath(request.getRequestURI());

            for(FieldError f : e.getBindingResult().getFieldErrors()){
                err.addError(f.getField(), f.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
        }
}
