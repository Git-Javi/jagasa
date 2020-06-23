package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.api.dto.PersonaDto;
import app.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@Slf4j
@Api(tags = "Persona Controller")
@RequestMapping(path = "/api")
public class PersonaController {

	@Autowired
	private PersonaService personaService;

	@ApiOperation(value = "Crea una persona")
	@PostMapping(value = "/persona")
	@ResponseStatus(value = HttpStatus.CREATED)
	public PersonaDto createPersona(@RequestBody @Valid @NotNull PersonaDto persona) {

		log.info("Inicio :: PersonaController.createPersona(PersonaDto): {}", persona);
		PersonaDto result = personaService.createPersona(persona);
		log.info("Fin :: PersonaController.createPersona(PersonaDto): {}", result);

		return result;
	}

	@ApiOperation("Muestra las personas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@GetMapping(value = "/persona")
	@ResponseStatus(value = HttpStatus.OK)
	public List<PersonaDto> showPersonas() {

		List<PersonaDto> listaPersonasDto = new ArrayList<>();
		listaPersonasDto.addAll(personaService.findPersonas());

		for (PersonaDto p : listaPersonasDto) {
			log.info("Persona de la lista: {}", p);
		}

		return listaPersonasDto;
	}

	@ApiOperation("Muestra una persona por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@GetMapping("/persona/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public PersonaDto getPersona(@PathVariable("id") @NotNull @Positive Long id) {

		return personaService.findPersonaById(id);
	}

	@ApiOperation("Actualiza todos los datos de una persona por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@PutMapping("/persona/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public PersonaDto updatePersona(@PathVariable("id") @NotNull @Positive Long id,
			@RequestBody @Valid @NotNull PersonaDto persona) {

		return personaService.updatePersonaById(id, persona);
	}

	@ApiOperation("Elimina una persona por id")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "NO CONTENT") , @ApiResponse(code = 404, message = "NOT FOUND") })
	@DeleteMapping("/persona/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePersona(@PathVariable("id") @NotNull @Positive Long id) {

		personaService.deletePersonaById(id);
	}

	@ApiOperation("Actualiza parcialmente una persona por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@PatchMapping(value = "/persona/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public PersonaDto patchPersona(@PathVariable("id") @NotNull @Positive Long id,
			@RequestBody Map<String, Object> fields) {

		return personaService.updatePersonaFieldsById(id, fields);
	}

//-----------------------------------------------------------------------------------------------------------------------------

//  // Manejador para la excepción lanzada cuando no cumple con las constraint
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(value = HttpStatus.BAD_REQUEST) // Con ésta notación corrijo el problemas de que me muestre el mensaje pero con status 200
//	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//	    Map<String, String> errors = new HashMap<>();
//	    ex.getBindingResult().getAllErrors().forEach((error) -> {
//	        String fieldName = ((FieldError) error).getField();
//	        String errorMessage = error.getDefaultMessage();
//	        errors.put(fieldName, errorMessage);
//	    });
//	    return errors;
//	}

}
