package app.service;

import java.util.List;

import app.api.dto.PersonaDTO;

public interface PersonaService {

	PersonaDTO createPersona(PersonaDTO persona);
	
	List<PersonaDTO> showPersonas();
	
}
