package app.service;

import org.springframework.stereotype.Service;

import app.api.dto.PersonaDTO;
import app.model.entity.Persona;


@Service
public class PersonaServiceImplementation implements PersonaService {

	@Override
	public PersonaDTO getPersonaDTO(PersonaDTO unaPersonaDTO) {

		return unaPersonaDTO;
	}

	@Override
	public Persona getPersona(PersonaDTO unaPersonaDTO) {
		
		Persona unaPersona = new Persona(
				unaPersonaDTO.getId(),
				unaPersonaDTO.getNombre(),
				unaPersonaDTO.getTlf());
		
		return unaPersona;
	}

}
