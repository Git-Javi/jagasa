package app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.api.dto.PersonaDto;
import app.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(tags = "Controller")
public class PersonaController {

	@Autowired
	private PersonaService personaService;

	@ApiOperation("Crea una persona")
	@RequestMapping("/persona")
	public PersonaDto createPersona(@RequestBody @NotNull PersonaDto persona) {

		log.info("Inicio :: PersonaController.createPersona(PersonaDto): {}", persona);

		PersonaDto result = personaService.createPersona(persona);
		
		log.info("Fin :: PersonaController.createPersona(PersonaDto): {}", result);

		return result;
	}

	@ApiOperation("Muestra las personas")
	@GetMapping("/persona")
	public List<PersonaDto> showPersonas() {

		List<PersonaDto> listaPersonasDto = new ArrayList<>();

		listaPersonasDto.addAll(personaService.findPersonas());

		for (PersonaDto p : listaPersonasDto) {

			log.info("Persona de la lista: {}", p);
		}

		return listaPersonasDto;
	}

	@ApiOperation("Muestra una persona por id")
	@GetMapping("/persona/{id}")
	public PersonaDto getPersona(@PathVariable("id") @NotNull Long id) {

		return personaService.findPersonaById(id);
	}

}
