package pl.kowal.restproject.service.validators;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.exception.BookingNotAvailableException;
import pl.kowal.restproject.repository.BookingRepository;
import pl.kowal.restproject.service.BookingValidator;

@Service
public class BookingCollisionValidator implements BookingValidator {

	private final BookingRepository bookingRepository;
	
	@Inject
	public BookingCollisionValidator(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	@Transactional
	public void validate(Booking booking) {
		Predicate<Booking> isCollidingWithOtherBookings = b -> b.getCheckIn().isBefore(booking.getCheckOut()) && b.getCheckOut().isAfter(booking.getCheckIn());
		List<Booking> bookingsForRoom = bookingRepository.findByRoomId(booking.getRoom().getId());
		Optional<Booking> collidingBooking = bookingsForRoom.stream().filter(Booking::isActive).filter(isCollidingWithOtherBookings).findAny();
		collidingBooking.ifPresent(s -> {throw new BookingNotAvailableException("Colliding with other booking");});
	}
}
