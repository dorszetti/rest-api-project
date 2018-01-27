package pl.kowal.restproject.service.validators;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import pl.kowal.restproject.domain.Availability;
import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.exception.BookingNotAvailableException;
import pl.kowal.restproject.service.BookingValidator;

@Service
public class BookingAvailabilityValidator implements BookingValidator {

	@Override
	public void validate(Booking booking) {
    	Predicate<Availability> isAvailableForBookingPeriod = a -> !a.getStartDate().isAfter(booking.getCheckIn()) && !a.getEndDate().isBefore(booking.getCheckOut());
		booking.getRoom().getAvailabilityPeriods().stream()
			.filter(isAvailableForBookingPeriod).findAny()
			.orElseThrow(() -> new BookingNotAvailableException("Booking not available in given period"));
	}
}
