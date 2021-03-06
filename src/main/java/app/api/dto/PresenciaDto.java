package app.api.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

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
public class PresenciaDto {

	@ApiModelProperty(value = "El id de la Presencia", required = false, accessMode = AccessMode.READ_ONLY, position = 1)
	private Long id;
	
	@PastOrPresent(message = "El instante de inicio no puede ser futuro")
	@NotNull
	@ApiModelProperty(value = "La hora de incio de la Presencia", required = true, accessMode = AccessMode.READ_ONLY, position = 2)
	private LocalDateTime inicio;
	
	@PastOrPresent(message = "El instante de inicio no puede ser futuro")
	@ApiModelProperty(value = "La hora de fin de la Presencia", required = true, accessMode = AccessMode.READ_ONLY, position = 3)
	private LocalDateTime fin;
	
	@NotNull
	@ApiModelProperty(value = "La Persona que ha registrado la Presencia", required = true, accessMode = AccessMode.READ_ONLY, position = 4)
	private PersonaDto persona;
	
	@NotNull
	@ApiModelProperty(value = "El el Tipo de la Presencia", required = true, accessMode = AccessMode.READ_ONLY, position = 5)
	private TipoPresenciaDto tipoPresencia;
}
