package com.mickc0.gtac.exception;

public class CustomDuplicateEntryException extends RuntimeException{
    public CustomDuplicateEntryException(String message) {
        super(message);
    }
}
