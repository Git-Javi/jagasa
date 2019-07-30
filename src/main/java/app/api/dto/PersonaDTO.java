package app.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import app.annotation.contraint.Phone;
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
	private Long id;
	
	@NotBlank(message = "Debe indicar el nombre de la persona.")
	@NotNull
	@ApiModelProperty(value = "El nombre de la Persona", required = true)
	private String nombre;
	
	@Phone
	@NotBlank(message = "Debe indicar el teléfono de la persona.")
	@NotNull
	@ApiModelProperty(value = "El teléfono de la Persona", required = true)
	private String telefono;
		
}
