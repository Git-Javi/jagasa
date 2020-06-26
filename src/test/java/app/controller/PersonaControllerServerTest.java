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

		PersonaDto personaRequest = new PersonaDto(null, "44333222J", "PostTest", "666777888");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.CREATED);

		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L, "44333222J", "PostTest", "666777888"), personaResponse);
	}
	
	@Test
	public void metodo03PostConDniNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "PostTest", "666777888");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo04PostConDniVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"", "PostTest", "");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo05PostConFormatoDniIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"4321B", "PostTest", "123456");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo06PostConNombreVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "", "666777888");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo07PostConNombreNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", null, "666777888");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo08PostConTelefonoVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PostTest", "");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void metodo09PostConTelefonoNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PostTest", null);
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo10PostConFormatoTelefonoIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PostTest", "123456");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, post("/api/persona"), HttpStatus.BAD_REQUEST);
	}

	// ---------------------------------------GET(ById)---------------------------------------

	@Test
	public void metodo11GetConIdDebeResponderOKTest() throws Exception {

		String responseBody = mMvcU.controllerResponseTesterNoPayloadUtil(mockMvc, get("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L,"44333222J", "PostTest", "666777888"), personaResponse);
	}

	@Test
	public void metodo12GetConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		mMvcU.controllerResponseTesterNoPayloadUtil(mockMvc, get("/api/persona/5"), HttpStatus.NOT_FOUND);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero envuelta en una ServletException.
	// Test realizado en PersonaControllerRestTest
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo13GetConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		mMvcU.controllerResponseTesterNoPayloadUtil(mockMvc, get("/api/persona/0"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ---------------------------------------PUT---------------------------------------

	@Test
	public void metodo14PutConIdCorrectoYPayloadCorrectoDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PutTest", "999888777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(personaRequest, personaResponse);
	}

	@Test
	public void metodo15PutConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PutTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/5"), HttpStatus.NOT_FOUND);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo16PutConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PutTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/0"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void metodo17PutNoTieneEnCuentaIdPayloadDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(3L,"44333222J", "PutTest", "999888777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L,"44333222J", "PutTest", "999888777"), personaResponse);
	}
	
	@Test
	public void metodo18PutConDniVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"", "PutTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo19PutConDniNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "PutTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo20PutConFormatoDniIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"12345J", "PutTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo21PutConNombreVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo22PutConNombreNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", null, "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo23PutConTelefonoVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PutTest", "");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo24PutConTelefonoNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PutTest", null);
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void metodo25PutConFormatoTelefonoIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PutTest", "123456");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, put("/api/persona/1"), HttpStatus.BAD_REQUEST);
	}

	// ---------------------------------------PATCH---------------------------------------

	@Test
	public void metodo20PatchConIdCorrectoYPayloadCorrectoDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PatchTest", "999888777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L,"44333222J", "PatchTest", "999888777"), personaResponse);
	}

	@Test
	public void metodo21PatchNoTieneEnCuentaIdPayloadDebeResponderOKTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(3L,"44333222J", "PatchTest", "777777777");
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L,"44333222J", "PatchTest", "777777777"), personaResponse);
	}

	@Test
	public void metodo22PatchConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PatchTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/5"), HttpStatus.NOT_FOUND);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo23PatchConIdIncorrectoeDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PatchTest", "999888777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/0"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo24PatchConDniVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"", "PatchTest", "777666555");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo25PatchConDniNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L, null, "PatchTest", "777666555");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo26PatchConFormatoDniIncorrectoDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"123555J", "PatchTest", "777666555");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo27PatchConNombreVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "", "777777777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo28PatchConNombreNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", null, "777777777");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo29PatchConTelefonoVacioDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PatchTest", "");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo30PatchConTelefonoNullDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PatchTest", null);
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo31PatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest() throws Exception {

		PersonaDto personaRequest = new PersonaDto(1L,"44333222J", "PatchTest", "123456");
		mMvcU.controllerResponseTesterPayloadUtil(mockMvc, personaRequest, patch("/api/persona/1"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void metodo32PatchActualizaSoloDniDebeResponderOKTest() throws Exception {

		String jsonString = "{\"dni\":\"33222777E\"}";
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, jsonString, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L,"33222777E", "PatchTest", "777777777"), personaResponse);
	}
	
	@Test
	public void metodo33PatchActualizaSoloNombreDebeResponderOKTest() throws Exception {

		String jsonString = "{\"nombre\":\"SoloNombrePatch\"}";
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, jsonString, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L,"33222777E", "SoloNombrePatch", "777777777"), personaResponse);
	}

	@Test
	public void metodo34PatchActualizaSoloTelefonoDebeResponderOKTest() throws Exception {

		String jsonString = "{\"telefono\":\"666777888\"}";
		String responseBody = mMvcU.controllerResponseTesterPayloadUtil(mockMvc, jsonString, patch("/api/persona/1"), HttpStatus.OK);
		PersonaDto personaResponse = objectMapper.readValue(responseBody, PersonaDto.class);
		assertEquals(new PersonaDto(1L,"33222777E", "SoloNombrePatch", "666777888"), personaResponse);
	}

	// ---------------------------------------DELETE---------------------------------------

	@Test
	public void metodo35DeleteDebeResponderNoContentTest() throws Exception {
		mockMvc.perform(delete("/api/persona/1")).andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	public void metodo36DeleteConIdInexistenteDebeResponderNotFoundTest() throws Exception {
		mockMvc.perform(delete("/api/persona/1")).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	// https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
	// Son excepciones envueltas en otra excepción. Atrapa una excepcion (P.E
	// ConstraintViolationException) y la arroja nuevamente en algún momento, pero
	// envuelta en una ServletException.
	@Ignore("PENDIENTE CONTROL DE ESTADOS HTTP")
	@Test(expected = NestedServletException.class)
	public void metodo37DeleteConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {
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
