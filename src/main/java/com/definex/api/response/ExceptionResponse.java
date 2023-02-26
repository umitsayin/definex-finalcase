package com.definex.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus statusCode;
    private String message;
    private String path;
    private ZonedDateTime dateTime;
}
