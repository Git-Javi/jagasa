/* Con los test en los que le pasamos un valor de 0 o negativo en el path (api/persona/0) y que vulnera la constraint @Positive que tenemos puesta 
en nuestro controller así como cualquier método que vulnere una constraint de nuestra api desde el método PATCH, nos arrojará una excepción 
NestedServletException (Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E ConstraintViolationException) y la arroja nuevamente
en algún momento, pero envuelta en una ServletException.) y que si tratamos de capturar no nos realizará el .andExpect(status().is(httpStatus.value())) 
de nuestro método utilidad correspondiente y nos dará un falso positivo ya que puede no estar arrojando el código de estado que esperamos.
Por éste motivo dichos test, se realizaran en ésta clase valiéndonos de la clase TestRestTemplate.*/

package app.controller;

import static org.junit.Assert.assertEquals;

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

import app.Application;
import app.api.dto.PersonaDto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
@EnableAutoConfiguration
public class PersonaControllerRestTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	int randomServerPort;

	@Before
	public void setup() {
		testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
	}

	// -------------------------------POST(Para crear entidad sobre la que testear)-------------------------------

	@Test
	public void metodo02PostConIdNullDebeResponderCreatedConIdAutogeneradoTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(null, "44333222J", "PostTest", "666777888");
		String URL = "http://localhost:" + randomServerPort + "/api/persona";

		ResponseEntity<PersonaDto> response = testRestTemplate.postForEntity(URL, personaRequest, PersonaDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(new PersonaDto(1L, "44333222J", "PostTest", "666777888"), response.getBody());
	}

	// ---------------------------------------GET(ById)---------------------------------------

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo13GetConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		ResponseEntity<String> response = testRestTemplate.getForEntity(URL, String.class);
		assertEquals(response.getStatusCode(), (HttpStatus.INTERNAL_SERVER_ERROR));
	}

	// ---------------------------------------PUT---------------------------------------

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo16PutConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "44333222J", "PutTest", "999888777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	// ---------------------------------------PATCH---------------------------------------

	// https://stackoverflow.com/questions/29447382/resttemplate-patch-request

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo23PatchConIdIncorrectoeDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "44333222J", "PatchTest", "999888777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo24PatchConDniVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "", "PatchTest", "777777777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo25PatchConDniNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "PatchTest", "777777777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo26PatchConFormatoDniIncorrectoDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "123555J", "PatchTest", "777777777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo27PatchConNombreVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "44333222J", "", "777777777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo28PatchConNombreNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "44333222J", null, "777777777");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo29PatchConTelefonoVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "44333222J", "PatchTest", "");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo30PatchConTelefonoNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "44333222J", "PatchTest", null);
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo31PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "44333222J", "PatchTest", "123456");
		String URL = "http://localhost:" + randomServerPort + "/api/persona/1";

		HttpEntity<PersonaDto> entity = new HttpEntity<>(personaRequest);
		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.PATCH, entity, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	// ---------------------------------------DELETE---------------------------------------

	@Test // (expected = ConstraintViolationException.class) // Genera java.lang.AssertionError
	public void metodo37DeleteConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		String URL = "http://localhost:" + randomServerPort + "/api/persona/0";

		ResponseEntity<String> response = testRestTemplate.exchange(URL, HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

}
