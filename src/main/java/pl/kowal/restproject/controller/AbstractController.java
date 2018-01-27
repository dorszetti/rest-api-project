package pl.kowal.restproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.kowal.restproject.exception.BookingAlreadyCanceledException;
import pl.kowal.restproject.exception.BookingNotAvailableException;
import pl.kowal.restproject.exception.InvalidBookingDataException;
import pl.kowal.restproject.exception.ObjectNotFoundException;

public abstract class AbstractController {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleBookingAlreadyCanceledException(BookingAlreadyCanceledException e) {
		return e.getMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleBookingNotAvailableException(BookingNotAvailableException e) {
		return e.getMessage();
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleObjectNotFoundException(ObjectNotFoundException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleBookingNotAvailableException(InvalidBookingDataException e) {
		return e.getMessage();
	}
}
