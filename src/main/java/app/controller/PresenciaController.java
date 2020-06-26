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

import app.api.dto.PresenciaDto;
import app.service.PresenciaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@Slf4j
@Api(tags = "Presencia Controller")
@RequestMapping(path = "/api")
public class PresenciaController {

	
	@Autowired
	private PresenciaService presenciaService;

	@ApiOperation(value = "Registra una presencia")
	@PostMapping(value = "/presencia")
	@ResponseStatus(value = HttpStatus.CREATED)
	public PresenciaDto createPresencia(@RequestBody @Valid @NotNull PresenciaDto presencia) {

		log.info("Inicio :: PresenciaController.createPresencia(PresenciaDto): {}", presencia);
		PresenciaDto result = presenciaService.createPresencia(presencia);
		log.info("Fin :: PresenciaController.createPresencia(PresenciaDto): {}", result);

		return result;
	}
	
	@ApiOperation("Muestra las presencias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@GetMapping(value = "/presencia")
	@ResponseStatus(value = HttpStatus.OK)
	public List<PresenciaDto> showPresencias() {

		List<PresenciaDto> listaPresenciasDto = new ArrayList<>();
		listaPresenciasDto.addAll(presenciaService.findPresencias());

		for (PresenciaDto p : listaPresenciasDto) {
			log.info("Presencia de la lista: {}", p);
		}

		return listaPresenciasDto;
	}

	@ApiOperation("Muestra una presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@GetMapping("/presencia/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public PresenciaDto getPresencia(@PathVariable("id") @NotNull @Positive Long id) {

		return presenciaService.findPresenciaById(id);
	}

	@ApiOperation("Actualiza todos los datos de una presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@PutMapping("/presencia/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public PresenciaDto updatePresencia(@PathVariable("id") @NotNull @Positive Long id,
			@RequestBody @Valid @NotNull PresenciaDto presencia) {

		return presenciaService.updatePresenciaById(id, presencia);
	}

	@ApiOperation("Elimina una presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "NO CONTENT") , @ApiResponse(code = 404, message = "NOT FOUND") })
	@DeleteMapping("/presencia/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePresencia(@PathVariable("id") @NotNull @Positive Long id) {

		presenciaService.deletePresenciaById(id);
	}

	@ApiOperation("Actualiza parcialmente una presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@PatchMapping(value = "/presencia/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public PresenciaDto patchPresencia(@PathVariable("id") @NotNull @Positive Long id,
			@RequestBody Map<String, Object> fields) {

		return presenciaService.updatePresenciaFieldsById(id, fields);
	}
}
