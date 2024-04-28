package dev.jda.api.library.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static class CodeExistsExceptionHandler extends Exception {
        public CodeExistsExceptionHandler(String code) {
            super(" with code " + code + " already exists.");
        }
    }
    @ExceptionHandler(CodeExistsExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleCustomCodeExistsException(CodeExistsExceptionHandler ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 409, "/v1/users","Conflict", Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A null value was encountered where it was not expected.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
