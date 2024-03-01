package org.example.exceptionsprocessing;

import jakarta.validation.ConstraintViolationException;
import org.example.utils.SortColum;
import org.example.utils.SortMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.addAll(ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList()));
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ErrorsMessage errorsMessage = new ErrorsMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, errorsMessage, headers, errorsMessage.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ErrorsMessage errorsMessage = new ErrorsMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(errorsMessage, new HttpHeaders(), errorsMessage.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class,IllegalStateException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(RuntimeException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add("This argument specification violated. Valid values valid values in any register: ");
        errors.add("for argument column: ");
        errors.addAll( Arrays.stream(SortColum.values()).map(m->"  "+m.toString()).collect(Collectors.toList() )   );
        errors.add("for argument sort: ");
        errors.addAll( Arrays.stream(SortMethod.values()).map(m->"  "+m.toString()).collect(Collectors.toList() )   );
        ErrorsMessage errorsMessage = new ErrorsMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(errorsMessage, new HttpHeaders(), errorsMessage.getStatus());
    }
}
