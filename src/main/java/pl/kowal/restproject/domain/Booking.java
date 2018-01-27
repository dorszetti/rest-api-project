package pl.kowal.restproject.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
public class Booking {

	@Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "check_in", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @NotNull
    @Column(name = "check_out", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  checkOut;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_client")
	private Client client;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_room")
    private Room room;
    
    public boolean isActive() {
    	return status == BookingStatus.ACTIVE;
    }

}
