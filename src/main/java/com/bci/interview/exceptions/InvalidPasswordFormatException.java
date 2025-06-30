package com.bci.interview.exceptions;

public class InvalidPasswordFormatException extends  RuntimeException {
    public InvalidPasswordFormatException(String message) {
        super(message);
    }
}