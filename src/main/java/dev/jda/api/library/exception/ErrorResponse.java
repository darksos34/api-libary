package dev.jda.api.library.exception;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int status;
    private String path;
    private String error;
    private Timestamp timeStamp;

    public ErrorResponse(String message, int status, String path, String error, Timestamp timeStamp) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.error = error;
        this.timeStamp = timeStamp;
    }
}
