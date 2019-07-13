package app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.api.dto.PersonaDto;
import app.mapper.PersonaMapper;
import app.model.entity.Persona;
import app.repository.PersonaRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PersonaMapper personaMapper;

	@Override
	public PersonaDto createPersona(PersonaDto persona) {
		
		log.info("Inicio :: PersonaService.createPersona(PersonaDto): {}", persona);

		Persona personaRequest = personaMapper.personaDtoToPersona(persona);
		log.info("Request :: PersonaService.createPersona(PersonaDto): {}", personaRequest);

		Persona personaResponse = personaRepository.save(personaRequest);
		log.info("Response :: PersonaService.createPersona(PersonaDto): {}", personaResponse);

		PersonaDto result = personaMapper.personaToPersonaDto(personaResponse);
		log.info("Fin :: PersonaService.createPersona(PersonaDto): {}", result);

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
