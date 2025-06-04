package com.vena.learning.exception.customException.UserExceptions;

public class UserDeletionNotAuthorizedException extends RuntimeException {
    public UserDeletionNotAuthorizedException(String message) {
        super(message);
    }

    public UserDeletionNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDeletionNotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
