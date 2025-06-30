package com.bci.interview.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
public UserAlreadyExistsException(String message) {
    super(message);
    }
}
