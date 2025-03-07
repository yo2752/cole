package org.gestoresmadrid.oegam2comun.wscn.model.service;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.wscn.model.dto.RespuestaNotificacionesSS;
import org.gi.infra.wscn.pruebas.ws.ListadoPoderdantes;

public interface ServicioWebServiceConsultaNotificaciones extends javax.xml.rpc.Service {

	public final String NOTIFICACIONES_SS_ERROR_LOG = "WS_NOTIFICACIONES_SS:";

	RespuestaNotificacionesSS recuperaListadoNotificacionesByRol(int rol, String apoderdante, String alias, BigDecimal numColegiado);

	RespuestaNotificacionesSS recuperaNotificaciones(String alias, BigDecimal numColegiado, BigDecimal idContrato);

	RespuestaNotificacionesSS aceptarNotificaciones(String xmlEnviar, String alias);

	RespuestaNotificacionesSS rechazarNotificaciones(String xmlEnviar, String alias);

	RespuestaNotificacionesSS imprimirNotificaciones(String xmlEnviar, String alias);

	RespuestaNotificacionesSS solicitarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, int codigoNotificacion, boolean esDeAceptacion, String alias);

	RespuestaNotificacionesSS enviarAcuseNotificacion(int rol, java.lang.String identificadorPoderdante, byte[] xmlAcuseFirmado, String alias);

	RespuestaNotificacionesSS verNotificacionAceptada(int rol, String identificadorPoderdante, int codigoNotificacion, String alias);

	ListadoPoderdantes solicitarPoderdantes(String alias);

}