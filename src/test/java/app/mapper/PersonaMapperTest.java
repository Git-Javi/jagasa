package app.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.Application;
import app.api.dto.PersonaDto;
import app.mapper.PersonaMapper;
import app.model.entity.Persona;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class }) //@SpringBootTest Le dice al test que cargue todo el Contexto de Spring (beans) cada vez que se ejecute esta prueba.
@EnableAutoConfiguration 
public class PersonaMapperTest {
	
	@Autowired
	PersonaMapper personaMapper;

	@Test
	public void ComprobarCamposConversionPersonaToPersonaDto() {

		Persona persona = new Persona("PepeTest", "981223344");
		
		PersonaDto pDto = personaMapper.personaToPersonaDto(persona);
		
		assertNull("El id viene vacio", pDto.getId());
		assertNotNull("El nombre apunta a null", pDto.getNombre());
		assertNotNull("El TLF apunta a null", pDto.getTelefono());
		
	}
	
	@Test
	public void ComprobarCamposConversionPersonaDtoToPersona() {

		PersonaDto personaDto = new PersonaDto(1L,"ManoloTest", "987456123");
		
		Persona persona = personaMapper.personaDtoToPersona(personaDto);
		
		assertNotNull("El id no viene vacio", persona.getId());
		assertNotNull("El nombre apunta a null", persona.getNombre());
		assertNotNull("El TLF apunta a null", persona.getTelefono());
		
	}

	
//	@Test
//	public void restarValorMenorDeberiaDevolverPositivo() {
//	assertTrue("Restar un valor menor deberia devolver un valor positivo",  (10 -5) > 0);
//	}
	
	
}
