package app.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import app.annotation.constraint.Dni;
import app.annotation.constraint.Phone;
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

	@ApiModelProperty(value = "El id de la Persona", required = false, accessMode = AccessMode.READ_ONLY, position = 1)
	private Long id;

	@Dni
	@NotBlank(message = "Debe indicar el DNI de la persona.")
	@NotNull
	@ApiModelProperty(value = "El DNI de la Persona", required = true, position = 2)
	private String dni;

	@NotBlank(message = "Debe indicar el nombre de la persona.")
	@NotNull
	@ApiModelProperty(value = "El nombre de la Persona", required = true, position = 3)
	private String nombre;

	@Phone
	@NotBlank(message = "Debe indicar el teléfono de la persona.")
	@NotNull
	@ApiModelProperty(value = "El teléfono de la Persona", required = true, position = 4)
	private String telefono;

}
