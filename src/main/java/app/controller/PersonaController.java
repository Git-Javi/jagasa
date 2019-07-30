package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.api.dto.PersonaDto;
import app.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@Slf4j
@Api(tags = "Controller")
@RequestMapping(path = "/api")
public class PersonaController {

	@Autowired
	private PersonaService personaService;

	@ApiOperation(value = "Crea una persona")
	@PostMapping(value = "/persona")
	public PersonaDto createPersona(@RequestBody @Valid @NotNull PersonaDto persona) {

		log.info("Inicio :: PersonaController.createPersona(PersonaDto): {}", persona);
		PersonaDto result = personaService.createPersona(persona);
		log.info("Fin :: PersonaController.createPersona(PersonaDto): {}", result);

		return result;
	}

	@ApiOperation("Muestra las personas")
	@GetMapping(value = "/persona")
	public List<PersonaDto> showPersonas() {

		List<PersonaDto> listaPersonasDto = new ArrayList<>();
		listaPersonasDto.addAll(personaService.findPersonas());

		for (PersonaDto p : listaPersonasDto) {
			log.info("Persona de la lista: {}", p);
		}

		return listaPersonasDto;
	}

	@ApiOperation("Muestra una persona por id")
	@GetMapping("/persona/{id}")
	public PersonaDto getPersona(@PathVariable("id") @NotNull @Positive Long id) {

		return personaService.findPersonaById(id);
	}

	@ApiOperation("Actualiza todos los datos de una persona por id")
	@PutMapping("/persona/{id}")
	public PersonaDto updatePersona(@PathVariable("id") @NotNull @Positive Long id,
			@RequestBody @Valid @NotNull PersonaDto persona) {

		return personaService.updatePersonaById(id, persona);
	}

	@ApiOperation("Elimina una persona por id")
	@DeleteMapping("/persona/{id}")
	public void deletePersona(@PathVariable("id") @NotNull @Positive Long id) {

		personaService.deletePersonaById(id);
	}

	@ApiOperation("Actualiza parcialmente una persona")
	@PatchMapping(value = "/persona/{id}")
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
