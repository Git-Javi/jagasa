package app.annotation;

import static org.junit.Assert.assertEquals;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.Application;
import app.api.dto.PersonaDto;
import app.service.PersonaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
@EnableAutoConfiguration
public class DniValidator {

	@Autowired
	PersonaService personaService;
	
	@Autowired
	AnnotationServiceImpl annotationServiceImpl;
	
	PersonaDto personaTest = new PersonaDto();

	@Test (expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteMenosNueveCaracteres() {

		String dni = "1234567A";
		annotationServiceImpl.dnitester(dni);
	}

	@Test (expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteMasNueveCaracteres() {

		String dni = "123456789A";
		annotationServiceImpl.dnitester(dni);
	}
	
	@Test (expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteNull() {

		String dni = null;
		annotationServiceImpl.dnitester(dni);
	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteIntroducirValoresDistintosANumeros() {

		String dni = "abcde678A";
		annotationServiceImpl.dnitester(dni);
	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteEmpiecePorLetra() {

		String dni = "A1234567A";
		annotationServiceImpl.dnitester(dni);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteNoTengaLetra() {

		String dni = "123456789";
		annotationServiceImpl.dnitester(dni);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteLetraMinuscula() {

		String dni = "12345678a";
		annotationServiceImpl.dnitester(dni);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteLetraI() {

		String dni = "12345678I";
		annotationServiceImpl.dnitester(dni);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteLetraÑ() {

		String dni = "12345678Ñ";
		annotationServiceImpl.dnitester(dni);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteLetraO() {

		String dni = "12345678O";
		annotationServiceImpl.dnitester(dni);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintDniNoPermiteLetraU() {

		String dni = "12345678U";
		annotationServiceImpl.dnitester(dni);
	}

	@Test
	public void comprobarConstraintDniEjemploCorrecto() {

		personaTest.setId(1l);
		personaTest.setDni("44333777E");
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("666333222");

		personaService.createPersona(personaTest);
		assertEquals("El DNI es válido y la persona lo devolverá con el get", "44333777E", personaTest.getDni());
	}
	
}
