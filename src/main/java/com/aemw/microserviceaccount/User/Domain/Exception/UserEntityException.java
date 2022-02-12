package com.aemw.microserviceaccount.User.Domain.Exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class UserEntityException extends RuntimeException {
    private final List<String> detalles = new ArrayList<>();

    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public UserEntityException(String message,HttpStatus status) {
        super(message);
        this.httpStatus=status;
    }

    public List<String> getDetalles() {
        return detalles;
    }

    public void addDetalles(String detalles) {
        this.detalles.add(detalles);
    }
}
