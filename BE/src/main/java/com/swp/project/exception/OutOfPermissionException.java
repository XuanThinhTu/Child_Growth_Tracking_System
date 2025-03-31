package com.swp.project.exception;

public class OutOfPermissionException extends RuntimeException{

    public OutOfPermissionException(String message) {
        super(message);
    }
}
