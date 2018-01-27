package pl.kowal.restproject.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.kowal.restproject.domain.Room;
import pl.kowal.restproject.dto.RoomSearchDto;
import pl.kowal.restproject.service.RoomService;

@RestController
@Slf4j
public class RoomController extends AbstractController {

	private final RoomService roomService;

	@Inject
	public RoomController(final RoomService roomService) {
		this.roomService = roomService;
	}

	@RequestMapping(value = "/room", method = RequestMethod.GET)
	public List<Room> listRooms(@Valid RoomSearchDto roomSearch) {
		log.debug("Searching for rooms in given period, price-range and city");
		return roomService.getRoomsInDateAndPriceRange(roomSearch);
	}

}
