package app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.api.dto.PersonaDto;
import app.service.PersonaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PersonaController {

	@Autowired
	private PersonaService personaService;

	@RequestMapping("/persona")
	public PersonaDto createPersona(@RequestBody PersonaDto persona) {

		log.info("Inicio :: PersonaController.createPersona(PersonaDto): {}", persona);

		PersonaDto result = personaService.createPersona(persona);
		
		log.info("Fin :: PersonaController.createPersona(PersonaDto): {}", result);

		return result;
	}

	@GetMapping("/personas")
	public List<PersonaDto> showPersonas() {

		List<PersonaDto> listaPersonasDTO = new ArrayList<>();

		listaPersonasDTO.addAll(personaService.showPersonas());

		for (PersonaDto p : listaPersonasDTO) {

			log.info("Persona de la lista: {}", p);
		}

		return listaPersonasDTO;
	}

	@GetMapping("/persona/{id}")
	public PersonaDto getPerson(@PathVariable("id") Long id) {

		return personaService.findPersonaPorId(id);
	}

}
