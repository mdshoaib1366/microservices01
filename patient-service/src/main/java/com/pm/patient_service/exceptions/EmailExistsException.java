package com.pm.patient_service.exceptions;

public class EmailExistsException extends RuntimeException{

    public EmailExistsException(String message) {
        super(message);
    }
}
