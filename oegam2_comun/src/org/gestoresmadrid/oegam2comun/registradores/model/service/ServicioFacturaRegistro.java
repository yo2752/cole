package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FacturaRegistroDto;

public interface ServicioFacturaRegistro extends Serializable{

	public ResultRegistro guardarOActualizarFacturaRegistro(FacturaRegistroDto facturaRegistroDto, BigDecimal idTramiteRegistro);
	public ResultRegistro validarFacturaRegistro(FacturaRegistroDto facturaRegistroDto);
	public List<FacturaRegistroDto> getFacturasPorTramite(BigDecimal idTramiteRegistro);
	public ResultRegistro getFacturaRegistro(Long identificador);
	public ResultRegistro borrarFacturaRegistro(Long id);

}
