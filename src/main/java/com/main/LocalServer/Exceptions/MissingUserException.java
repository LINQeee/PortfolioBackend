package com.main.LocalServer.Exceptions;

public class MissingUserException extends RuntimeException {
    public MissingUserException(String message){
        super(message);
    }
}
