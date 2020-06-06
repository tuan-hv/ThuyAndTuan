package com.serviceorder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class FileDuplicateException  extends RuntimeException{

    public FileDuplicateException(String message){
        super(message);
    }
}
