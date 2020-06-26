package app.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import app.api.dto.PresenciaDto;

public interface PresenciaService {

	PresenciaDto createPresencia(@Valid @NotNull PresenciaDto presencia);
	
	List<PresenciaDto> findPresencias();
	
	PresenciaDto findPresenciaById(@NotNull @Positive Long id);
	
	PresenciaDto updatePresenciaById(@NotNull @Positive Long id, @Valid @NotNull PresenciaDto presencia);
	
	void deletePresenciaById(@NotNull @Positive Long id);
	
	PresenciaDto updatePresenciaFieldsById(@NotNull @Positive Long id, Map<String,Object> fields);
	
	public void presenciaExists(@NotNull @Positive Long id);
	
	public PresenciaDto presenciaFind(@NotNull @Positive Long id);
	
	public PresenciaDto presenciaSave(@Valid @NotNull PresenciaDto presenciaDto);
	
	public PresenciaDto presenciaMergeIdSave(@NotNull @Positive Long id, @Valid @NotNull PresenciaDto presenciaDto);
	
}
