package app.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import app.api.dto.PersonaDto;
import app.mapper.PersonaMapper;
import app.model.entity.Persona;
import app.repository.PersonaRepository;
import lombok.extern.slf4j.Slf4j;

@Validated
@Service
@Slf4j
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private PersonaMapper personaMapper;

	@Override
	public PersonaDto createPersona(@Valid @NotNull PersonaDto persona) {

		log.info("Inicio :: PersonaService.createPersona(PersonaDto): {}", persona);

		Persona personaRequest = personaMapper.personaDtoToPersona(persona);
		log.info("Request :: PersonaService.createPersona(PersonaDto): {}", personaRequest);

		Persona personaResponse = personaRepository.save(personaRequest);
		log.info("Response :: PersonaService.createPersona(PersonaDto): {}", personaResponse);

		@Valid
		PersonaDto result = personaMapper.personaToPersonaDto(personaResponse);
		log.info("Fin :: PersonaService.createPersona(PersonaDto): {}", result);

		return result;
	}

	@Override
	public List<PersonaDto> findPersonas() {

		List<PersonaDto> listaPersonasDto = new ArrayList<>();
		List<Persona> listaPersonas = new ArrayList<>();

		listaPersonas.addAll((List<Persona>) personaRepository.findAll());

		for (Persona p : listaPersonas) {

			@Valid
			PersonaDto pDto = personaMapper.personaToPersonaDto(p);
			listaPersonasDto.add(pDto);
		}

		return listaPersonasDto;
	}

	@Override
	public PersonaDto findPersonaById(@NotNull @Positive Long id) {

		Persona p = personaRepository.findById(id).get();

		@Valid
		PersonaDto pDto = personaMapper.personaToPersonaDto(p);
		
		log.info("Response :: La persona solicitada es: {}", pDto);

		return pDto;
	}

	@Override
	public PersonaDto updatePersonaById(@NotNull @Positive Long id, @Valid @NotNull PersonaDto persona) {

		Persona p = personaRepository.findById(id).get();
		p.setNombre(persona.getNombre());
		p.setTelefono(persona.getTelefono());
		
		personaRepository.save(p);

		@Valid
		PersonaDto pDto = personaMapper.personaToPersonaDto(p);
		
		log.info("Response :: La persona con id " +id+ " se ha actualizado a: {}", pDto);

		return pDto;
	}

	@Override
	public void deletePersonaById(@NotNull @Positive Long id) {

			personaRepository.deleteById(id);
			
	}

}
