package pl.kowal.restproject.service.validators;

import org.springframework.stereotype.Service;

import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.exception.InvalidBookingDataException;
import pl.kowal.restproject.service.BookingValidator;

@Service
public class BookingDateRangeValidator implements BookingValidator {

	@Override
	public void validate(Booking booking) {
    	if (booking.getCheckIn().isAfter(booking.getCheckOut())) {
    		throw new InvalidBookingDataException();
    	}
	}
}
