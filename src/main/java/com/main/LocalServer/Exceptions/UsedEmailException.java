package com.main.LocalServer.Exceptions;

public class UsedEmailException extends RuntimeException{
    public UsedEmailException(String message){
        super(message);
    }
}
