package app.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "PERSONA")
@NoArgsConstructor
@RequiredArgsConstructor
public class Persona {

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@NonNull
	private String nombre;

	@NotBlank
	@NonNull
	private String telefono;

}
