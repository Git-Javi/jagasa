package app.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.api.dto.PresenciaDto;
import app.exception.FieldNotFoundJagasaException;
import app.exception.NotFoundJagasaException;
import app.mapper.PresenciaMapper;
import app.model.entity.Presencia;
import app.repository.PresenciaRepository;
import lombok.extern.slf4j.Slf4j;

@Validated
@Service
@Slf4j
public class PresenciaServiceImpl implements PresenciaService {

	@Autowired
	private PresenciaRepository presenciaRepository;

	@Autowired
	private PresenciaMapper presenciaMapper;

	@Override
	public PresenciaDto createPresencia(@Valid @NotNull PresenciaDto presencia) {

		log.info("Inicio :: PresenciaService.createPresencia(PresenciaDto): {}", presencia);

		Presencia presenciaRequest = presenciaMapper.presenciaDtoToPresencia(presencia);
		log.info("Request :: PresenciaService.createPresencia(PresenciaDto): {}", presenciaRequest);

		Presencia presenciaResponse = presenciaRepository.save(presenciaRequest);
		log.info("Response :: PresenciaService.createPresencia(PresenciaDto): {}", presenciaResponse);

		PresenciaDto result = presenciaMapper.presenciaToPresenciaDto(presenciaResponse);
		log.info("Fin :: PresenciaService.createPresencia(PresenciaDto): {}", result);

		return result;
	}

	@Override
	public List<PresenciaDto> findPresencias() {

		List<PresenciaDto> listaPresenciasDto = new ArrayList<>();
		List<Presencia> listaPresencias = new ArrayList<>();
		listaPresencias.addAll((List<Presencia>) presenciaRepository.findAll());

		for (Presencia p : listaPresencias) {
			PresenciaDto pDto = presenciaMapper.presenciaToPresenciaDto(p);
			listaPresenciasDto.add(pDto);
		}

		return listaPresenciasDto;
	}

	@Override
	public PresenciaDto findPresenciaById(@NotNull @Positive Long id) {

		presenciaExists(id);
		PresenciaDto pDto = presenciaFind(id);
		log.info("Response :: La presencia solicitada es: {}", pDto);

		return pDto;
	}

	@Override
	public PresenciaDto updatePresenciaById(@NotNull @Positive Long id, @Valid @NotNull PresenciaDto presenciaDto) {

		presenciaExists(id);
		PresenciaDto pDto = presenciaMergeIdSave(id, presenciaDto);
		log.info("Response :: La presencia con id " + id + " se ha actualizado a: {}", pDto);

		return pDto;
	}

	@Override
	public void deletePresenciaById(@NotNull @Positive Long id) {

		presenciaExists(id);
		presenciaRepository.deleteById(id);
	}

	@Override
	public PresenciaDto updatePresenciaFieldsById(@NotNull @Positive Long id, Map<String, Object> fields) {
		// Comprobamos que la presencia exista
		presenciaExists(id);
		// Recuperamos la presencia de BBD
		PresenciaDto presenciaDto = presenciaFind(id);
		// Mapeamos los campos sobre una presencia
		final ObjectMapper mapper = new ObjectMapper();
		final PresenciaDto presenciaNew = mapper.convertValue(fields, PresenciaDto.class);
		// Recorremos los campos
		fields.forEach((k, v) -> {
			// Obtenemos el campo
			Field field = ReflectionUtils.findField(PresenciaDto.class, k);
			if (field == null) {
				throw new FieldNotFoundJagasaException("El campo (" + k + ") no existe en la clase");
			}
			// Guadamos en el campo del objeto original el valor del objeto mapeado
			ReflectionUtils.makeAccessible(field); // Es necesario para que permita accerder al campo ya que en nuestra entidad es privado
			ReflectionUtils.setField(field, presenciaDto, ReflectionUtils.getField(field, presenciaNew));
		});
		PresenciaDto result = presenciaMergeIdSave(id, presenciaDto);
		return result;
	}

	@Override
	public void presenciaExists(@NotNull @Positive Long id) {

		if (!presenciaRepository.existsById(id)) {
			throw new NotFoundJagasaException("No se ha encontrado ese id");
		}
	}

	@Override
	public @Valid PresenciaDto presenciaFind(@NotNull @Positive Long id) {

		Presencia presencia = presenciaRepository.findById(id).get();
		PresenciaDto presenciaDto = presenciaMapper.presenciaToPresenciaDto(presencia);

		return presenciaDto;
	}

	@Override
	public @Valid PresenciaDto presenciaSave(@Valid @NotNull PresenciaDto presenciaDto) {

		Presencia presencia = presenciaMapper.presenciaDtoToPresencia(presenciaDto);
		Presencia presenciaSaved = presenciaRepository.save(presencia);
		PresenciaDto presenciaDtoDevuelta = presenciaMapper.presenciaToPresenciaDto(presenciaSaved);

		return presenciaDtoDevuelta;
	}

	@Override
	public @Valid PresenciaDto presenciaMergeIdSave(@NotNull @Positive Long id, @Valid @NotNull PresenciaDto presenciaDto) {

		Presencia presencia = presenciaMapper.mergePresenciaIdAndPresenciaDtoToPresencia(id, presenciaDto);
		Presencia presenciaSaved = presenciaRepository.save(presencia);
		PresenciaDto presenciaDtoDevuelta = presenciaMapper.presenciaToPresenciaDto(presenciaSaved);

		return presenciaDtoDevuelta;
	}

}
