package com.emrhnsyts.todo.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setMessage("Validation failed");
        validationErrorResponse.setCreatedAt(new Date());
        validationErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        validationErrorResponse.setDetails(ex.getBindingResult().getAllErrors().stream().map(objectError -> {
            return objectError.getDefaultMessage();
        }).collect(Collectors.toList()));

        return new ResponseEntity<>(validationErrorResponse, validationErrorResponse.getHttpStatus());
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(Exception exception) {
        return exceptionUtil("Your username must be unique.");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException() {
        return exceptionUtil("Token is not matched.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleMalformedJwtException() {
        return exceptionUtil("Token is malformed.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException() {
        return exceptionUtil("Token is expired.");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<Object> handleUnsupportedJwtException() {
        return exceptionUtil("Token is not supported.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException() {
        return exceptionUtil("Token is not matched");
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Object> handleTodoNotFoundException(Exception exception) {
        return exceptionUtil(exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception exception) {

        return exceptionUtil(exception.getMessage());
    }

    private ResponseEntity<Object> exceptionUtil(String exceptionMessage) {
        ErrorResponse errorResponse =
                ErrorResponse.builder().message(exceptionMessage)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .createdAt(new Date()).build();

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
