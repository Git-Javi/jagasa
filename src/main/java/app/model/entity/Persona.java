package app.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "PERSONA")
public class Persona {

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	private String nombre;

	@NotBlank
	private String tlf;

}
