package ru.kotikov.blog.services.impl;

public class UserIsExistsException extends RuntimeException {

    public UserIsExistsException(String message) {
        super(message);
    }

}
