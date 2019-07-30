package app.service;

import java.util.List;
import java.util.Map;

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
	
	PersonaDto updatePersonaFieldsById(@NotNull @Positive Long id, Map<String,Object> fields);
	
	public void personaExists(@NotNull @Positive Long id);
	
	public PersonaDto personaFind(@NotNull @Positive Long id);
	
	public PersonaDto personaSave(@Valid @NotNull PersonaDto personaDto);
	
	
}
