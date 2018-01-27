package pl.kowal.restproject.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/*
 * Technical availability period theoretically set by hotel owner
 * Assignment was pretty vague about it, so I'm not sure if it 
 * is actually necessary
 */
@Entity
@Data
public class Availability {

	@Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  endDate;

    @ManyToOne
	@JoinColumn(name = "fk_room")
    @JsonBackReference
	private Room room;
}
