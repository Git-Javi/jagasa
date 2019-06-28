package app.service;

import org.springframework.stereotype.Service;

import app.api.dto.PersonaDTO;


@Service
public class PersonaServiceImplementation implements PersonaService {

	@Override
	public PersonaDTO getPersonaDTO(PersonaDTO unaPersonaDTO) {

		return unaPersonaDTO;
	}

}
