package pl.kowal.restproject.service;

import static pl.kowal.restproject.service.DomainBuildersUtil.availabilityBuilder;
import static pl.kowal.restproject.service.DomainBuildersUtil.bookingBuilder;
import static pl.kowal.restproject.service.DomainBuildersUtil.roomBuilder;
//import static pl.kowal.restproject.domain.Room.builder;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.kowal.restproject.domain.Availability;
import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.domain.Room;
import pl.kowal.restproject.exception.BookingNotAvailableException;
import pl.kowal.restproject.service.validators.BookingAvailabilityValidator;

@RunWith(MockitoJUnitRunner.class)
public class BookingAvailabilityValidatorTest {

	BookingAvailabilityValidator sut;

    @Before
    public void setUp() throws Exception {
        sut = new BookingAvailabilityValidator();
    }

    @Test(expected = BookingNotAvailableException.class)
    public void shouldThrowBookingNotAvailable() throws Exception {
        //given
		Availability availability = availabilityBuilder()
				.startDate(LocalDate.parse("2018-01-01"))
				.endDate(LocalDate.parse("2020-01-01"))
				.build();
		Room room = roomBuilder().availabilityPeriods(Arrays.asList(availability)).build();
		Booking bookingForUnavailableRoom = bookingBuilder()
				.checkIn(LocalDate.parse("2017-01-01"))
				.checkOut(LocalDate.parse("2017-01-10"))
				.room(room)
				.build();
	
		//when
		sut.validate(bookingForUnavailableRoom);
    }

    @Test
    public void shouldValidateProperly() throws Exception {
        //given
		Availability availability = availabilityBuilder()
				.startDate(LocalDate.parse("2018-01-01"))
				.endDate(LocalDate.parse("2020-01-01"))
				.build();
		Room room = roomBuilder().availabilityPeriods(Arrays.asList(availability)).build();
		Booking bookingForUnavailableRoom = bookingBuilder()
				.checkIn(LocalDate.parse("2018-01-01"))
				.checkOut(LocalDate.parse("2018-01-10"))
				.room(room)
				.build();
	
		try {
			sut.validate(bookingForUnavailableRoom);
		} catch (BookingNotAvailableException e) {
			Assert.fail(e.getMessage());
		}
    }
    
}
