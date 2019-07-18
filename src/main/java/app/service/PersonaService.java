package app.service;

import java.util.List;

import app.api.dto.PersonaDto;

public interface PersonaService {

	PersonaDto createPersona(PersonaDto persona);
	
	List<PersonaDto> findPersonas();
	
	PersonaDto findPersonaById(Long id);
	
}
