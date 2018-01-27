package pl.kowal.restproject.service;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import pl.kowal.restproject.domain.Availability;
import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.domain.BookingStatus;
import pl.kowal.restproject.domain.Room;

public class DomainBuildersUtil {

	@Builder(builderMethodName = "roomBuilder")
	static Room createRoom(Long id, List<Availability> availabilityPeriods) {
		Room room = new Room();
		room.setAvailabilityPeriods(availabilityPeriods);
		room.setId(id);
		return room;
	}

	@Builder(builderMethodName = "bookingBuilder")
	static Booking createBooking(Long id, LocalDate checkIn, LocalDate checkOut, Room room, BookingStatus status) {
		Booking booking = new Booking();
		booking.setId(id);
		booking.setRoom(room);
		booking.setCheckIn(checkIn);
		booking.setCheckOut(checkOut);
		booking.setStatus(status);
		return booking;
	}

	@Builder(builderMethodName = "availabilityBuilder")
	static Availability createAvailability(Long id, LocalDate startDate, LocalDate endDate) {
		Availability availability = new Availability();
		availability.setId(id);
		availability.setStartDate(startDate);
		availability.setEndDate(endDate);
		return availability;
	}
}
