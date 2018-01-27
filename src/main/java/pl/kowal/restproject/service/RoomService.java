package pl.kowal.restproject.service;

import java.util.List;

import pl.kowal.restproject.domain.Room;
import pl.kowal.restproject.dto.RoomSearchDto;

public interface RoomService {

    List<Room> getRoomsInDateAndPriceRange(RoomSearchDto roomSearch);

}
