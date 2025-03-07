package org.gestoresmadrid.oegam2comun.modelo600_601.model.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.ErroresWSModelo600601;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoTramiteModelos;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioResultadoModelo600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioWebServiceModelo600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelos;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.opti.presentacion.ws.SoapModelo600601WSHandler;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.gescogroup.blackbox.ws.SoapTramitarBtvWSHandler;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import opti.presentacion.domain.xsd.Peticion;
import opti.presentacion.domain.xsd.Respuesta;
import opti.presentacion.services.PresentacionServiceLocator;
import opti.presentacion.services.PresentacionServicePortType;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceModelo600601Impl implements ServicioWebServiceModelo600601{
	
	private static final long serialVersionUID = -3921254536442335450L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceModelo600601Impl.class);
	
	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;
	
	@Autowired
	private ServicioResultadoModelo600601 servicioResultadoModelo600601;
	
	@Autowired
	private ServicioCredito servicioCredito;
	
	@Autowired
	private ServicioNotificacion servicioNotificacion;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public void guardarResultadoSubSanableFinalizado(BigDecimal numExpediente, String respuesta) {
		try {
			Modelo600_601VO modelo600601BBDD = servicioModelo600_601.getModeloVOPorNumExpediente(numExpediente, false);
			if(modelo600601BBDD != null){
				servicioResultadoModelo600601.guardarResultadoSubSanable(modelo600601BBDD, Calendar.getInstance().getTime(), respuesta);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el resultado subsanable de la presentación, error ",e, numExpediente.toString());
		}
	}

	@Override
	@Transactional
	public ResultadoModelos procesarSolicitud(ColaBean solicitud) {
		ResultadoModelos resultado = null; 
		String identificadorDoc = null;
		Modelo600_601VO modelo600601BBDD = null;
		try {
			modelo600601BBDD = servicioModelo600_601.getModeloVOPorNumExpediente(solicitud.getIdTramite(), false);
			if(modelo600601BBDD != null){
				ResultadoModelos resultLlamadaWS = llamadaWS(solicitud.getXmlEnviar(),modelo600601BBDD);
				if(resultLlamadaWS.getExcepcion() != null || resultLlamadaWS.getErrorSubsanable()){
					return resultLlamadaWS;
				}else{
					ResultBean resultGuardarResultado = servicioResultadoModelo600601.guardarResultadoOk(modelo600601BBDD,resultLlamadaWS);
					if(resultGuardarResultado.getError()){
						resultado = new ResultadoModelos(true, resultGuardarResultado.getListaMensajes().get(0));
					}else{
						identificadorDoc = (String) resultGuardarResultado.getAttachment("identificadorDoc");
						resultado = new ResultadoModelos(false);
						if(identificadorDoc != null && !identificadorDoc.isEmpty()){
							guardarCartaDePagoYDiligencia(resultLlamadaWS,identificadorDoc,modelo600601BBDD.getNumExpediente());
							solicitud.setRespuesta("Ejecución Correcta.");
						}
						if(resultLlamadaWS.isError()){
							resultado.setMensajeError("Error en la presentación del modelo, error: " + resultLlamadaWS.getRespuesta());
							solicitud.setRespuesta("Error en la presentación del modelo, error: " + resultLlamadaWS.getRespuesta());
							resultado.setError(true);
						}
					}
				}
			}else{
				resultado = new ResultadoModelos(true, "No existe datos del modelo para su presentacion.");
			}
		} catch (Exception e) {
			resultado = new ResultadoModelos(true, e);
		} catch (OegamExcepcion e) {
			resultado = new ResultadoModelos(true, new Exception(e));
		}
		if(resultado != null && resultado.getExcepcion() != null){
			if(identificadorDoc != null && !identificadorDoc.isEmpty()){
				borrarDocPresentacion(modelo600601BBDD.getNumExpediente(),identificadorDoc);
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	private void borrarDocPresentacion(BigDecimal numExpediente, String identificadorDoc) {
		String nombreCartaPago = ServicioWebServiceModelo600601.NOMBRE_FICH_CARTA_PAGO + "_" + identificadorDoc;
		String nombreDiligencia = ServicioWebServiceModelo600601.NOMBRE_FICH_CARTA_PAGO + "_" + identificadorDoc;
		try {
			gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
					Utilidades.transformExpedienteFecha(numExpediente),nombreCartaPago);
			gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
					Utilidades.transformExpedienteFecha(numExpediente),nombreDiligencia);
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de borrar la carta de pago y la diligencia");
		}
	}

	private void guardarCartaDePagoYDiligencia(ResultadoModelos resultLlamadaWS, String identificadorDoc, BigDecimal numExpediente) throws OegamExcepcion {
		String nombreCartaPago = ServicioWebServiceModelo600601.NOMBRE_FICH_CARTA_PAGO + "_" + identificadorDoc;
		String nombreDiligencia = ServicioWebServiceModelo600601.NOMBRE_FICH_DILIGENCIA + "_" + identificadorDoc;
		
		gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
				Utilidades.transformExpedienteFecha(numExpediente),nombreCartaPago, ConstantesGestorFicheros.EXTENSION_PDF, (byte[]) resultLlamadaWS.getAttachment("cartaPago"));
		
		gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.PDF_MODELOS_600_601, 
				Utilidades.transformExpedienteFecha(numExpediente),nombreDiligencia, ConstantesGestorFicheros.EXTENSION_PDF, (byte[]) resultLlamadaWS.getAttachment("diligencia"));
	}

	private ResultadoModelos llamadaWS(String xmlEnviar, Modelo600_601VO modelo600601VO) {
		ResultadoModelos resultadoWS = null;
		try {
			byte[] escritura = null;
			String[] datosEnvio = xmlEnviar.split(" ");
			String xml = recogerXml(datosEnvio[0], modelo600601VO.getNumExpediente());
			if (datosEnvio.length > 1){
				escritura = recogerEscritura(datosEnvio[1], modelo600601VO.getNumExpediente());
			}
			if(xml != null && !xml.isEmpty()){
				Peticion peticionWS = generarPeticionWS(modelo600601VO,xml,escritura);
				if(peticionWS != null){
					Respuesta respuestaWS = getEndPoint(modelo600601VO.getNumExpediente()).presentar(peticionWS);
					log.info("------- Recibida Respuesta del WS de presentacion de los modelos 600/601------" );
					if(respuestaWS != null){
						resultadoWS = gestionarRespuestaWS(respuestaWS);
					}else{
						resultadoWS = new ResultadoModelos(true, "Ha sucedido un error a la hora de generar la petición.");
					}
				}else{
					resultadoWS = new ResultadoModelos(true, "Ha sucedido un error a la hora de generar la petición.");
				}
			}else{
				resultadoWS = new ResultadoModelos(true, "No existe el xml de presentacion.");
			}
		} catch (Exception e) {
			resultadoWS = new ResultadoModelos(true, e);
		}
		return resultadoWS;
	}

	private ResultadoModelos gestionarRespuestaWS(Respuesta respuestaWS) {
		ResultadoModelos resultado = new ResultadoModelos();
		if(ErroresWSModelo600601.Error000.getValorEnum().equals(respuestaWS.getCodigoDelResultado())){
			resultado.setFechaEjecucion(Calendar.getInstance().getTime());
			resultado.addAttachment("nccm", respuestaWS.getNccm());
			resultado.addAttachment("cso", respuestaWS.getCso());
			resultado.addAttachment("expedienteCam", respuestaWS.getExpediente());
			resultado.addAttachment("fechaPresentacion", respuestaWS.getFechaDePresentacion().getTime());
			resultado.addAttachment("cartaPago", respuestaWS.getCartaPago());
			resultado.addAttachment("diligencia", respuestaWS.getDiligencia());
			resultado.addAttachment("justificante", respuestaWS.getJustificante());
		}else if(ErroresWSModelo600601.Error199.getValorEnum().equals(respuestaWS.getCodigoDelResultado())){
			resultado.setError(true);
			resultado.setErrorSubsanable(true);
		}else{
			resultado.setFechaEjecucion(Calendar.getInstance().getTime());
			resultado.setError(true);
		}
		resultado.setRespuesta(rellenarRespuesta(respuestaWS.getCodigoDelResultado()));
		resultado.setErrorWS(ErroresWSModelo600601.convertirValor(respuestaWS.getCodigoDelResultado()));
		return resultado;
	}

	private String rellenarRespuesta(String codigoDelResultado) {
		ErroresWSModelo600601 errorWS = ErroresWSModelo600601.convertirValor(codigoDelResultado);
		String mensaje = null;
		if(errorWS != null){
			mensaje = errorWS.getValorEnum() + ", " + errorWS.getNombreEnum();
		}
		return mensaje;
	}

	private PresentacionServicePortType getEndPoint(BigDecimal numExpediente) throws ServiceException {
		String address = gestorPropiedades.valorPropertie(ServicioWebServiceModelo600601.SERVICIO_FORMULARIO600601_URL);
		if (log.isDebugEnabled()) {
			log.info("Inicializando cliente del WS para la presentación telemática de los modelos 600/601 " + address);
		}
		log.info ("Antes de llamar usando el protocolo " + System.getProperty("https.protocols"));
		
		//if ("TLSv1".equals(System.getProperty("https.protocols"))){
		//	System.setProperty("https.protocols", "TLSv1.2");
		//	log.info ("CAMBIO EL TIPO DE PROTOCOLO");			
		//}
		
		//log.info ("Despues de llamar usando el protocolo " + System.getProperty("https.protocols"));
				
		PresentacionServiceLocator locator = new PresentacionServiceLocator();
		locator.setPresentacionServiceHttpsSoap11EndpointEndpointAddress(address);
		// Añadimos los handlers de firmado y logs
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(address, locator.getPresentacionServiceHttpsSoap11EndpointWSDDServiceName());
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = locator.getHandlerRegistry().getHandlerChain(portName);
		// Handler de logs
		HandlerInfo logHandlerInfo = new HandlerInfo();
		logHandlerInfo.setHandlerClass(SoapModelo600601WSHandler.class);
		Map<String, Object> filesConfig = new HashMap<String, Object>();
		filesConfig.put(SoapTramitarBtvWSHandler.PROPERTY_KEY_ID, numExpediente);
		logHandlerInfo.setHandlerConfig(filesConfig);
		list.add(logHandlerInfo);
		
		return locator.getPresentacionServiceHttpsSoap11Endpoint();
	}

	private Peticion generarPeticionWS(Modelo600_601VO modelo600601VO, String xml, byte[] escritura) {
		Peticion peticion = null;
		String numCuenta = null;
		if(modelo600601VO.getDatosBancarios() != null && modelo600601VO.getDatosBancarios().getIdDatosBancarios() != null){
			numCuenta = servicioDatosBancariosFavoritos.descifrarNumCuenta(modelo600601VO.getDatosBancarios().getDatosBancarios());
			peticion = new Peticion(numCuenta,escritura,Calendar.getInstance(),modelo600601VO.getDatosBancarios().getNifPagador(),ServicioWebServiceModelo600601.GRUPO_ORIGEN,xml.getBytes());
		}else{
			numCuenta = servicioDatosBancariosFavoritos.descifrarNumCuenta(modelo600601VO.getNumCuentaPago());
			peticion = new Peticion(numCuenta,escritura,Calendar.getInstance(), modelo600601VO.getNifPagador(), ServicioWebServiceModelo600601.GRUPO_ORIGEN, xml.getBytes());
		}
		return peticion;
	}

	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.FORMULARIOS_CAM, 
					fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				byte[] by = IOUtils.toByteArray(fis);
				xml = new String (by, ServicioWebServiceModelo600601.ENCODING_XML);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		}
		return xml;
	}
	
	private byte[] recogerEscritura(String escritura, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		byte[] b = null;
		String[] nombreFichero = escritura.split("\\.");
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MODELO_600_601, ConstantesGestorFicheros.FORMULARIOS_CAM, 
					fecha, nombreFichero[0], "."+nombreFichero[1]);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				b = IOUtils.toByteArray(fis);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		}
		return b;
	}

	@Override
	@Transactional
	public void cambiarEstadoModelo(BigDecimal numExpediente, BigDecimal nuevoEstado, BigDecimal idUsuario) {
		try {
			servicioModelo600_601.cambiarEstado(numExpediente, nuevoEstado, idUsuario,true);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del modelo, error: ",e, numExpediente.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	@Override
	@Transactional
	public void tratarDevolverCredito(BigDecimal numExpediente, BigDecimal idUsuario) {
		int creditos = 0;
		if("SI".equalsIgnoreCase(ServicioWebServiceModelo600601.cobrarCreditos)){
			creditos = servicioCredito.cantidadCreditosAnotados(numExpediente.toString(), ConceptoCreditoFacturado.PMD, ConceptoCreditoFacturado.DPMD);
		}
		if(creditos > 0){
			Modelo600_601VO modelo600_601BBDD = servicioModelo600_601.getModeloVOPorNumExpediente(numExpediente, false);
			String tipoTramite = null;
			if(Modelo.Modelo600.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
				tipoTramite = TipoTramiteModelos.Modelo600.getValorEnum();
			}else if(Modelo.Modelo601.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
				tipoTramite = TipoTramiteModelos.Modelo601.getValorEnum();
			}
			servicioCredito.devolverCreditos(tipoTramite, new BigDecimal(modelo600_601BBDD.getIdContrato()), BigDecimal.ONE.intValue(), idUsuario, ConceptoCreditoFacturado.PMD, numExpediente.toString());
		}
	}
	
	@Override
	@Transactional
	public void crearNotificacion(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estado) {
		Modelo600_601VO modelo600_601BBDD = servicioModelo600_601.getModeloVOPorNumExpediente(numExpediente, false);
		String tipoTramite = null;
		if(Modelo.Modelo600.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
			tipoTramite = TipoTramiteModelos.Modelo600.getValorEnum();
		}else if(Modelo.Modelo601.getValorEnum().equals(modelo600_601BBDD.getModelo().getId().getModelo())){
			tipoTramite = TipoTramiteModelos.Modelo601.getValorEnum();
		}
		NotificacionDto notifDto = new NotificacionDto();
		notifDto.setEstadoAnt(new BigDecimal(EstadoModelos.Pendiente_Presentacion.getValorEnum()));
		notifDto.setEstadoNue(estado);
		notifDto.setDescripcion(ServicioWebServiceModelo600601.NOTIFICACION);
		notifDto.setTipoTramite(tipoTramite);
		notifDto.setIdTramite(numExpediente);
		notifDto.setIdUsuario(idUsuario.longValue());
		servicioNotificacion.crearNotificacion(notifDto);
		
	}
	
}
