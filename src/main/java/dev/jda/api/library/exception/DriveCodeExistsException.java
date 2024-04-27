package dev.jda.api.library.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriveCodeExistsException extends RuntimeException {
    private final String code;

    public DriveCodeExistsException(String code) {
        super("Drive with code " + code + " already exists.");
        this.code = code;
    }
}
