package ru.kotikov.blog.services.impl;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String message) {
        super(message);
    }

}
