package app.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.Application;
import app.api.dto.PersonaDto;
import app.utils.MockMVCUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
public class PersonaControllerServerTest {

	private MockMvc mockMvc;

	@Autowired
	private MockMVCUtils mMvcU;

	@Autowired
	private PersonaController personaController;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(personaController).build();
	}

	// ---------------------------------------GET(All)---------------------------------------

	@Test
	public void metodo01GetDebeResponderOkYJsonVacioTest() throws Exception {

		String responseBody = mMvcU.controllerResponseTesterNoPayloadUtil(mockMvc, get("/api/persona"), HttpStatus.OK);
		assertEquals("[]", responseBody);
	}

	// ---------------------------------------POST---------------------------------------

	@Test
	public void metodo02PostConIdNullDebeResponderCreatedConIdAutogeneradoTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(null, "PostTest", "666777888");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.CREATED);

		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "PostTest", "666777888"), personaResponse);
	}

	@Test
	public void metodo03PostConNombreVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "", "666777888");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo04PostConNombreNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "666777888");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo05PostConTelefonoNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PostTest", null);
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo06PostConTelefonoVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PostTest", "");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo07PostConFormatoTelefonoIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PostTest", "123456");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	// ---------------------------------------GET(ById)---------------------------------------

	@Test
	public void metodo08GetConIdDebeResponderOKTest() throws Exception {

		String responseBody = mMvcU.controllerResponseTesterNoPayloadUtil(mockMvc, get("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "PostTest", "666777888"), personaResponse);
	}

	@Test
	public void metodo09GetConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		mMvcU.controllerResponseTesterNoPayloadUtil(mockMvc, get("/api/persona/5"), HttpStatus.NOT_FOUND);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo10GetConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		mMvcU.controllerResponseTesterNoPayloadUtil(mockMvc, get("/api/persona/0"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ---------------------------------------PUT---------------------------------------

	@Test
	public void metodo11PutConIdCorrectoYPayloadCorrectoDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PutTest", "999888777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(personaRequest, personaResponse);
	}

	@Test
	public void metodo12PutConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PutTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/5"), HttpStatus.NOT_FOUND);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo13PutConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PutTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/0"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void metodo14PutNoTieneEnCuentaIdPayloadDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(3L, "PutTest", "999888777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "PutTest", "999888777"), personaResponse);
	}

	@Test
	public void metodo15PutConNombreVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo16PutConNombreNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo17PutConTelefonoVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PutTest", "");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo18PutConTelefonoNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PutTest", null);
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo19PutConFormatoTelefonoIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PutTest", "123456");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	// ---------------------------------------PATCH---------------------------------------

	@Test
	public void metodo20PatchConIdCorrectoYPayloadCorrectoDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "999888777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "PatchTest", "999888777"), personaResponse);
	}

	@Test
	public void metodo21PatchNoTieneEnCuentaIdPayloadDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(3L, "PatchTest", "777777777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "PatchTest", "777777777"), personaResponse);
	}

	@Test
	public void metodo22PatchConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/5"), HttpStatus.NOT_FOUND);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo23PatchConIdIncorrectoeDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/0"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo24PatchConNombreVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "", "777777777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo25PatchConNombreNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "777777777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo26PatchConTelefonoVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo27PatchConTelefonoNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", null);
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo28PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, "PatchTest", "123456");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void metodo29PatchActualizaSoloNombreDebeResponderOKTest() throws Exception {

		String jsonString = "{\"nombre\":\"SoloNombrePatch\"}";
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, jsonString, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "SoloNombrePatch", "777777777"), personaResponse);
	}

	@Test
	public void metodo30PatchActualizaSoloTelefonoDebeResponderOKTest() throws Exception {

		String jsonString = "{\"telefono\":\"666777888\"}";
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, jsonString, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "SoloNombrePatch", "666777888"), personaResponse);
	}

	// ---------------------------------------DELETE---------------------------------------

	@Test
	public void metodo31DeleteDebeResponderNoContentTest() throws Exception {
		mockMvc.perform(delete("/api/persona/1")).andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	public void metodo32DeleteConIdInexistenteDebeResponderNotFoundTest() throws Exception {
		mockMvc.perform(delete("/api/persona/1")).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo33DeleteConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {
		mockMvc.perform(delete("/api/persona/0")).andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
}

//[ERROR] Errors: 
//[ERROR] PersonaControllerServerTest.metodo28PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest:314 » NestedServlet
//[ERROR] Tests run: 28, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 3.2 s <<< FAILURE! - in app.controller.PersonaControllerServerTest
//[ERROR] metodo28PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest(app.controller.PersonaControllerServerTest)  Time elapsed: 0.029 s  <<< ERROR!
//org.springframework.web.util.NestedServletException: Request processing failed; nested exception is javax.validation.ConstraintViolationException: mergePersonaIdAndPersonaDtoToPersona.personaDTo.telefono: El número de teléfono no cumple las características de un teléfono de España
//at app.controller.PersonaControllerServerTest.metodo28PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest(PersonaControllerServerTest.java:314)
//Caused by: javax.validation.ConstraintViolationException: mergePersonaIdAndPersonaDtoToPersona.personaDTo.telefono: El número de teléfono no cumple las características de un teléfono de España
//at app.controller.PersonaControllerServerTest.metodo28PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest(PersonaControllerServerTest.java:314)
