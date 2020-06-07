package app.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class PhoneValidatorTest {

	@Autowired
	PersonaService personaService;

	@Autowired
	AnnotationServiceImpl annotationServiceImpl;

	PersonaDto personaTest = new PersonaDto();

	@Test (expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoMenosOchoDígitos() {

		String telefono = "7654321";
		annotationServiceImpl.telefonoTester(telefono);
	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoMasNueveDígitos() {

		String telefono = "9876543210";
		annotationServiceImpl.telefonoTester(telefono);
	}
	
	@Test
	public void comprobarConstraintTelefonoPermiteNull() {

		String telefonoTest = null;
		String telefono = annotationServiceImpl.telefonoTester(telefonoTest);
		assertNull("El teléfono devuelto ha de ser null", telefono);
	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoIntroducirValoresDistintosANumeros() {

		String telefono = "abcde678";
		annotationServiceImpl.telefonoTester(telefono);
	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoIntroducirNumeroQueEmpiecePorNumeroDistintoDeSeisSieteOchoNueve() {

		String telefono = "12345678";
		annotationServiceImpl.telefonoTester(telefono);
	}

	@Test
	public void comprobarConstraintTelefonoEjemploMovilCorrecto() {

		personaTest.setId(1l);
		personaTest.setDni("44333777E");
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("666333222");

		personaService.createPersona(personaTest);
		assertEquals("El telefono es válido y la persona lo devolverá con el get", "666333222", personaTest.getTelefono());
	}

	@Test
	public void comprobarConstraintTelefonoEjemploFijoCorrecto() {

		personaTest.setId(1l);
		personaTest.setDni("44333777E");
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("91232425");

		personaService.createPersona(personaTest);
		assertEquals("El telefono es válido y la persona lo devolverá con el get", "91232425", personaTest.getTelefono());
	}
	
}
