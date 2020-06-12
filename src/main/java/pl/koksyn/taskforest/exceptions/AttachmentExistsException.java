package pl.koksyn.taskforest.exceptions;

public class AttachmentExistsException extends RuntimeException {
    public AttachmentExistsException(String message) {
        super(message);
    }

    public AttachmentExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
