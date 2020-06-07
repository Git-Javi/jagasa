package app.mapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.validation.annotation.Validated;

import app.api.dto.PersonaDto;
import app.model.entity.Persona;

@Validated
@Mapper
public interface PersonaMapper {

	PersonaDto personaToPersonaDto (@Valid @NotNull Persona persona);
	
	Persona personaDtoToPersona (@Valid @NotNull PersonaDto personaDto);
	
	@Mapping(source = "personaId", target = "id")
	Persona mergePersonaIdAndPersonaDtoToPersona (@NotNull @Positive Long personaId, @Valid @NotNull PersonaDto personaDTo);
	
}
