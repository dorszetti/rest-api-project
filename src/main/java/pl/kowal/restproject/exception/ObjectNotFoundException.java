package pl.kowal.restproject.exception;

public class ObjectNotFoundException extends RuntimeException {
	
	private final static String MESSAGE = "Object not found";

    public ObjectNotFoundException() {
        super(MESSAGE);
    }
}
