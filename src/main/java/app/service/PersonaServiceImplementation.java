package app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.api.dto.PersonaDTO;
import app.mapper.PersonaMapper;
import app.model.entity.Persona;
import app.repository.PersonaRepository;

@Service
public class PersonaServiceImplementation implements PersonaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonaServiceImplementation.class);

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PersonaMapper personaMapper;

	@Override
	public PersonaDTO createPersona(PersonaDTO persona) {
		
		LOGGER.info("Inicio :: PersonaService.createPersona(PersonaDTO): {}", persona);

		Persona personaRequest = personaMapper.personaDTOtoPersona(persona);
		LOGGER.info("Request :: PersonaService.createPersona(PersonaDTO): {}", personaRequest);

		Persona personaResponse = personaRepository.save(personaRequest);
		LOGGER.info("Response :: PersonaService.createPersona(PersonaDTO): {}", personaResponse);

		PersonaDTO result = personaMapper.personaToPersonaDTO(personaResponse);
		LOGGER.info("Fin :: PersonaService.createPersona(PersonaDTO): {}", result);

		return result;
	}

	@Override
	public List<PersonaDTO> showPersonas() {

		List<PersonaDTO> listaPersonasDTO = new ArrayList<>();
		List<Persona> listaPersonas = new ArrayList<>();

		listaPersonas.addAll((List<Persona>) personaRepository.findAll());

		for (Persona p : listaPersonas) {
			
			PersonaDTO pDTO = personaMapper.personaToPersonaDTO(p);
			listaPersonasDTO.add(pDTO);
		}

		return listaPersonasDTO;
	}

	@Override
	public PersonaDTO findPersonaPorId(Long id) {

		Persona p = personaRepository.findById(id).get();

		PersonaDTO pDTO = personaMapper.personaToPersonaDTO(p);

		return pDTO;
	}

}
