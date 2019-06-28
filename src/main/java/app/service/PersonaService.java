package app.service;

import app.api.dto.PersonaDTO;
import app.model.entity.Persona;

public interface PersonaService {

	PersonaDTO getPersonaDTO(PersonaDTO unaPersonaDTO);
	
	Persona getPersona(PersonaDTO unaPersonaDTO);
}
