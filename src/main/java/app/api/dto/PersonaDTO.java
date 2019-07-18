package app.api.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDto {


	@ApiModelProperty(value = "El id de la Persona", required = false, accessMode = AccessMode.READ_ONLY)
	private Long id;
	
	@NotBlank
	@ApiModelProperty(value = "El nombre de la Persona", required = true)
	private String nombre;
	
	@NotBlank
	@ApiModelProperty(value = "El teléfono de la Persona", required = true)
	private String telefono;
		
}
