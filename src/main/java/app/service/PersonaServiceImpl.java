package app.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
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
			PersonaDto pDto = personaMapper.personaToPersonaDto(p);
			listaPersonasDto.add(pDto);
		}

		return listaPersonasDto;
	}

	@Override
	public PersonaDto findPersonaById(@NotNull @Positive Long id) {

		personaExists(id);
		PersonaDto pDto = personaFind(id);
		log.info("Response :: La persona solicitada es: {}", pDto);

		return pDto;
	}

	@Override
	public PersonaDto updatePersonaById(@NotNull @Positive Long id, @Valid @NotNull PersonaDto personaDto) {

		personaExists(id);
		Persona p = personaMapper.mergePersonaIdAndPersonaDtoToPersona(id, personaDto);
		Persona pSaved = personaRepository.save(p);
		PersonaDto pDto = personaMapper.personaToPersonaDto(pSaved);
		log.info("Response :: La persona con id " + id + " se ha actualizado a: {}", pDto);

		return pDto;

	}

	@Override
	public void deletePersonaById(@NotNull @Positive Long id) {

		personaExists(id);
		personaRepository.deleteById(id);

	}

	@Override
	public PersonaDto updatePersonaFieldsById(@NotNull @Positive Long id, Map<String, Object> fields) {

		personaExists(id);
		PersonaDto pDto = personaFind(id);

		fields.forEach((k, v) -> {
			Field field = ReflectionUtils.findRequiredField(PersonaDto.class, k);
			ReflectionUtils.setField(field, pDto, v);
		});

		PersonaDto personaDtoDevuelta = personaSave(pDto);

		return personaDtoDevuelta;

	}

	@Override
	public void personaExists(@NotNull @Positive Long id) {

		if (!personaRepository.existsById(id)) {
			throw new RuntimeException("No se ha encontrado ese id");
		}

	}

	@Override
	public PersonaDto personaFind(@NotNull @Positive Long id) {

		Persona persona = personaRepository.findById(id).get();
		PersonaDto personaDto = personaMapper.personaToPersonaDto(persona);

		return personaDto;

	}

	@Override
	public PersonaDto personaSave(@Valid @NotNull PersonaDto personaDto) {

		Persona persona = personaMapper.personaDtoToPersona(personaDto);
		Persona personaSaved = personaRepository.save(persona);
		PersonaDto personaDtoDevuelta = personaMapper.personaToPersonaDto(personaSaved);

		return personaDtoDevuelta;

	}

}
