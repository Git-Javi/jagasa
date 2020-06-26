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

import app.api.dto.TipoPresenciaDto;
import app.service.TipoPresenciaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@Slf4j
@Api(tags = "Tipo Presencia Controller")
@RequestMapping(path = "/api")
public class TipoPresenciaController {

	@Autowired
	private TipoPresenciaService tipoPresenciaService;

	@ApiOperation(value = "Crea un tipo de presencia")
	@PostMapping(value = "/tipoPresencia")
	@ResponseStatus(value = HttpStatus.CREATED)
	public TipoPresenciaDto createTipoPresencia(@RequestBody @Valid @NotNull TipoPresenciaDto tipoPresencia) {

		log.info("Inicio :: TipoPresenciaController.createTipoPresencia(TipoPresenciaDto): {}", tipoPresencia);
		TipoPresenciaDto result = tipoPresenciaService.createTipoPresencia(tipoPresencia);
		log.info("Fin :: TipoPresenciaController.createTipoPresencia(TipoPresenciaDto): {}", result);

		return result;
	}

	@ApiOperation("Muestra los tipos de presencias")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@GetMapping(value = "/tipoPresencia")
	@ResponseStatus(value = HttpStatus.OK)
	public List<TipoPresenciaDto> showTipoPresencias() {

		List<TipoPresenciaDto> listaTipoPresenciasDto = new ArrayList<>();
		listaTipoPresenciasDto.addAll(tipoPresenciaService.findTipoPresencias());

		for (TipoPresenciaDto p : listaTipoPresenciasDto) {
			log.info("TipoPresencia de la lista: {}", p);
		}

		return listaTipoPresenciasDto;
	}

	@ApiOperation("Muestra un tipo de presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@GetMapping("/tipoPresencia/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public TipoPresenciaDto getTipoPresencia(@PathVariable("id") @NotNull @Positive Long id) {

		return tipoPresenciaService.findTipoPresenciaById(id);
	}

	@ApiOperation("Actualiza todos los datos de un tipo de presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@PutMapping("/tipoPresencia/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public TipoPresenciaDto updateTipoPresencia(@PathVariable("id") @NotNull @Positive Long id,
			@RequestBody @Valid @NotNull TipoPresenciaDto tipoPresencia) {

		return tipoPresenciaService.updateTipoPresenciaById(id, tipoPresencia);
	}

	@ApiOperation("Elimina un tipo de presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "NO CONTENT") , @ApiResponse(code = 404, message = "NOT FOUND") })
	@DeleteMapping("/tipoPresencia/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteTipoPresencia(@PathVariable("id") @NotNull @Positive Long id) {

		tipoPresenciaService.deleteTipoPresenciaById(id);
	}

	@ApiOperation("Actualiza parcialmente un tipo de presencia por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "NOT FOUND") })
	@PatchMapping(value = "/tipoPresencia/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public TipoPresenciaDto patchTipoPresencia(@PathVariable("id") @NotNull @Positive Long id,
			@RequestBody Map<String, Object> fields) {

		return tipoPresenciaService.updateTipoPresenciaFieldsById(id, fields);
	}
	
	
	
}
