package org.gestoresmadrid.oegam2comun.arrendatarios.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultadoWSCaycBean;

public interface ServicioWebServiceArrendatario extends Serializable {

	public static final String URL_WS = "webservice.altaArrendatario.url";
	public static final String TIMEOUT = "webservice.altaArrendatario.timeOut";

	ResultadoWSCaycBean procesarAltaArrendatario(ColaBean colabean);

	ResultadoWSCaycBean procesarModificacionArrendatario(ColaBean colabean);

	void cambiarEstadoConsulta(BigDecimal idConsultaConductor, BigDecimal idUsuario, EstadoCaycEnum estado,
			String respuesta, String numRegistro);

}