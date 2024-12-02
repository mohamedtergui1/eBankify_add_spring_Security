package org.example.ebankify.exception;

public class DeleteUpdateException extends RuntimeException {
    public DeleteUpdateException(String message) {
        super(message);
    }
}