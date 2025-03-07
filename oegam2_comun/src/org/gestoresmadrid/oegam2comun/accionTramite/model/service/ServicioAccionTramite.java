package org.gestoresmadrid.oegam2comun.accionTramite.model.service;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.accionTramite.model.vo.AccionTramiteVO;

import utilidades.web.OegamExcepcion;

public interface ServicioAccionTramite {

	void crearAccionTramite(BigDecimal idUsuario, BigDecimal idTramite, String accion, Date fechaFin, String respuesta) throws OegamExcepcion;

	AccionTramiteVO setAccionTramite(BigDecimal idUsuario, BigDecimal numExpediente, String accion, Date fechaFin, String respuesta);

	void guardarAccion(AccionTramiteVO accionTramiteVO);

	void crearCerrarAccionTramite(BigDecimal idUsuario, BigDecimal idExpediente, String accion, String respuesta);

	void cerrarAccionTramite(BigDecimal idUsuario, BigDecimal idExpediente, String accion, String respuesta);

}
