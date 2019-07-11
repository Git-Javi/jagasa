package app.service;

import java.util.List;

import app.api.dto.PersonaDto;

public interface PersonaService {

	PersonaDto createPersona(PersonaDto persona);
	
	List<PersonaDto> showPersonas();
	
	PersonaDto findPersonaPorId(Long id);
	
}
