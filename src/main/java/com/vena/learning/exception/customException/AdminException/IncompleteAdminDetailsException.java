package com.vena.learning.exception.customException.AdminException;

public class IncompleteAdminDetailsException extends RuntimeException{
    public IncompleteAdminDetailsException(String message){
        super(message);
    }

    public IncompleteAdminDetailsException(String message, Throwable cause){
        super(message,cause);
    }

    public IncompleteAdminDetailsException(Throwable cause){
        super(cause);
    }
}
