package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatoFirmaDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;

public interface ServicioDatoFirma extends Serializable{

	public ResultRegistro getDatoFirma(String id);
	public ResultRegistro borrarDatosFirma(String id);
	public ResultRegistro guardarOActualizarDatosFirma(DatoFirmaDto datoFirmaDto, BigDecimal idTramiteRegistro, String tipoTramite);
	public DireccionDto convertirLugarFirma(String direccionFirma);
	public ResultRegistro validarDatosFirma(DatoFirmaDto datoFirmaDto, String tipoTramite);

}
