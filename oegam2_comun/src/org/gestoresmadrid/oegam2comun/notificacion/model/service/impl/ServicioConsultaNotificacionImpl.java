package org.gestoresmadrid.oegam2comun.notificacion.model.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacion;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacionTransactional;
import org.gestoresmadrid.oegam2comun.notificacionpreferencias.service.ServicioContratoPreferencias;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.wscn.model.dto.RespuestaNotificacionesSS;
import org.gestoresmadrid.oegam2comun.wscn.model.service.ServicioWebServiceConsultaNotificaciones;
import org.gestoresmadrid.oegam2comun.wscn.utiles.enumerados.ErroresWSNotificacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesPQ;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.utiles.constantes.Constantes;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioConsultaNotificacionImpl implements ServicioConsultaNotificacion {

	@Autowired
	private ServicioCola servicioCola;
	
	@Autowired
	private ServicioWebServiceConsultaNotificaciones servicioWebServiceConsultaNotificacion;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioContratoPreferencias servicioContratoPreferencias;
	
	@Autowired
	private ServicioConsultaNotificacionTransactional consultaNotificacionTransactional;
	
	@Autowired
	private ServicioPermisos servicioPermisos;
	
	@Autowired
	private ServicioContrato servicioContrato;
	
	@Autowired
	private ServicioCorreo servicioCorreo;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	private UtilesViafirma utilesViafirma;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioConsultaNotificacionImpl.class);

	private static final long serialVersionUID = 8967693785923809496L;
	
	private static final String SEGSOL_CREDITOS = "segsol.creditos.consulta";

	public ServicioConsultaNotificacionImpl() {
		super();
	}
	
	public RespuestaNotificacionesSS generaRespuesta(ColaBean solicitud) throws OegamExcepcion{
		RespuestaNotificacionesSS respuesta = null;
		String copiaCorreo = null;
		String[] datosNotificaciones = solicitud.getXmlEnviar().split(" ");
		String alias = datosNotificaciones[0];
		//Aquí tenemos que controlar lo que viene en xml para llamar al metodo del WS correspondiente.
		if (solicitud.getXmlEnviar().contains("actualizar")){ 
			LOG.info("Proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + " -- Entramos a actualizar ");
			respuesta = servicioWebServiceConsultaNotificacion.recuperaNotificaciones(alias, solicitud.getIdUsuario(), solicitud.getIdContrato());
		}else if (solicitud.getXmlEnviar().contains("aceptar")){
			LOG.info("Proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + " -- Entramos a aceptar ");
			respuesta = servicioWebServiceConsultaNotificacion.aceptarNotificaciones(datosNotificaciones[2], alias);
		}else if (solicitud.getXmlEnviar().contains("rechazar")){
			LOG.info("Proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + " -- Entramos a rechazar ");
			respuesta = servicioWebServiceConsultaNotificacion.rechazarNotificaciones(datosNotificaciones[2], alias);
		}else if (solicitud.getXmlEnviar().contains("imprimir")){
			LOG.info("Proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + " -- Entramos a imprimir ");
			respuesta = servicioWebServiceConsultaNotificacion.imprimirNotificaciones(datosNotificaciones[2], alias);
		}else if (solicitud.getXmlEnviar().contains("correo")){
			LOG.info("Consultando las notificaciones del contrato: " +solicitud.getIdContrato());
			String xmlAFirmar = "<AFIRMA><CONTENT Id=\"D0\">Prueba firma</CONTENT></AFIRMA>";
			String pruebaCaduCert = getUtilesViafirma().firmarPruebaCertificadoCaducidadSS(xmlAFirmar, alias);
			if (pruebaCaduCert != null){
				respuesta = servicioWebServiceConsultaNotificacion.recuperaNotificaciones(alias, solicitud.getIdUsuario(), solicitud.getIdContrato());
				if (respuesta.getListaNotificacionSS() != null && respuesta.getListaNotificacionSS().size() > 0){
					LOG.info("Actualizar Notificaciones");					
					
					ContratoVO contrato = servicioContrato.getContrato(solicitud.getIdContrato());
					String emailContrato = contrato.getCorreoElectronico();
					if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
						for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
							if (solicitud.getTipoTramite().equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
								emailContrato = correoContratoTramite.getCorreoElectronico();
								break;
							}
						}
					}
					
					ContratoPreferenciaVO contratoPreferencias = servicioContratoPreferencias.obtenerContratoPreferenciaByIdContrato(solicitud.getIdContrato().longValue());
					if (contratoPreferencias != null){
						copiaCorreo = contratoPreferencias.getOtrosDestinatariosSS();
					}
					String bcc = gestorPropiedades.valorPropertie("seguridadsocial.mail.bcc");
					String subject = "Aviso de notificaciones de la Seguridad Social - " + utilesFecha.formatoFecha(new Date());
					String entorno = gestorPropiedades.valorPropertie("Entorno");
					if(!"PRODUCCION".equals(entorno)){
						subject = entorno + ": " + subject;
					}
					List<NotificacionSSVO> notificacionesPendientes = consultaNotificacionTransactional.obtenerNotificacionesPendientes(contrato.getColegiado().getNumColegiado());
					
					ResultBean resultado;
					
					try {
						resultado = servicioCorreo.enviarCorreo(crearTexto(respuesta.getListaNotificacionSS(), notificacionesPendientes), null, null, subject, emailContrato, copiaCorreo, bcc, null, null);
						if(resultado==null || resultado.getError())
							throw new OegamExcepcion("Error envio mail");
					} catch (IOException e) {
						throw new OegamExcepcion("Error envio mail");
					}
										
					LOG.info("Se ha enviado email con las notificaciones correspondientes para el contrato: " +solicitud.getIdContrato());
				}else{
					LOG.info("No hay notificaciones para el contrato: " +solicitud.getIdContrato());
				}
			}else{
				LOG.info("Está caducado el certificado del contrato: " +solicitud.getIdContrato());
				respuesta = new RespuestaNotificacionesSS();
				respuesta.setError(true);
				respuesta.setMensajesError(Collections.singletonList("Está caducado el certificado del contrato: " +solicitud.getIdContrato()));	
			}
		}
		return respuesta;
	}

	@Override
	@Transactional
	public ResultBean creaSolicitudActualizar(String alias, BigDecimal idContrato, BigDecimal idUsuario) {
		LOG.info("Actualizar Notificaciones");
		ResultBean result = null;
		try {
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum(), alias+" actualizar",
					nodo, null, null, idUsuario, null, idContrato);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la consulta, erorr: ", e);
			result = new ResultBean(true, "Ha surgido un error al actualizar las notificaciones.");
		}

		return result;
	}
	
	@Override
	@Transactional
	public ResultBean creaSolicitudAceptar(String indices, String alias, BigDecimal idUsuario){
		LOG.info("Aceptar Notificación");
		ResultBean result = null;
		try{
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum(), alias+" aceptar "+indices,
					nodo, null, null, idUsuario, null, null);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		}catch (OegamExcepcion e) {
			LOG.error("Error a la hora de aceptar notificaciones, error: ", e);
			result = new ResultBean(true, "Ha surgido un error al aceptar notificaciones.");
		}

		return result;
		
	}
	
	@Override
	@Transactional
	public ResultBean creaSolicitudRechazar(String indices, String alias, BigDecimal idUsuario){
		LOG.info("Rechazar Notificación");
		ResultBean result = null;
		try{
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum(), alias+" rechazar "+indices,
					nodo, null, null, idUsuario, null, null);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		}catch (OegamExcepcion e) {
			LOG.error("Error a la hora de rechazar notificaciones, error: ", e);
			result = new ResultBean(true, "Ha surgido un error al rechazar notificaciones.");
		}

		return result;
		
	}
	
	@Override
	@Transactional
	public ResultBean creaSolicitudImprimir(String indices, String alias, BigDecimal idUsuario){
		LOG.info("Imprimir Notificación");
		ResultBean result = null;
		try{
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum(), alias+" imprimir "+indices,
					nodo, null, null, idUsuario, null, null);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		}catch (OegamExcepcion e) {
			LOG.error("Error a la hora de rechazar notificaciones, error: ", e);
			result = new ResultBean(true, "Ha surgido un error al imprimir notificaciones.");
		}

		return result;
		
	}
	
	@Override
	@Transactional
	public ResultBean creaSolicitudCorreo(String alias, BigDecimal idContrato, BigDecimal idUsuario) {
		LOG.info("Actualizar Notificaciones y envío de correo automático");
		ResultBean result = null;
		try {
			String nodo = gestorPropiedades.valorPropertie(ServicioConsultaNotificacion.NOMBRE_HOST_SOLICITUD);
			result = servicioCola.crearSolicitud(ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum(), alias+" correo",
					nodo, null, null, idUsuario, null, idContrato);
			LOG.info("Se ha creado la cola para el proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + "\n" +
					"En el nodo " + nodo);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de crear la consulta, erorr: ", e);
			result = new ResultBean(true, "Ha surgido un error al actualizar las notificaciones.");
		}

		return result;
	}
	
	@Transactional
	public RespuestaNotificacionesSS aceptarNotificaciones (String rol, String codigoNotificacion, String alias, String numColegiado){
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		RespuestaNotificacionesSS respuestaSolicitar = servicioWebServiceConsultaNotificacion.solicitarAcuseNotificacion(Integer.parseInt(rol),
				null, Integer.parseInt(codigoNotificacion), true, alias);
		if (respuestaSolicitar.getException() != null){
			LOG.info("Error al recibir respuesta de solicitar acuse notificacion "+ respuestaSolicitar.getException().getMessage() );
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error de comunicación con el Web Service de la Seguridad Social", numColegiado);
			return respuestaSolicitar;
		}
		if (respuestaSolicitar.getAcuse().getError().getCodigo() == 0){
			LOG.info("RESPUESTA OK, ACUSE RECIBIDO "+ respuestaSolicitar.getAcuse().getCodigoNotificacion());
			//En caso de que la llamada al WS sea correcta, guardo el acuse 
			guardarAcuse(respuestaSolicitar.getAcuse().getCodigoNotificacion(), respuestaSolicitar.getAcuse().getXML(), numColegiado);
			//Insertamos en la tabla resultados
			consultaNotificacionTransactional.insertarRespuesta(respuestaSolicitar.getAcuse().getCodigoNotificacion(), "Acuse solicitado", numColegiado);
		} else if (respuestaSolicitar.getAcuse().getError().getCodigo() != 0){
			String error = ErroresWSNotificacion.convertirTexto(respuestaSolicitar.getAcuse().getError().getCodigo());
			LOG.info("ERROR EN LA RESPUESTA "+ error);
			boolean estadoCambiado = consultaNotificacionTransactional.cambiarEstadoNotificacion(Integer.parseInt(codigoNotificacion), 5, numColegiado);
			if (estadoCambiado) {
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			}else{
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error en BBDD al intentar cambiar el estado de la notificación", numColegiado);
			}
			return null;
		}else {
			String error = ErroresWSNotificacion.convertirTexto(respuestaSolicitar.getAcuse().getError().getCodigo());
			LOG.error("Ha ocurrido un error al solicitar el acuse de notificación por aceptación. Código Error: " + respuestaSolicitar.getAcuse().getError().getCodigo());
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			respuestaSolicitar.setError(true);
			respuestaSolicitar.setMensajesError(Collections.singletonList("Ha ocurrido un error al solicitar el acuse de notificación por aceptación. Código Error: " + respuestaSolicitar.getAcuse().getError().getCodigo()));
			return respuestaSolicitar;
		}
		
		byte[] documentoCustodiado = utilesViafirma.firmarAcuseSS(respuestaSolicitar.getAcuse().getXML(), alias); 
		
		RespuestaNotificacionesSS respuestaEnviar = servicioWebServiceConsultaNotificacion.enviarAcuseNotificacion(Integer.parseInt(rol), null, documentoCustodiado, alias);
		if (respuestaEnviar.getException() != null){
			LOG.info("ERROR EN LA RESPUESTA AL ENVIAR ACUSE DE NOTIFICACION " + respuestaEnviar.getException().getMessage());
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error de comunicación con el Web Service de la Seguridad Social", numColegiado);
			return respuestaEnviar;
		}
		if (respuestaEnviar.getNotificacion().getError().getCodigo() == 0){
			//En caso de que la llamada al WS sea correcta, guardo el PDF
			
			LOG.info("RESPUESTA WS CORRECTA AL ENVIAR ACUSE DE NOTIFICACION ");
			guardarPDFnotificacion(respuestaEnviar.getNotificacion().getCodigoNotificacion(), respuestaEnviar.getNotificacion().getPdfNotificacion(), numColegiado);
			boolean estadoCambiado = consultaNotificacionTransactional.cambiarEstadoNotificacion(respuestaEnviar.getNotificacion().getCodigoNotificacion(), 2, numColegiado);
			if (estadoCambiado) {
				consultaNotificacionTransactional.insertarRespuesta(respuestaEnviar.getNotificacion().getCodigoNotificacion(), "Notificación Aceptada", numColegiado);
			}else{
				consultaNotificacionTransactional.insertarRespuesta(respuestaEnviar.getNotificacion().getCodigoNotificacion(), "Ha ocurrido un error en BBDD al intentar cambiar el estado de la notificación", numColegiado);
			}
		}else if (respuestaEnviar.getNotificacion().getError().getCodigo() != 0){
			String error = ErroresWSNotificacion.convertirTexto(respuestaEnviar.getNotificacion().getError().getCodigo());
			LOG.info("ERROR " + error);
			boolean estadoCambiado = consultaNotificacionTransactional.cambiarEstadoNotificacion(Integer.parseInt(codigoNotificacion), 5, numColegiado);
			if (estadoCambiado) {
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			}else{
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error en BBDD al intentar cambiar el estado de la notificación", numColegiado);
			}
			return null;
		}else {
			String error = ErroresWSNotificacion.convertirTexto(respuestaEnviar.getNotificacion().getError().getCodigo());
			LOG.error("Ha ocurrido un error al enviar el acuse de notificación por aceptación. Código Error: " + respuestaEnviar.getNotificacion().getError().getCodigo());
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			respuestaEnviar.setError(true);
			respuestaEnviar.setMensajesError(Collections.singletonList("Ha ocurrido un error al enviar el acuse de notificación por aceptación. Código Error: " + respuestaEnviar.getNotificacion().getError().getCodigo()));
			return respuestaEnviar;
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public RespuestaNotificacionesSS rechazarNotificaciones(String rol, String codigoNotificacion, String alias, String numColegiado) {
		UtilesViafirma utilesViafirma = new UtilesViafirma();
		RespuestaNotificacionesSS respuestaSolicitar = servicioWebServiceConsultaNotificacion.solicitarAcuseNotificacion(Integer.parseInt(rol),
				null, Integer.parseInt(codigoNotificacion), false, alias);
		if (respuestaSolicitar.getException() != null){
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error de comunicación con el Web Service de la Seguridad Social", numColegiado);
			return respuestaSolicitar;
		}
		if (respuestaSolicitar.getAcuse().getError().getCodigo() == 0){
			//En caso de que la llamada al WS sea correcta, guardo el acuse 
			guardarAcuse(respuestaSolicitar.getAcuse().getCodigoNotificacion(), respuestaSolicitar.getAcuse().getXML(), numColegiado);
			//Insertamos en la tabla resultados
			consultaNotificacionTransactional.insertarRespuesta(respuestaSolicitar.getAcuse().getCodigoNotificacion(), "Acuse solicitado", numColegiado);
		} else if (respuestaSolicitar.getAcuse().getError().getCodigo() != 0){
			String error = ErroresWSNotificacion.convertirTexto(respuestaSolicitar.getAcuse().getError().getCodigo());
			boolean estadoCambiado = consultaNotificacionTransactional.cambiarEstadoNotificacion(Integer.parseInt(codigoNotificacion), 5, numColegiado);
			if (estadoCambiado) {
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			}else{
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error en BBDD al intentar cambiar el estado de la notificación", numColegiado);
			}
			return null;
		}else {
			String error = ErroresWSNotificacion.convertirTexto(respuestaSolicitar.getAcuse().getError().getCodigo());
			LOG.error("Ha ocurrido un error al solicitar el acuse de notificación por rechazo. Código Error: " + respuestaSolicitar.getAcuse().getError().getCodigo());
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			respuestaSolicitar.setError(true);
			respuestaSolicitar.setMensajesError(Collections.singletonList("Ha ocurrido un error al solicitar el acuse de notificación por rechazo. Código Error: " + respuestaSolicitar.getAcuse().getError().getCodigo()));
			return respuestaSolicitar;
		}
		
		byte[] documentoCustodiado = utilesViafirma.firmarAcuseSS(respuestaSolicitar.getAcuse().getXML(), alias); 
		
		RespuestaNotificacionesSS respuestaEnviar = servicioWebServiceConsultaNotificacion.enviarAcuseNotificacion(Integer.parseInt(rol), null, documentoCustodiado, alias);
		if (respuestaEnviar.getException() != null){
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error de comunicación con el Web Service de la Seguridad Social", numColegiado);
			return respuestaEnviar;
		}
		
		if (respuestaEnviar.getNotificacion().getError().getCodigo() == 0) {
			boolean estadoCambiado = consultaNotificacionTransactional.cambiarEstadoNotificacion(respuestaEnviar.getNotificacion().getCodigoNotificacion(), 3, numColegiado);
			if (estadoCambiado) {
				consultaNotificacionTransactional.insertarRespuesta(respuestaEnviar.getNotificacion().getCodigoNotificacion(), "Notificación Rechazada", numColegiado);
			}else{
				consultaNotificacionTransactional.insertarRespuesta(respuestaEnviar.getNotificacion().getCodigoNotificacion(), "Ha ocurrido un error en BBDD al intentar cambiar el estado de la notificación", numColegiado);
			}
		}else if (respuestaEnviar.getNotificacion().getError().getCodigo() != 0){
			String error = ErroresWSNotificacion.convertirTexto(respuestaEnviar.getNotificacion().getError().getCodigo());
			boolean estadoCambiado = consultaNotificacionTransactional.cambiarEstadoNotificacion(Integer.parseInt(codigoNotificacion), 5, numColegiado);
			if (estadoCambiado) {
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			}else{
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error en BBDD al intentar cambiar el estado de la notificación", numColegiado);
			}
			return null;
		} else {
			String error = ErroresWSNotificacion.convertirTexto(respuestaEnviar.getNotificacion().getError().getCodigo());
			LOG.error("Ha ocurrido un error al enviar el acuse de notificación por rechazo. Código Error: " + respuestaEnviar.getNotificacion().getError().getCodigo());
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			respuestaEnviar.setError(true);
			respuestaEnviar.setMensajesError(Collections.singletonList("Ha ocurrido un error al enviar el acuse de notificación por rechazo. Código Error: " + respuestaEnviar.getNotificacion().getError().getCodigo()));
			return respuestaEnviar;
		}
		
		return null;
		
	}
	
	@Override
	public RespuestaNotificacionesSS imprimirNotificaciones(String rol, String codigoNotificacion, String alias, String numColegiado) {
		// EN caso contrario llamamos al WS
		RespuestaNotificacionesSS respuesta = servicioWebServiceConsultaNotificacion.verNotificacionAceptada(Integer.parseInt(rol), null, Integer.parseInt(codigoNotificacion), alias);
		if (respuesta.getException() != null){
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error de comunicación con el Web Service de la Seguridad Social", numColegiado);
			return respuesta;
		}
		if (respuesta.getNotificacion().getError().getCodigo() == 0) {
			//En caso de que la llamada al WS sea correcta, guardo el PDF
			guardarPDFnotificacion(respuesta.getNotificacion().getCodigoNotificacion(), respuesta.getNotificacion().getPdfNotificacion(), numColegiado);
		}else if (respuesta.getNotificacion().getError().getCodigo() != 0){
			String error = ErroresWSNotificacion.convertirTexto(respuesta.getNotificacion().getError().getCodigo());
			boolean estadoCambiado = consultaNotificacionTransactional.cambiarEstadoNotificacion(Integer.parseInt(codigoNotificacion), 5, numColegiado);
			if (estadoCambiado) {
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			}else{
				consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), "Ha ocurrido un error en BBDD al intentar cambiar el estado de la notificación", numColegiado);
			}
			return null;
		}else{
			String error = ErroresWSNotificacion.convertirTexto(respuesta.getNotificacion().getError().getCodigo());
			LOG.error("Ha ocurrido un error al solicitar la impresión de la notificación. Código Error: " + respuesta.getNotificacion().getError().getCodigo());
			consultaNotificacionTransactional.insertarRespuesta(Integer.parseInt(codigoNotificacion), error, numColegiado);
			respuesta.setError(true);
			respuesta.setMensajesError(Collections.singletonList("Ha ocurrido un error al solicitar la impresión de la notificación. Código Error: " + respuesta.getNotificacion().getError().getCodigo()));
			return respuesta;
		}
		
		return null;
		
	}
	
	@Override
	public RespuestaNotificacionesSS recuperarNotificaciones(String alias, BigDecimal numColegiado, BigDecimal idContrato) {
		boolean error = false;
		RespuestaNotificacionesSS respuesta = new RespuestaNotificacionesSS();
		List<NotificacionSSVO> listaAuxiliar = new ArrayList<NotificacionSSVO>();
		LOG.info("Proceso " + ProcesosEnum.NOTIFICACIONES_SS.getNombreEnum() + " -- Entramos a recuperar listado de notificaciones ");
		RespuestaNotificacionesSS respuestaRolPropio = servicioWebServiceConsultaNotificacion.recuperaListadoNotificacionesByRol(1, null, alias, numColegiado);
		if (respuestaRolPropio.getException() != null){
			error = true;
			respuesta.setException(respuestaRolPropio.getException());
			LOG.error("Ha ocurrido un error al solicitar la actualización de notificaciones");
		}else{
			if (respuestaRolPropio.getListaNotificacionSS() != null && respuestaRolPropio.getListaNotificacionSS().size() > 0){
				listaAuxiliar.addAll(respuestaRolPropio.getListaNotificacionSS());
				respuesta.setListaNotificacionSS(listaAuxiliar);
			}
		}
		RespuestaNotificacionesSS respuestaRolAutorizadoRed = servicioWebServiceConsultaNotificacion.recuperaListadoNotificacionesByRol(2, null, alias, numColegiado);
		if (respuestaRolAutorizadoRed.getException() != null){
			error = true;
			respuesta.setException(respuestaRolAutorizadoRed.getException());
			LOG.error("Ha ocurrido un error al solicitar la actualización de notificaciones");
		}else{
			if (respuestaRolAutorizadoRed.getListaNotificacionSS() != null && respuestaRolAutorizadoRed.getListaNotificacionSS().size() > 0){
				listaAuxiliar.addAll(respuestaRolAutorizadoRed.getListaNotificacionSS());
				respuesta.setListaNotificacionSS(listaAuxiliar);
			}
		}
		RespuestaNotificacionesSS respuestaRolApoderdante = servicioWebServiceConsultaNotificacion.recuperaListadoNotificacionesByRol(3, null, alias, numColegiado);
		if (respuestaRolApoderdante.getException() != null){
			error = true;
			respuesta.setException(respuestaRolApoderdante.getException());
			LOG.error("Ha ocurrido un error al solicitar la actualización de notificaciones");
		}else{
			if (respuestaRolApoderdante.getListaNotificacionSS() != null && respuestaRolApoderdante.getListaNotificacionSS().size() > 0){
				listaAuxiliar.addAll(respuestaRolApoderdante.getListaNotificacionSS());
				respuesta.setListaNotificacionSS(listaAuxiliar);
			}
		}
		
		//Compruebo la propertie para descontar o no los creditos
		String creditos = gestorPropiedades.valorPropertie(SEGSOL_CREDITOS);
		if (!error && "SI".equals(creditos)){
			descontarCreditos(numColegiado, idContrato);
		}
		
		return respuesta;
	}

	@Override
	public ResultBean aceptarServicio(BigDecimal idContrato, String numColegiado) {
		ResultBean result;
		try {
			result = servicioPermisos.asignarPermiso(idContrato, "SS2", "OEGAM_SEGSOC", numColegiado, 0L);
		} catch (RuntimeException e) {
			LOG.error("Error al dar permisos en la seguridad social", e);
			result = new ResultBean(Boolean.TRUE, "No se pudo asignar el permiso");
		}
		return result;
	}

	private void guardarPDFnotificacion(int codigoNotificacion, byte[] pdf, String numColegiado) {
		LOG.info("GUARDAR NOTIFICACION: " + codigoNotificacion + "DEL COLEGIADO: "+ numColegiado);
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento("NOTIFICACIONES_SS");
		fichero.setSubTipo("PDF_ACUSE");
		fichero.setSobreescribir(true);
		fichero.setExtension(".pdf");
		fichero.setFecha(utilesFecha.getFechaFracionada(new Date()));
         
		fichero.setNombreDocumento("Notificacion_" + Integer.toString(codigoNotificacion) + "_" + numColegiado);
			

		if (Base64.isArrayByteBase64(pdf)) {
			pdf = Base64.decodeBase64(pdf);
		}
		fichero.setFicheroByte(pdf);
		try {
			gestorDocumentos.guardarFichero(fichero);
			consultaNotificacionTransactional.insertarRespuesta(codigoNotificacion, "PDF Custodiado", numColegiado);
		} catch (OegamExcepcion e) {
			LOG.error("Se ha obtenido un error guardando el pdf de la notificación: "+ codigoNotificacion, e);
		}
		
	}
	
	
	private void guardarAcuse(int codigoNotificacion, byte[] xml, String numColegiado) {
		LOG.info("GUARDAR ACUSE: " + codigoNotificacion + "DEL COLEGIADO: "+ numColegiado);
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento("NOTIFICACIONES_SS");
		fichero.setSubTipo("XML_ACUSE");
		fichero.setSobreescribir(true);
		fichero.setExtension(".xml");
		fichero.setFecha(utilesFecha.getFechaFracionada(new Date()));
         
		fichero.setNombreDocumento("Notificacion_" + Integer.toString(codigoNotificacion) + "_" + numColegiado);
			

		if (Base64.isArrayByteBase64(xml)) {
			xml = Base64.decodeBase64(xml);
		}
		fichero.setFicheroByte(xml);
		try {
			gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			LOG.error("Se ha obtenido un error guardando el acuse de la notificación: "+ codigoNotificacion, e);
		}
	}
	
	private boolean descontarCreditos(BigDecimal numColegiado, BigDecimal idContrato) {
		ModeloCreditosTrafico modelo = new ModeloCreditosTrafico();
		HashMap<String, Object> map = modelo.descontarCreditos(idContrato.toString(), new BigDecimal(1), "SSCN", numColegiado);
		ResultBean res = (ResultBean) map.get(ConstantesPQ.RESULTBEAN);
		if(res.getError()){
			LOG.error("ERROR DESCONTAR CREDITOS");
			LOG.error("CONTRATO: " + idContrato);
			LOG.error("ID_USUARIO: " + numColegiado);
		}else{
			try{
				ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
				if (servicioCreditoFacturado != null ){
					servicioCreditoFacturado.anotarGasto(new Integer(1), ConceptoCreditoFacturado.SSCN, idContrato.longValue(), "SSCN", "Consulta_SS");
				}
			}catch(Exception e){
				LOG.error("Se ha producido un error al guardar historico de creditos para la consulta de notificaciones de la Seguridad Social ", e);
			}
		}
		return !res.getError();		
	}
	
	private String crearTexto(List<NotificacionSSVO> listaNuevas, List<NotificacionSSVO> listaPendientes){
		StringBuilder sb = new StringBuilder();
		sb.append("<span style=\"font-size:12pt;font-family:Tunga;margin-left:20px;\">");
		sb.append("<br>Desde la Oficina Electrónica de Gestión Administrativa (OEGAM), le comunicamos que para el Colegiado: " +listaNuevas.get(0).getId().getNumColegiado()+ ", están disponibles nuevas notificaciones para ser descargadas:<br>");
		sb.append("<br>");
		sb.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\">");
		sb.append("<tr><th>Destinatario</th><th>Razón Social</th><th>Fecha Fin Disponibilidad</th></tr>");
		for (NotificacionSSVO notificacion : listaNuevas) {
			sb.append("<tr>");
			sb.append("<td>").append(notificacion.getDestinatario()).append("</td>");
			sb.append("<td>").append(notificacion.getNombreAppRazonSocial()).append("</td>");
			sb.append("<td>").append(utilesFecha.formatoFechaHorasMinutos(notificacion.getFechaFinDisponibilidad())).append("</td>");
			sb.append("</tr>");
		}
	
		sb.append("</table>");
		sb.append("</span>");
		
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<br>");
		
		//eliminar nuevas notificaciones de las notificaciones pendientes
		List<NotificacionSSVO> listaSinDuplicados = eliminaNuevasDePendientes(listaNuevas, listaPendientes);
		if (listaSinDuplicados.size() > 0){
			sb.append("<span style=\"font-size:12pt;font-family:Tunga;margin-left:20px;\">");
			sb.append("<br>Además de las notificaciones mostradas en el listado anterior, le recordamos que tiene disponible para ser descargadas las siguientes notificaciones:<br>");
			sb.append("<br>");
			sb.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\">");
			sb.append("<tr><th>Destinatario</th><th>Razón Social</th><th>Fecha Fin Disponibilidad</th></tr>");
			for (NotificacionSSVO notificacion : listaSinDuplicados) {
				sb.append("<tr>");
				sb.append("<td>").append(notificacion.getDestinatario()).append("</td>");
				sb.append("<td>").append(notificacion.getNombreAppRazonSocial()).append("</td>");
				sb.append("<td>").append(utilesFecha.formatoFechaHorasMinutos(notificacion.getFechaFinDisponibilidad())).append("</td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			sb.append("</span>");
			
			sb.append("<br>");
			sb.append("<br>");
			sb.append("<br>");
		}
			
		
		return sb.toString();
	}
	
	private List<NotificacionSSVO> eliminaNuevasDePendientes(List<NotificacionSSVO> listaNuevas, List<NotificacionSSVO> listaPendientes){
		List<NotificacionSSVO> mergeList = new ArrayList<NotificacionSSVO>();
		mergeList.addAll(listaPendientes);
		for (NotificacionSSVO notificacionNueva : listaNuevas) {
			for (NotificacionSSVO notificacionPendiente : listaPendientes){
				if (notificacionNueva.getId().getCodigo()==notificacionPendiente.getId().getCodigo()){
					mergeList.remove(notificacionPendiente);
				}
			}
		}
		return mergeList;
	}
	
	public UtilesViafirma getUtilesViafirma() {
		if(utilesViafirma == null){
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}

	public void setUtilesViafirma(UtilesViafirma utilesViafirma) {
		this.utilesViafirma = utilesViafirma;
	}
	
}
