package pl.kowal.restproject.service.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.domain.BookingStatus;
import pl.kowal.restproject.exception.BookingAlreadyCanceledException;
import pl.kowal.restproject.exception.ObjectNotFoundException;
import pl.kowal.restproject.repository.BookingRepository;

/*
 * Business logic service
 */
@Service
public class BookingCancelService {

	private final BookingRepository bookingRepository;
	
	@Inject
	public BookingCancelService(final BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Transactional
	public Booking cancel(Long bookingId) {
		Booking booking = Optional.ofNullable(bookingRepository.findOne(bookingId)).orElseThrow(() -> new ObjectNotFoundException());
		performCancel(booking);
		return bookingRepository.save(booking);
	}

	private Booking performCancel(Booking booking) {
		if (!booking.isActive()) {
			throw new BookingAlreadyCanceledException("This booking has already been canceled");
		}
		booking.setStatus(BookingStatus.CANCELED);
		return booking;
	}
}
