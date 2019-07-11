package app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.api.dto.PersonaDto;
import app.service.PersonaService;

@RestController
public class PersonaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonaController.class);

	@Autowired
	private PersonaService personaService;

	@RequestMapping("/persona")
	public PersonaDto createPersona(@RequestBody PersonaDto persona) {

		LOGGER.info("Inicio :: PersonaController.createPersona(PersonaDto): {}", persona);

		PersonaDto result = personaService.createPersona(persona);
		
		LOGGER.info("Fin :: PersonaController.createPersona(PersonaDto): {}", result);

		return result;
	}

	@GetMapping("/personas")
	public List<PersonaDto> showPersonas() {

		List<PersonaDto> listaPersonasDTO = new ArrayList<>();

		listaPersonasDTO.addAll(personaService.showPersonas());

		for (PersonaDto p : listaPersonasDTO) {

			LOGGER.info("Persona de la lista: {}", p);
		}

		return listaPersonasDTO;
	}

	@GetMapping("/persona/{id}")
	public PersonaDto getPerson(@PathVariable("id") Long id) {

		return personaService.findPersonaPorId(id);
	}

}
