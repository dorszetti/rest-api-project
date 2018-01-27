package pl.kowal.restproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import pl.kowal.restproject.domain.Booking;
import pl.kowal.restproject.domain.BookingStatus;
import pl.kowal.restproject.dto.BookingDto;
import pl.kowal.restproject.dto.BookingRequestDto;
import pl.kowal.restproject.repository.ClientRepository;
import pl.kowal.restproject.repository.RoomRepository;

@Service
class BookingTransformer {

	private final RoomRepository roomRepository;
	
	private final ClientRepository clientRepository;

	@Inject
	BookingTransformer(RoomRepository roomRepository, ClientRepository clientRepository) {
		this.roomRepository = roomRepository;
		this.clientRepository = clientRepository;
	}

	Booking toDomain(BookingDto in, Long clientId) {
		Booking out = new Booking();
		out.setId(in.getId());
		out.setCheckIn(in.getCheckIn());
		out.setCheckOut(in.getCheckOut());
		out.setStatus(in.getStatus());
		out.setRoom(roomRepository.findOne(in.getRoomId()));
		out.setClient(clientRepository.findOne(clientId));
		return out;
	}

	Booking toDomain(BookingRequestDto in, Long clientId) {
		Booking out = new Booking();
		out.setCheckIn(in.getCheckIn());
		out.setCheckOut(in.getCheckOut());
		out.setStatus(BookingStatus.ACTIVE);
		out.setRoom(roomRepository.findOne(in.getRoomId()));
		out.setClient(clientRepository.findOne(clientId));
		return out;
	}

	List<BookingDto> toDto(List<Booking> in) {
		return in.stream().map(this::toDto).collect(Collectors.toList());
	}

	BookingDto toDto(Booking in) {
		BookingDto out = new BookingDto();
		out.setId(in.getId());
		out.setCheckIn(in.getCheckIn());
		out.setCheckOut(in.getCheckOut());
		out.setStatus(in.getStatus());
		out.setRoomId(in.getRoom().getId());
		return out;
	}
}
