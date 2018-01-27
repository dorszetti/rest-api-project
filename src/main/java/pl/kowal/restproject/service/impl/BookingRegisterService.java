package pl.kowal.restproject.service.impl;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.repository.BookingRepository;
import pl.kowal.restproject.service.BookingValidator;

/*
 * Business logic service
 */
@Service
public class BookingRegisterService {

	private final BookingRepository bookingRepository;
	private final Collection<BookingValidator> validators;

	@Inject
	public BookingRegisterService(final BookingRepository bookingRepository, Collection<BookingValidator> validators) {
		this.bookingRepository = bookingRepository;
		this.validators = validators;
	}

	@Transactional
	public Booking register(Booking  booking) {
		validators.forEach(v -> v.validate(booking));
		return bookingRepository.save(booking);
	}
}
