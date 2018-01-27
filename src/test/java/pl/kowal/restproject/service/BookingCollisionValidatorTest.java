package pl.kowal.restproject.service;

import static org.mockito.Mockito.when;
import static pl.kowal.restproject.service.DomainBuildersUtil.bookingBuilder;
import static pl.kowal.restproject.service.DomainBuildersUtil.roomBuilder;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.domain.BookingStatus;
import pl.kowal.restproject.domain.Room;
import pl.kowal.restproject.exception.BookingNotAvailableException;
import pl.kowal.restproject.repository.BookingRepository;
import pl.kowal.restproject.service.validators.BookingCollisionValidator;

@RunWith(MockitoJUnitRunner.class)
public class BookingCollisionValidatorTest {

	private static final Room ANY_ROOM = roomBuilder().id(1L).build();
	
	BookingCollisionValidator sut;

	@Mock
	BookingRepository bookingRepositoryMock;
	
    @Before
    public void setUp() throws Exception {
        sut = new BookingCollisionValidator(bookingRepositoryMock);
    }

    @Test(expected = BookingNotAvailableException.class)
    public void shouldCollideWithActiveBooking() throws Exception {
        //given

		Booking booking = bookingBuilder()
					.checkIn(LocalDate.parse("2017-01-01"))
					.checkOut(LocalDate.parse("2017-01-10"))
					.room(ANY_ROOM)
					.status(BookingStatus.ACTIVE)
					.build();

		 when(bookingRepositoryMock.findByRoomId(ANY_ROOM.getId())).thenReturn(Arrays.asList(
				 bookingBuilder()
				 	.checkIn(LocalDate.parse("2017-01-01"))
				 	.checkOut(LocalDate.parse("2017-02-01"))
				 	.status(BookingStatus.ACTIVE)
				 	.build()));
		//when
		sut.validate(booking);
    }

    @Test
    public void shouldNotCollideWithCanceledBooking() throws Exception {
        //given
		Booking booking = bookingBuilder()
					.checkIn(LocalDate.parse("2017-01-01"))
					.checkOut(LocalDate.parse("2017-01-10"))
					.room(ANY_ROOM)
					.status(BookingStatus.ACTIVE)
					.build();

		 when(bookingRepositoryMock.findByRoomId(ANY_ROOM.getId())).thenReturn(Arrays.asList(
				 bookingBuilder()
					 .checkIn(LocalDate.parse("2017-01-11"))
					 .checkOut(LocalDate.parse("2017-02-01"))
					 .room(ANY_ROOM)
					 .status(BookingStatus.CANCELED)
					 .build()));
		try {
			sut.validate(booking);
		} catch (BookingNotAvailableException e) {
			Assert.fail(e.getMessage());
		}
    }

    @Test
    public void shouldNotCollideWithDifferentPeriod() throws Exception {
        //given
		Booking booking = bookingBuilder()
					.checkIn(LocalDate.parse("2017-01-01"))
					.checkOut(LocalDate.parse("2017-01-10"))
					.room(ANY_ROOM)
					.status(BookingStatus.ACTIVE)
					.build();

		 when(bookingRepositoryMock.findByRoomId(ANY_ROOM.getId())).thenReturn(Arrays.asList(
				 bookingBuilder()
					 .checkIn(LocalDate.parse("2017-01-11"))
					 .checkOut(LocalDate.parse("2017-02-01"))
					 .room(ANY_ROOM)
					 .status(BookingStatus.CANCELED)
					 .build()));
		try {
			sut.validate(booking);
		} catch (BookingNotAvailableException e) {
			Assert.fail(e.getMessage());
		}
    }
    
}
