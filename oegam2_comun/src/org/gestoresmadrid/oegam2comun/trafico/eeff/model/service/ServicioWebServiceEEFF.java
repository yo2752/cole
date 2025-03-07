package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.ResultadoWSEEFF;

public interface ServicioWebServiceEEFF extends Serializable {

	public static final String UTF_8 = "UTF-8";
	public static final String EEFF_URL = "eeff.direccionWS";
	public static final String TIMEOUT_PROP_EEFF = "eeff.timeOut";
	public static final String ESTADO_BASTIDOR_LIBERADO	= "LIBERADA";

	ResultadoWSEEFF liberar(ColaBean solicitud);

	void cambiarEstadoLiberacion(BigDecimal numExpediente, BigDecimal idUsuario, EstadoTramiteTrafico estadoNuevo, EstadoTramiteTrafico estadoAnt, String respuestaError);

	ResultadoWSEEFF consultar(ColaBean solicitud);

	void cambiarEstadoConsulta(BigDecimal idTramite, BigDecimal idUsuario, EstadoConsultaEEFF estadoNuevo, EstadoConsultaEEFF estadoAnt, String respuesta);

}