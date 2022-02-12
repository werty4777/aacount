package com.aemw.microserviceaccount.configuration.GlobalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalException  extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> errorValid() {
        System.out.println("entre aca");
        ExceptionGlobal global = new ExceptionGlobal();
        global.setDetails(Arrays.asList("Forbidden 2 "));
        return new ResponseEntity<>(global, HttpStatus.FORBIDDEN);
    }



}
