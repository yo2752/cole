package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MinutaRegistroDto;

public interface ServicioMinutaRegistro extends Serializable{

	public ResultRegistro getMinutaRegistro(String id);
	public ResultRegistro borrarMinutaRegistro(String id);
	public ResultRegistro guardarOActualizarMinutaRegistro(MinutaRegistroDto minutaRegistroDto, BigDecimal idTramiteRegistro);
	public ResultRegistro validarMinutaRegistro(MinutaRegistroDto minutaRegistroDto);
	public List<MinutaRegistroDto> getMinutasPorTramite(BigDecimal idTramiteRegistro);

}
