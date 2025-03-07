package org.gestoresmadrid.oegam2comun.conductor.model.service;

import java.math.BigDecimal;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultadoWSCaycBean;

public interface ServicioWebServiceConductorHabitual {

	public static final String URL_WS = "webservice.altaConductorHabitual.url";
	public static final String TIMEOUT = "webservice.altaConductorHabitual.timeOut";
	public final String UTF_8 = "UTF-8";

	ResultadoWSCaycBean procesarAltaConductor(ColaBean colabean);

	ResultadoWSCaycBean procesarModificacionConductor(ColaBean colabean);

	void cambiarEstadoConsulta(BigDecimal idConsultaConductor, BigDecimal idUsuario, EstadoCaycEnum estado,
			String respuesta, String numRegistro);

}