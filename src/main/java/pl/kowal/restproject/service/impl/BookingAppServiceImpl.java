package pl.kowal.restproject.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;
import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.dto.BookingDto;
import pl.kowal.restproject.dto.BookingRequestDto;
import pl.kowal.restproject.repository.BookingRepository;
import pl.kowal.restproject.service.BookingAppService;

@Service
@Validated
@Slf4j
public class BookingAppServiceImpl implements BookingAppService {

	private final BookingRepository bookingRepository;
	private final BookingTransformer bookingTransformer;
	private final BookingCancelService bookingCancelService;
	private final BookingRegisterService bookingRegisterService;
	
	@Inject
	public BookingAppServiceImpl(final BookingRepository bookingRepository, BookingTransformer bookingTransformer,
			BookingCancelService bookingCancelService, BookingRegisterService bookingRegisterService) {
		this.bookingRepository = bookingRepository;
		this.bookingTransformer = bookingTransformer;
		this.bookingCancelService = bookingCancelService;
		this.bookingRegisterService = bookingRegisterService;
	}

	@Override
	@Transactional
	public BookingDto register(BookingRequestDto bookingDto, Long clientId) {
		log.debug("Creating {}", bookingDto);
		Booking booking = bookingTransformer.toDomain(bookingDto, clientId);
		return bookingTransformer.toDto(bookingRegisterService.register(booking));
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookingDto> getListForClient(Long clientId) {
		log.debug("Retrieving the list of bookings for given clientId {}, clientId");
		return bookingTransformer.toDto(bookingRepository.findByClientId(clientId));
	}

	@Override
	@Transactional
	public BookingDto cancel(Long bookingId) {
		log.debug("Cancelling booking with id {}, bookingId");
		return bookingTransformer.toDto(bookingCancelService.cancel(bookingId));
	}

}
