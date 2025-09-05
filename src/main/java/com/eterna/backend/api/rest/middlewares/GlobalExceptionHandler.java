package com.eterna.backend.api.rest.middlewares;

import com.eterna.backend.api.rest.schemas.common.ErrorResponse;
import com.eterna.backend.core.auth.domain.exception.AuthenticationFailedException;
import com.eterna.backend.core.auth.domain.exception.UserNotActiveException;
import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleUserNotActive(
            UserNotActiveException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex, request);
    }

    @ExceptionHandler({
            DomainException.class,
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(
            Exception ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class,
            DisabledException.class,
            LockedException.class,
            AuthenticationFailedException.class
    })
    public ResponseEntity<ErrorResponse> handleAuthFailures(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
                                                       HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            Exception ex,
            HttpServletRequest request) {
        var error = ErrorResponse.of(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }
}
