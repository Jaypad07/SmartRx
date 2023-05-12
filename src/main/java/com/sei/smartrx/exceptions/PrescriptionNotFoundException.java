package com.sei.smartrx.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrescriptionNotFoundException extends RuntimeException {
    public PrescriptionNotFoundException(Long id) {
        super("Prescription:" + id + "could not be found. ");
    }
}



