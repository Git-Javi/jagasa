package app.model.entity;

import javax.persistence.Column;
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
	@Column(name = "id", nullable = false)
	private Long id;

	@NotBlank
	@NonNull
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@NotBlank
	@NonNull
	@Column(name = "telefono", nullable = false)
	private String telefono;

}
