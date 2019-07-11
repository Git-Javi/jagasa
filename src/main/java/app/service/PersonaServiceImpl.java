package app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.api.dto.PersonaDto;
import app.mapper.PersonaMapper;
import app.model.entity.Persona;
import app.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonaServiceImpl.class);

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PersonaMapper personaMapper;

	@Override
	public PersonaDto createPersona(PersonaDto persona) {
		
		LOGGER.info("Inicio :: PersonaService.createPersona(PersonaDto): {}", persona);

		Persona personaRequest = personaMapper.personaDtoToPersona(persona);
		LOGGER.info("Request :: PersonaService.createPersona(PersonaDto): {}", personaRequest);

		Persona personaResponse = personaRepository.save(personaRequest);
		LOGGER.info("Response :: PersonaService.createPersona(PersonaDto): {}", personaResponse);

		PersonaDto result = personaMapper.personaToPersonaDto(personaResponse);
		LOGGER.info("Fin :: PersonaService.createPersona(PersonaDto): {}", result);

		return result;
	}

	@Override
	public List<PersonaDto> showPersonas() {

		List<PersonaDto> listaPersonasDTO = new ArrayList<>();
		List<Persona> listaPersonas = new ArrayList<>();

		listaPersonas.addAll((List<Persona>) personaRepository.findAll());

		for (Persona p : listaPersonas) {
			
			PersonaDto pDTO = personaMapper.personaToPersonaDto(p);
			listaPersonasDTO.add(pDTO);
		}

		return listaPersonasDTO;
	}

	@Override
	public PersonaDto findPersonaPorId(Long id) {

		Persona p = personaRepository.findById(id).get();

		PersonaDto pDTO = personaMapper.personaToPersonaDto(p);

		return pDTO;
	}

}
