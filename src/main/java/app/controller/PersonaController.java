package app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.api.dto.PersonaDTO;
import app.service.PersonaService;

@RestController
public class PersonaController {

	@Autowired
	private PersonaService personaService;

	@RequestMapping("/persona")
	public PersonaDTO createPersona(@RequestBody PersonaDTO persona) {
		
		System.out.println("Inicio :: PersonaController.createPersona(PersonaDTO): " + persona);
		
		PersonaDTO result = personaService.createPersona(persona);
		System.out.println("Fin :: PersonaController.createPersona(PersonaDTO): " + result);
		
		return result;
	}
	
	@GetMapping("/personas")
	public List<PersonaDTO> showPersonas(){
		
		List <PersonaDTO> listaPersonasDTO = new ArrayList<>();
		
		listaPersonasDTO.addAll(personaService.showPersonas());
		
		
		for (PersonaDTO p : listaPersonasDTO) {
			
			System.out.println(p);
		}
		
		
		return listaPersonasDTO;	
	}
	
	
}
