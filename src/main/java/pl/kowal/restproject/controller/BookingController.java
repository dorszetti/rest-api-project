package pl.kowal.restproject.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.kowal.restproject.dto.BookingDto;
import pl.kowal.restproject.dto.BookingRequestDto;
import pl.kowal.restproject.service.BookingAppService;

@RestController
@Slf4j
public class BookingController  extends AbstractController {

	private final BookingAppService bookingService;

	@Inject
	public BookingController(final BookingAppService bookingService) {
		this.bookingService = bookingService;
	}

	@RequestMapping(path = "/booking/client/{clientId}", method = RequestMethod.POST)
	public BookingDto createBooking(@RequestBody @Valid final BookingRequestDto bookingDto, @PathVariable Long clientId) {
		log.debug("Creating {}", bookingDto);
		return bookingService.register(bookingDto, clientId);
	}

	@RequestMapping(path = "/booking/client/{clientId}", method = RequestMethod.GET)
	public List<BookingDto> showBookings(@PathVariable Long clientId) {
		log.debug("Listing all bookings for client {}, clientId");
		return bookingService.getListForClient(clientId);
	}

	@RequestMapping(path = "/booking/{bookingId}", method = RequestMethod.DELETE)
	public BookingDto cancelBooking(@PathVariable Long bookingId) {
		log.debug("Canceling booking with id {}", bookingId);
		return bookingService.cancel(bookingId);
	}
}
