package app.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonaDto {

	@ApiModelProperty(value = "El id de la Persona", required = false)
	private Long id;
	
	@ApiModelProperty(value = "El nombre de la Persona", required = true)
	private String nombre;
	
	@ApiModelProperty(value = "El teléfono de la Persona", required = true)
	private String tlf;
		
}
