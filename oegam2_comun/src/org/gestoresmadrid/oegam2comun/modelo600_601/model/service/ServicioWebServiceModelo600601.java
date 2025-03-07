package org.gestoresmadrid.oegam2comun.modelo600_601.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelos;

public interface ServicioWebServiceModelo600601 extends Serializable{
	
	public static String ENCODING_XML = "ISO-8859-1";
	public static String ENCODING_XML_UTF8 = "UTF-8";
	public static String SERVICIO_FORMULARIO600601_URL = "servicio.formulario600601.url";
	public static String GRUPO_ORIGEN = "GESTORES";
	public static String NOMBRE_FICH_CARTA_PAGO = "cartaPago";
	public static String NOMBRE_FICH_DILIGENCIA = "diligencia";
	public static String NOMBRE_FICH_ESCRITURA = "Escritura";
	public static String cobrarCreditos = "cobrar.creditos.cam";
	public static String NOTIFICACION = "PROCESO PRESENTACION MODELOS 600/601 FINALIZADO";
	
	ResultadoModelos procesarSolicitud(ColaBean solicitud);

	void cambiarEstadoModelo(BigDecimal idTramite, BigDecimal nuevoEstado, BigDecimal idUsuario);

	void guardarResultadoSubSanableFinalizado(BigDecimal numExpediente, String respuesta);

	void tratarDevolverCredito(BigDecimal numExpediente, BigDecimal idUsuario);

	void crearNotificacion(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estado);

}
