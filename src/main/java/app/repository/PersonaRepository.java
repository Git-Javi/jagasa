package app.repository;

import app.api.dto.PersonaDTO;
import app.model.entity.Persona;

//import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository { // extends JpaRepository<PersonaDTORepo, Long>

	PersonaDTO pintaLog(Persona unaPersona);
	
}
