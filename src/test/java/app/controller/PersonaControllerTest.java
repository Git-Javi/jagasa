package app.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.validation.ConstraintViolationException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.Application;
import app.api.dto.PersonaDto;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
@AutoConfigureMockMvc // Permite inyectar en la clase de test una instancia de MockMvc que podemos utilizar directamente sin ninguna configuración adicional.
public class PersonaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	
	// ---------------------------------------GET(All)---------------------------------------

//		@Test
//		public void metodo10GetDebeResponderOKTest() throws Exception {
//			String response = mockMvc.perform(get("http://localhost:8080/api/persona"))
//					.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();
//
//			System.out.println(
//					"------------------------xxxxxxxxxxxxxGETxxxxxxxxxxxxx----------------------------" + response);
//			
//			assertEquals("[]", response);
//
//		}

	// ---------------------------------------POST---------------------------------------

	public String metodoRespuestaPost(PersonaDto personaTest, HttpStatus responseState) throws Exception {
		
		String response = mockMvc
				.perform(post("http://localhost:8080/api/persona").content(objectMapper.writeValueAsString(personaTest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(responseState.value())).andReturn().getResponse().getContentAsString();
		
		return response;
	}	
		
		
	@Test
	public void metodoPostConIdNullDebeResponderCreatedConIdAutogeneradoTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(null, "PostTest", "666777888");

//		String response = mockMvc
//				.perform(post("http://localhost:8080/api/persona").content(objectMapper.writeValueAsString(personaTest))
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.CREATED.value())).andReturn().getResponse().getContentAsString();

		assertEquals("{\"id\":1,\"nombre\":\"PostTest\",\"telefono\":\"666777888\"}", metodoRespuestaPost(personaTest, HttpStatus.CREATED));
	}

	@Test
	public void metodoPostConNombreVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "", "666777888");

		String response = mockMvc
				.perform(post("http://localhost:8080/api/persona").content(objectMapper.writeValueAsString(personaTest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPostConNombreNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, null, "666777888");

		String response = mockMvc
				.perform(post("http://localhost:8080/api/persona").content(objectMapper.writeValueAsString(personaTest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPostConTelefonoNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PostTest", null);

		String response = mockMvc
				.perform(post("http://localhost:8080/api/persona").content(objectMapper.writeValueAsString(personaTest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPostConTelefonoVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PostTest", "");

		String response = mockMvc
				.perform(post("http://localhost:8080/api/persona").content(objectMapper.writeValueAsString(personaTest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPostConFormatoTelefonoIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PostTest", "123456");

		String response = mockMvc
				.perform(post("http://localhost:8080/api/persona").content(objectMapper.writeValueAsString(personaTest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}
	
	// ---------------------------------------GET(ById)---------------------------------------

//	@Test
//	public void metodoGetConIdDebeResponderOKTest() throws Exception {
//		String response = mockMvc.perform(get("http://localhost:8080/api/persona/1"))
//				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();
//
//		assertEquals("{\"id\":1,\"nombre\":\"PostTest\",\"telefono\":\"666777888\"}", response);
//	}

	@Test
	public void metodoGetConIdInexistenteDebeResponderNotFoundTest() throws Exception {
		String response = mockMvc.perform(get("http://localhost:8080/api/persona/5"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);

	}

//	@Test//(expected = ConstraintViolationException.class)
//	public void laRespuestaDelGetConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {
//		String response = mockMvc.perform(get("http://localhost:8080/api/persona/0"))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse()
//				.getContentAsString();
//
//		System.out.println("------------------------xxxxxxxxxxxxxxxxxxxxxxxxxx----------------------------" + response);
//
//	}

	// ---------------------------------------PUT---------------------------------------

	@Test
	public void metodoPutConIdCorrectoYPayloadCorrectoDebeResponderOKTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PutTest", "999888777");

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();

		assertEquals("{\"id\":1,\"nombre\":\"PutTest\",\"telefono\":\"999888777\"}", response);

	}

	@Test
	public void metodoPutConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PutTest", "999888777");

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/5")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

//	@Test // (expected = ConstraintViolationException.class)
//	public void laRespuestaDelPutConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {
//		
//		PersonaDto personaTest = new PersonaDto(1L, "PutTest", "999888777");
//
//		String response = mockMvc
//				.perform(put("http://localhost:8080/api/persona/0")
//						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse()
//				.getContentAsString();
//
//		assertEquals("", response);
//	}

	@Test
	public void metodoPutNoTieneEnCuentaIdPayloadDebeResponderOKTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(3L, "PutTest", "999888777");

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();

		assertEquals("{\"id\":1,\"nombre\":\"PutTest\",\"telefono\":\"999888777\"}", response);
	}

	@Test
	public void metodoPutConNombreVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "", "999888777");

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPutConNombreNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, null, "999888777");

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPutConTelefonoVacioDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PutTest", "");

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPutConTelefonoNullDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PutTest", null);

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	@Test
	public void metodoPutConFormatoTelefonoIncorrectoDebeResponderBadRequestTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PutTest", "123456");

		String response = mockMvc
				.perform(put("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

	// ---------------------------------------PATCH---------------------------------------

	@Test
	public void metodoPatchConIdCorrectoYPayloadCorrectoDebeResponderOKTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PatchTest", "999888777");

		String response = mockMvc
				.perform(patch("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();

		assertEquals("{\"id\":1,\"nombre\":\"PatchTest\",\"telefono\":\"999888777\"}", response);
	}

	@Test
	public void metodoPatchNoTieneEnCuentaIdPayloadDebeResponderOKTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(3L, "PatchTest", "777777777");

		String response = mockMvc
				.perform(patch("http://localhost:8080/api/persona/1")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();

		assertEquals("{\"id\":1,\"nombre\":\"PatchTest\",\"telefono\":\"777777777\"}", response);

		System.out.println(
				"------------------------xxxxxxxxxxxxxPATCHxxxxxxxxxxxxx----------------------------" + response);
	}

	@Test
	public void metodoPatchConIdInexistenteDebeResponderNotFoundTest() throws Exception {

		PersonaDto personaTest = new PersonaDto(1L, "PatchTest", "999888777");

		String response = mockMvc
				.perform(patch("http://localhost:8080/api/persona/5")
						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value())).andReturn().getResponse().getContentAsString();

		assertEquals("", response);
	}

//	@Test
//	public void metodoPatchConIdIncorrectoeDebeResponderInternalServerErrorTest() throws Exception {
//
//		PersonaDto personaTest = new PersonaDto(1L, "PatchTest", "999888777");
//
//		String response = mockMvc
//				.perform(patch("http://localhost:8080/api/persona/0")
//						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse().getContentAsString();
//
//		assertEquals("", response);
//	}
//
//	@Test
//	public void metodoPatchConNombreVacioDebeResponderInternalServerErrorTest() throws Exception {
//
//		PersonaDto personaTest = new PersonaDto(1L, "", "777777777");
//
//		String response = mockMvc
//				.perform(patch("http://localhost:8080/api/persona/1")
//						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse().getContentAsString();
//
//		assertEquals("", response);
//	}
//	
//	@Test
//	public void metodoPatchConNombreNullDebeResponderInternalServerErrorTest() throws Exception {
//
//		PersonaDto personaTest = new PersonaDto(1L, null, "777777777");
//
//		String response = mockMvc
//				.perform(patch("http://localhost:8080/api/persona/1")
//						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse().getContentAsString();
//
//		assertEquals("", response);
//	}
//	
//	@Test
//	public void metodoPatchConTelefonoVacioDebeResponderInternalServerErrorTest() throws Exception {
//
//		PersonaDto personaTest = new PersonaDto(1L, "PatchTest", "");
//
//		String response = mockMvc
//				.perform(patch("http://localhost:8080/api/persona/1")
//						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse().getContentAsString();
//
//		assertEquals("", response);
//	}	
//	
//	@Test
//	public void metodoPatchConTelefonoNullDebeResponderInternalServerErrorTest() throws Exception {
//
//		PersonaDto personaTest = new PersonaDto(1L, "PatchTest", null);
//
//		String response = mockMvc
//				.perform(patch("http://localhost:8080/api/persona/1")
//						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse().getContentAsString();
//
//		assertEquals("", response);
//	}
//
//	@Test
//	public void metodoPatchConFormatoTelefonoIncorrectoDebeResponderInternalServerErrorTest() throws Exception {
//
//		PersonaDto personaTest = new PersonaDto(1L, "PutTest", "123456");
//
//		String response = mockMvc
//				.perform(patch("http://localhost:8080/api/persona/1")
//						.content(objectMapper.writeValueAsString(personaTest)).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value())).andReturn().getResponse().getContentAsString();
//
//		assertEquals("", response);
//	}

	@Test
	public void metodoPatchActualizaSoloNombreDebeResponderOKTest() throws Exception {

		String jsonString = "{\"nombre\":\"SoloNombrePatch\"}";

		String response = mockMvc
				.perform(patch("http://localhost:8080/api/persona/1").content(jsonString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();

		assertEquals("{\"id\":1,\"nombre\":\"SoloNombrePatch\",\"telefono\":\"999888777\"}", response);

		System.out.println(
				"------------------------xxxxxxxxxxxxxNombrePATCHxxxxxxxxxxxxx----------------------------" + response);
	}

	@Test
	public void metodoPatchActualizaSoloTelefonoDebeResponderOKTest() throws Exception {

		String jsonString = "{\"telefono\":\"666777888\"}";

		String response = mockMvc
				.perform(patch("http://localhost:8080/api/persona/1").content(jsonString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();

		assertEquals("{\"id\":1,\"nombre\":\"PostTest\",\"telefono\":\"666777888\"}", response);

		System.out.println("------------------------xxxxxxxxxxxxxTelefonoPATCHxxxxxxxxxxxxx----------------------------"
				+ response);
	}

	// ---------------------------------------DELETE---------------------------------------

	@Test
	public void metodoDeleteDebeResponderNoContentTest() throws Exception {
		mockMvc.perform(delete("http://localhost:8080/api/persona/1"))
				.andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}

	@Test
	public void metodoDeleteConIdInexistenteDebeResponderNotFoundTest() throws Exception {
		mockMvc.perform(delete("http://localhost:8080/api/persona/5"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

//	@Test//(expected = ConstraintViolationException.class)
//	public void laRespuestaDelDeleteConIdIncorrectoDebeSerInternalServerErrorTest() throws Exception {
//		mockMvc.perform(delete("http://localhost:8080/api/persona/0"))
//				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
//	}

	

}
