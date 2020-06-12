package pl.koksyn.taskforest.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.koksyn.taskforest.exceptions.AttachmentExistsException;
import pl.koksyn.taskforest.exceptions.NotFoundException;
import java.io.IOException;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity badRequestHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({AttachmentExistsException.class})
    public ResponseEntity conflictHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity notFoundHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({IOException.class, RuntimeException.class})
    public ResponseEntity serverErrorHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
