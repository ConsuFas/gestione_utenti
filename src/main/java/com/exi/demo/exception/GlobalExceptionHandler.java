package com.exi.demo.exception;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private ErrorResponse buildError(HttpStatus status, String message) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
    }

    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(UtenteNotFoundException ex) {
        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicateCodiceFiscaleException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateCodiceFiscaleException ex) {
        return new ResponseEntity<>(
                buildError(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return new ResponseEntity<>(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno del server"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
