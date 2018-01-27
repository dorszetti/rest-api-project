package pl.kowal.restproject.service;

import java.util.List;

import pl.kowal.restproject.dto.BookingDto;
import pl.kowal.restproject.dto.BookingRequestDto;

/*
 * Application service
 */
public interface BookingAppService {

    BookingDto register(BookingRequestDto bookingDto, Long clientId);

    List<BookingDto> getListForClient(Long clientId);

    BookingDto cancel(Long bookingId);

}
