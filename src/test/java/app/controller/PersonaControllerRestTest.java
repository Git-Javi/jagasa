package app.controller;

import static org.junit.Assert.assertEquals;

//import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.Application;
import app.api.dto.PersonaDto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
@EnableAutoConfiguration
public class PersonaControllerRestTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@LocalServerPort
	int randomServerPort;

	@Before
	public void setup() {
		testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	}

	// -------------------------------POST(Para crear entidad sobre la que testear)-------------------------------

	@Test
	public void metodo02PostConIdNullDebeResponderCreatedConIdAutogeneradoTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(null, "PostTest", "666777888");
		String URL = "http://localhost:" + randomServerPort + "/api/persona";

		ResponseEntity<String> response = testRestTemplate.postForEntity(URL, personaRequest, String.class);
		PersonaDto personaResponse = objectMapper.readValue(response.getBody(), PersonaDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(new PersonaDto(1L, "PostTest", "666777888"), personaResponse);
	}

	// ---------------------------------------GET(ById)---------------------------------------

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo10GetConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		ResponseEntity<String> response = testRestTemplate.getForEntity(URL, String.class);
		assertEquals(response.getStatusCode(), (HttpStatus.INTERNAL_SERVER_ERROR));
	}

	// ---------------------------------------PUT---------------------------------------

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo13PutConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PutTest", "999888777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	// ---------------------------------------PATCH---------------------------------------

	// https://stackoverflow.com/questions/29447382/resttemplate-patch-request

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo23PatchConIdIncorrectoeDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "999888777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo24PatchConNombreVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "", "777777777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo25PatchConNombreNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "777777777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo26PatchConTelefonoVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo27PatchConTelefonoNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", null);
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo28PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "123456");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	// ---------------------------------------DELETE---------------------------------------

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo33DeleteConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
