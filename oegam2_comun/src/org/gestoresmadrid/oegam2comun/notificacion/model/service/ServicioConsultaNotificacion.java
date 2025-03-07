package org.gestoresmadrid.oegam2comun.notificacion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.wscn.model.dto.RespuestaNotificacionesSS;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioConsultaNotificacion extends Serializable{
	
	public static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	ResultBean creaSolicitudActualizar(String alias, BigDecimal idContrato, BigDecimal idUsuario);

	ResultBean creaSolicitudAceptar(String indices, String alias, BigDecimal idUsuario);

	ResultBean creaSolicitudRechazar(String indices, String alias, BigDecimal idUsuario);

	ResultBean creaSolicitudImprimir(String indices, String alias, BigDecimal idUsuario);
	
	public ResultBean creaSolicitudCorreo(String alias, BigDecimal idContrato, BigDecimal idUsuario);

	RespuestaNotificacionesSS aceptarNotificaciones(String rol, String codigoNotificacion, String alias, String numColegiado);

	RespuestaNotificacionesSS rechazarNotificaciones(String rol, String codigoNotificacion, String alias, String numColegiado);

	RespuestaNotificacionesSS imprimirNotificaciones(String rol, String codigoNotificacion, String alias, String numColegiado);

	RespuestaNotificacionesSS generaRespuesta(ColaBean solicitud) throws OegamExcepcion;

	RespuestaNotificacionesSS recuperarNotificaciones(String alias, BigDecimal numColegiado, BigDecimal idContrato);

	ResultBean aceptarServicio(BigDecimal idContrato, String numColegiado);

}
