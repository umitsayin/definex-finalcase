package com.definex.exception;

import com.definex.api.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundException(EntityNotFoundException exception,
                                                                   HttpServletRequest request){

        log.error("Error: " +exception.getMessage());

        return ResponseEntity.ok().body(new ExceptionResponse(HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getServletPath(),
                ZonedDateTime.now()));
    }

    @ExceptionHandler(InsufficientCreditLimitException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundException(InsufficientCreditLimitException exception,
                                                                   HttpServletRequest request){
        log.error("Error: " +exception.getMessage());

        return ResponseEntity.ok().body(new ExceptionResponse(HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getServletPath(),
                ZonedDateTime.now()));
    }

    @ExceptionHandler(CreditNotIdentifiedException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundException(CreditNotIdentifiedException exception,
                                                                   HttpServletRequest request){
        log.error("Error: " +exception.getMessage());

        return ResponseEntity.ok().body(new ExceptionResponse(HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getServletPath(),
                ZonedDateTime.now()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> userNotFoundException(RuntimeException exception,
                                                                   HttpServletRequest request){
        log.error("Error: " +exception.getMessage());

        return ResponseEntity.ok().body(new ExceptionResponse(HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getServletPath(),
                ZonedDateTime.now()));
    }
}
