package app.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import app.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { Application.class })
@AutoConfigureMockMvc //Esto permite inyectar en la clase de test una instancia de MockMvc que podemos utilizar directamente sin ninguna configuración adicional.
public class PersonaServiceTest {

	@Autowired
	private MockMvc mockMvc;

//	@MockBean
//	private PersonaService service;

	@Test
	public void laRespuestaDelGetDebeSer200Test() throws Exception { // Porqué lanza exception?? quien?
		mockMvc.perform(get("http://localhost:8080/api/persona")).andExpect(status().is(HttpStatus.OK.value()));

	}

	@Test
	public void laRespuestaDelGetDebeSer200YJsonVacioTest() throws Exception {
		String response = mockMvc.perform(get("http://localhost:8080/api/persona"))
				.andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();

		assertEquals("[]", response);
		
		System.out.println("-------------------------------------"+response);
	}

}
