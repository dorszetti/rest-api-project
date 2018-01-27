package pl.kowal.restproject.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.kowal.restproject.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select b from Booking b where b.room.id = :roomId")
	List<Booking> findByRoomId(@Param("roomId") Long roomId);

	List<Booking> findByClientId(Long clientId);
}
