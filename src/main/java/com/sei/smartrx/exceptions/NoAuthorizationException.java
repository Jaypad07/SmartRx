package com.sei.smartrx.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoAuthorizationException extends RuntimeException{
    String message;
    public NoAuthorizationException(String message){
        super(message);
    }
}

