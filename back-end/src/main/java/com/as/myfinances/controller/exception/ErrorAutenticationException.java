package com.as.myfinances.controller.exception;

public class ErrorAutenticationException extends RuntimeException{

    public ErrorAutenticationException (String msg){
        super(msg);
    }
}
