package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.api.dto.PersonaDTO;
import app.repository.PersonaRepository;
import app.service.PersonaService;

@RestController
public class PersonaController {
	
	@Autowired
	private PersonaService ps;
	
	@Autowired
	private PersonaRepository pr;
	
	

	@RequestMapping("/persona")
	public PersonaDTO getPersona(@RequestBody PersonaDTO unaPersona) {
		
		System.out.println("\n\n" + unaPersona + "\n");
		
		pr.pintaLog(ps.getPersona(unaPersona));
		
		return ps.getPersonaDTO(unaPersona);
		
	}

}
