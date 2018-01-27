package pl.kowal.restproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Client {

	@Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    @Size(max = 64)
    private String firstName;
    
    @NotNull
    @Column(name = "last_name", nullable = false)
    @Size(max = 64)
    private String lastName;

}
