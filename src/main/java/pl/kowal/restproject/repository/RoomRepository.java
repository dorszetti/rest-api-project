package pl.kowal.restproject.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.kowal.restproject.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	@Query("select r from Room r inner join r.availabilityPeriods a join r.hotel h where" 
			+ " a.startDate <= :startDate and a.endDate >= :endDate"
			+ " and h.city = :city"
			+ " and r.price >= :minPrice" + " and r.price <= :maxPrice"
			+ " AND NOT EXISTS (select b from Booking b where b.room = r and b.checkIn < :endDate and b.checkOut > :startDate and status = 'ACTIVE') ")
	public List<Room> findByPriceAndCity(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
			@Param("city") String city, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

}
