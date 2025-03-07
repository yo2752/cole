package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReunionDto;

import escrituras.beans.ResultBean;

public interface ServicioReunion extends Serializable {

	public static String ID_REUNION = "idReunion";

	ReunionDto getReunion(BigDecimal idTramiteRegistro);

	ResultBean guardarReunion(ReunionDto reunion, BigDecimal idTramiteRegistro, String tipoTramite);
}
