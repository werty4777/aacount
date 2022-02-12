package com.aemw.microserviceaccount.User.Infrastructure.api;

import com.aemw.microserviceaccount.User.Domain.Exception.UserEntityException;
import com.aemw.microserviceaccount.configuration.GlobalExceptions.ExceptionGlobal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class userControllerAdvice {
    @ExceptionHandler({UserEntityException.class})
    public ResponseEntity<?> errorCreateUser(UserEntityException ex) {

        ExceptionGlobal response = new ExceptionGlobal();
        response.setMessage(ex.getMessage());
        response.setDetails(ex.getDetalles());
        return new ResponseEntity<>(response, ex.getHttpStatus());

    }
}
