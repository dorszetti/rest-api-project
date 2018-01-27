package pl.kowal.restproject.exception;

public class BookingAlreadyCanceledException extends RuntimeException {

    public BookingAlreadyCanceledException(final String message) {
        super(message);
    }
}
