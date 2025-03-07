package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OtroImporteDto;

public interface ServicioOtroImporte extends Serializable{

	public ResultRegistro getOtroImporte(String id);
	public ResultRegistro guardarOActualizarOtroImporte(OtroImporteDto otroImporteDto, long idDatosFinancieros);
	public ResultRegistro borrarOtroImporte(String id);
	public ResultRegistro validarOtroImporte(OtroImporteDto otroImporteDto);

}
