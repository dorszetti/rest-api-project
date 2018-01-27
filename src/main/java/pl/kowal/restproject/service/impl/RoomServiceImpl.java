package pl.kowal.restproject.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;
import pl.kowal.restproject.domain.Room;
import pl.kowal.restproject.dto.RoomSearchDto;
import pl.kowal.restproject.repository.RoomRepository;
import pl.kowal.restproject.service.RoomService;

@Service
@Validated
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;

    @Inject
    public RoomServiceImpl(final RoomRepository repository) {
        this.repository = repository;
    }

	@Override
    @Transactional(readOnly = true)
	public List<Room> getRoomsInDateAndPriceRange(RoomSearchDto roomSearch) {
		log.debug("Retrieving the list of rooms in dates, price range and city");
		List<Room> availableRooms = repository.findByPriceAndCity(roomSearch.getStartDate(), roomSearch.getEndDate(),
				roomSearch.getCity(), roomSearch.getMinPrice(), roomSearch.getMaxPrice());
		return availableRooms;
	}

}
