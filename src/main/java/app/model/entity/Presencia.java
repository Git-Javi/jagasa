package app.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "PRESENCIA")
@NoArgsConstructor
@RequiredArgsConstructor
public class Presencia {

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@PastOrPresent
	@NonNull
	@Column(name = "fecha_hora_inicio_presencia", nullable = false)
	private LocalDateTime fechaHoraIncioPresencia;
	
	@PastOrPresent
	@NonNull
	@Column(name = "fecha_hora_fin_presencia")
	private LocalDateTime fechaHoraFinPresencia;
	
	@ManyToOne//(cascade = CascadeType.ALL, orphanRemoval = true) // Si lo dejo salta el erro en controller de persona con los PUT y PATCH -> A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: app.model.entity.Persona.presencias; nested exception is org.hibernate.HibernateException: A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: app.model.entity.Persona.presencias
	@JoinColumn(name = "id_persona", nullable = false)
	private Persona persona;
	
	@OneToOne
	@JoinColumn(name = "id_tipo_presencia", nullable = false)
	private TipoPresencia tipoPresencia;
}
