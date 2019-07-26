package app.annotation;

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

	PersonaDto personaTest = new PersonaDto();

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoMenosOchoDígitos() {

		personaTest.setId(1l);
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("7654321");

		personaService.createPersona(personaTest);

	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoMasNueveDígitos() {

		personaTest.setId(1l);
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("9876543210");

		personaService.createPersona(personaTest);

	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoIntroducirValoresDistintosDeNumeros() {

		personaTest.setId(1l);
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("abcde678");

		personaService.createPersona(personaTest);

	}

	@Test(expected = ConstraintViolationException.class)
	public void comprobarConstraintTelefonoIntroducirNumeroQueEmpiecePorNumeroDistintoDeSeisSieteOchoNueve() {

		personaTest.setId(1l);
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("12345678");

		personaService.createPersona(personaTest);

	}

	@Test
	public void comprobarConstraintTelefonoEjemploMovilCorrecto() {

		personaTest.setId(1l);
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("666333222");

		personaService.createPersona(personaTest);

	}

	@Test
	public void comprobarConstraintTelefonoEjemploFijoCorrecto() {

		personaTest.setId(1l);
		personaTest.setNombre("Manolo");
		personaTest.setTelefono("91232425");

		personaService.createPersona(personaTest);

	}
}
