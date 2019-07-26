package app.api.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import app.annotation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Detalles sobre la entidad: ")
public class PersonaDto {
	
	@ApiModelProperty(value = "El id de la Persona", required = false, accessMode = AccessMode.READ_ONLY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@NotBlank(message = "Debe indicar el nombre de la persona.")
	@ApiModelProperty(value = "El nombre de la Persona", required = true)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Phone
	@NotBlank(message = "Debe indicar el teléfono de la persona.")
	@ApiModelProperty(value = "El teléfono de la Persona", required = true)
	@Column(name = "telefono", nullable = false)
	private String telefono;
		
}
