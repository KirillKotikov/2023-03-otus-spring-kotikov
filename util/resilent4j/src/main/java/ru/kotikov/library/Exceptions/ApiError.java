package ru.kotikov.library.Exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiError {

    private final int code;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Instant timestamp;

    private final String error;

    public ApiError(int code, String error) {
        this.code = code;
        this.timestamp = Instant.now();
        this.error = error;
    }

}