package ru.kotikov.library.Exceptions;

public class EmptyInputFieldException extends RuntimeException{
    public EmptyInputFieldException(String message) {
        super(message);
    }
}
