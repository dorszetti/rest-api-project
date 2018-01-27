package pl.kowal.restproject.service;

import static org.mockito.Mockito.when;
import static pl.kowal.restproject.service.DomainBuildersUtil.bookingBuilder;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.domain.BookingStatus;
import pl.kowal.restproject.exception.BookingAlreadyCanceledException;
import pl.kowal.restproject.exception.BookingNotAvailableException;
import pl.kowal.restproject.exception.ObjectNotFoundException;
import pl.kowal.restproject.repository.BookingRepository;
import pl.kowal.restproject.service.impl.BookingCancelService;

@RunWith(MockitoJUnitRunner.class)
public class BookingCancelServiceTest {

	BookingCancelService sut;

	@Mock
	private BookingRepository bookingRepositoryMock;

    @Before
    public void setUp() throws Exception {
        sut = new BookingCancelService(bookingRepositoryMock);
    }

    @Test(expected = BookingAlreadyCanceledException.class)
    public void shoulNotCancelIfAlreadyCanceled() throws Exception {
    	Long bookingId = 1L;
    	Booking booking = bookingBuilder()
			 	.checkIn(LocalDate.parse("2017-01-01"))
			 	.checkOut(LocalDate.parse("2017-02-01"))
			 	.status(BookingStatus.CANCELED)
			 	.build();
    	
		 when(bookingRepositoryMock.findOne(bookingId)).thenReturn(booking);
		 when(bookingRepositoryMock.save(booking)).thenReturn(booking);

		sut.cancel(bookingId);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void shouldNotCancelIfNotFound() throws Exception {
    	Long bookingId = 1L;
		when(bookingRepositoryMock.findOne(bookingId)).thenReturn(null);
		sut.cancel(bookingId);
    }

    @Test
    public void shouldCancelifActive() throws Exception {
    	Long bookingId = 1L;
    	Booking booking = bookingBuilder()
    			.id(bookingId)
			 	.checkIn(LocalDate.parse("2017-01-01"))
			 	.checkOut(LocalDate.parse("2017-02-01"))
			 	.status(BookingStatus.ACTIVE)
			 	.build();
    	
		when(bookingRepositoryMock.findOne(bookingId)).thenReturn(booking);
		when(bookingRepositoryMock.save(booking)).thenReturn(booking);
		try {
			Booking ret = sut.cancel(bookingId);
			Assert.assertEquals("Returning entity saved by repo", bookingId, ret.getId());
		} catch (BookingNotAvailableException e) {
			Assert.fail(e.getMessage());
		}
    }

}
