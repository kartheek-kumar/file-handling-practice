package com.example.filehandling.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ExceptionResponseObject {

    private HttpStatus errorStatus;

    private String errorMessage;

    private String errorDescription;
}
