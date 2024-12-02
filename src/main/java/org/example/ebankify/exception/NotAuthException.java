package org.example.ebankify.exception;

public class NotAuthException extends RuntimeException {
    public NotAuthException(String message) {
        super(message);
    }
}
