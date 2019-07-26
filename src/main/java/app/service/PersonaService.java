package app.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import app.api.dto.PersonaDto;

public interface PersonaService {

	PersonaDto createPersona(@Valid @NotNull PersonaDto persona);
	
	List<PersonaDto> findPersonas();
	
	PersonaDto findPersonaById(@NotNull @Positive Long id);
	
	PersonaDto updatePersonaById(@NotNull @Positive Long id, @Valid @NotNull PersonaDto persona);
	
	void deletePersonaById(@NotNull @Positive Long id);
	
}
