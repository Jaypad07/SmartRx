package com.sei.smartrx.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * useful to have custom error messages to be more informative for developer and/or user
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class InformationExistException extends RuntimeException{
    public InformationExistException(String message){
        super(message);
    }
}
