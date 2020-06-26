package app.service;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import app.api.dto.TipoPresenciaDto;

public interface TipoPresenciaService {

	TipoPresenciaDto createTipoPresencia(@Valid @NotNull TipoPresenciaDto tipoPresencia);
	
	List<TipoPresenciaDto> findTipoPresencias();
	
	TipoPresenciaDto findTipoPresenciaById(@NotNull @Positive Long id);
	
	TipoPresenciaDto updateTipoPresenciaById(@NotNull @Positive Long id, @Valid @NotNull TipoPresenciaDto tipoPresencia);
	
	void deleteTipoPresenciaById(@NotNull @Positive Long id);
	
	TipoPresenciaDto updateTipoPresenciaFieldsById(@NotNull @Positive Long id, Map<String,Object> fields);
	
	public void tipoPresenciaExists(@NotNull @Positive Long id);
	
	public TipoPresenciaDto tipoPresenciaFind(@NotNull @Positive Long id);
	
	public TipoPresenciaDto tipoPresenciaSave(@Valid @NotNull TipoPresenciaDto tipoPresenciaDto);
	
	public TipoPresenciaDto tipoPresenciaMergeIdSave(@NotNull @Positive Long id, @Valid @NotNull TipoPresenciaDto tipoPresenciaDto);
	
}
