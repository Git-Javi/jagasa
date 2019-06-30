//package app.repository;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import app.api.dto.PersonaDTO;
//import app.controller.PersonaController;
//import app.model.entity.Persona;
//
//@Service
//public class PersonaRepositoryImplementacion implements PersonaRepository {
//
//	private static Logger log = LogManager.getLogger(PersonaController.class);
//
//	@Override
//	public PersonaDTO pintaLog(Persona unaPersona) {
//
//		log.info(unaPersona.toString());
//
//		PersonaDTO unaPersonaDTO = new PersonaDTO(unaPersona.getId(), unaPersona.getNombre(), unaPersona.getTlf());
//
//		return unaPersonaDTO;
//	}
//
//}
