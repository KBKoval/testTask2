package org.example.exceptionsprocessing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
@Getter
@Setter
public class ErrorsMessage {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorsMessage(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorsMessage(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
