package pl.kowal.restproject.exception;

public class InvalidBookingDataException extends RuntimeException {
	
	private final static String MESSAGE = "Invalid booking data";

    public InvalidBookingDataException() {
        super(MESSAGE);
    }
}