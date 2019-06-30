package app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.api.dto.PersonaDTO;
import app.model.entity.Persona;
import app.repository.PersonaRepository;

@Service
public class PersonaServiceImplementation implements PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	@Override
	public PersonaDTO createPersona(PersonaDTO persona) {
		System.out.println("Inicio :: PersonaService.createPersona(PersonaDTO): " + persona);

		Persona personaRequest = new Persona(persona.getNombre(), persona.getTlf());
		System.out.println("Request :: PersonaService.createPersona(PersonaDTO): " + personaRequest);

		Persona personaResponse = personaRepository.save(personaRequest);
		System.out.println("Response :: PersonaService.createPersona(PersonaDTO): " + personaResponse);

		PersonaDTO result = new PersonaDTO(personaResponse.getId(), personaResponse.getNombre(),personaResponse.getTlf());
		System.out.println("Fin :: PersonaService.createPersona(PersonaDTO): " + result);

		return result;
	}

	@Override
	public List<PersonaDTO> showPersonas() {

		List<PersonaDTO> listaPersonasDTO = new ArrayList<>();
		List<Persona> listaPersonas = new ArrayList<>();

		listaPersonas.addAll((List<Persona>) personaRepository.findAll());

		for (Persona p : listaPersonas) {

			PersonaDTO pDTO = new PersonaDTO(p.getId(), p.getNombre(), p.getTlf());
			listaPersonasDTO.add(pDTO);
		}

		return listaPersonasDTO;
	}

}
