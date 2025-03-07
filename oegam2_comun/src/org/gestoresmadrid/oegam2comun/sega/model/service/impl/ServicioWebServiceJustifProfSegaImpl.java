package org.gestoresmadrid.oegam2comun.sega.model.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceJustifProfSega;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoJustificanteProfesionalBean;
import org.gestoresmadrid.oegamComun.colegio.view.dto.ColegioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import net.gestores.sega.pps.PpsError;
import net.gestores.sega.pps.PpsReturn;
import net.gestores.sega.pps.ProfessionalProofServiceLocator;
import net.gestores.sega.pps.ProfessionalProofWS;
import net.gestores.sega.pps.ProfessionalProofWSBindingStub;
import net.gestores.sega.pps.VppsReturn;
import net.gestores.sega.pps.ws.SoapJustificantesProfSegaWSHandler;
import net.gestores.sega.pps.ws.SoapVerificacionJustProfSegaWSHandler;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebServiceJustifProfSegaImpl implements ServicioWebServiceJustifProfSega{

	private static final long serialVersionUID = 1489522259975386132L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceJustifProfSegaImpl.class);

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	@Override
	public ResultadoJustificanteProfesionalBean procesarSolicitudJustificanteProf(ColaBean solicitud) {
		ResultadoJustificanteProfesionalBean resultadoWS = new ResultadoJustificanteProfesionalBean(Boolean.FALSE);
		try {
			JustificanteProfVO justificanteProfVO = null;
			if(solicitud.getXmlEnviar().contains(ProcesosEnum.JUSTIFICANTES_SEGA.getNombreEnum())){
				justificanteProfVO = servicioJustificanteProfesional.getJustificanteProfesionalPorIDInternoVO(solicitud.getIdTramite().longValue());
			}else{
			 justificanteProfVO = servicioJustificanteProfesional.getJustificanteProfesionalPorNumExpediente(solicitud.getIdTramite(), Boolean.TRUE, new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));
			}
			if(justificanteProfVO != null){
				ColegioDto colegioDto = servicioJustificanteProfesional.getColegioContrato(justificanteProfVO.getTramiteTrafico().getContrato().getIdContrato());
				if(colegioDto != null){
					String xml = recogerXml(solicitud.getXmlEnviar(),justificanteProfVO.getNumExpediente());
					if(xml != null && !xml.isEmpty()){
						//System.out.println(utiles.doBase64Encode(xml.getBytes(UTF_8)));
						PpsReturn respuestaWS =  getStubJustificante(justificanteProfVO.getNumExpediente()).
								issueProfessionalProof(colegioDto.getCif(), colegioDto.getCif(), utiles.doBase64Encode(xml.getBytes(UTF_8)));
						log.info("-------" + ProcesosEnum.JUSTIFICANTES_SEGA.toString() + " Recibida Respuesta WS ------" );
						if(respuestaWS != null){
							resultadoWS = gestionarResultadoWS(respuestaWS,justificanteProfVO.getIdJustificanteInterno(),justificanteProfVO.getNumExpediente());
						}else{
							resultadoWS = new ResultadoJustificanteProfesionalBean(true, "Ha sucedido un error a la hora realizar la peticion del justificante Profesional.");
						}
					} else {
						resultadoWS = new ResultadoJustificanteProfesionalBean(true,"No existen datos del xml de envio.");
					}
				} else {
					resultadoWS = new ResultadoJustificanteProfesionalBean(true,"No existen datos del colegio asociado al justificante.");
				}
			} else {
				resultadoWS = new ResultadoJustificanteProfesionalBean(true,"No existen datos del justificante.");
			}
		} catch (Throwable e) {
			resultadoWS = new ResultadoJustificanteProfesionalBean((Exception)e);
		}
		return resultadoWS;
	}
	
	private ResultadoJustificanteProfesionalBean gestionarResultadoWS(PpsReturn respuestaWS, Long idJustificanteInterno, BigDecimal numExpediente) {
		ResultadoJustificanteProfesionalBean resultado = new ResultadoJustificanteProfesionalBean(Boolean.FALSE);
		if(respuestaWS.getErrors() != null && respuestaWS.getErrors().length > 0){
			String mensajeError = null;
			for(PpsError pproofError : respuestaWS.getErrors()){
				if("PROOFI00115".equals(pproofError.getCode()) || "PROOFI00A04".equals(pproofError.getCode())){
					resultado.setExcepcion(new Exception(pproofError.getCode() + " - " + pproofError.getMessage()));
				} else{
					mensajeError = pproofError.getCode() + " - " + pproofError.getMessage() + "; ";
				}
			}
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError(mensajeError);
		} else {
			if(respuestaWS.getProofNumber() == null || respuestaWS.getProofNumber().isEmpty()){
				resultado.setExcepcion(new Exception("El WS no ha devuelto id de justificante"));
			} else if(respuestaWS.getProfessionalProofPdf() == null){
				resultado.setExcepcion(new Exception("El WS no ha devuelto justificante pdf"));
			} else {
				ResultBean resultJustfProf = servicioJustificanteProfesional.guardarJustifProfWS(idJustificanteInterno, numExpediente, new BigDecimal(respuestaWS.getProofNumber()),
						respuestaWS.getProfessionalProofPdf());
				if(resultJustfProf.getError()){
					resultado.setExcepcion(new Exception(resultJustfProf.getMensaje()));
				} else {
					resultado.setRespuesta("Ejecucion correcta");
				}
			}
		}
		resultado.setNumExpediente(numExpediente.toString());
		return resultado;
	}

	private ProfessionalProofWSBindingStub getStubJustificante(BigDecimal numExpediente) throws MalformedURLException, ServiceException{
		ProfessionalProofWSBindingStub stubJustificanteProf = null;
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT_PROP_JUSTIFICANTES);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_JUSTIFICANTES_PRO));
		ProfessionalProofWS professionalProofWS = null;
		if(miUrl != null){
			ProfessionalProofServiceLocator proofServiceLocator = new ProfessionalProofServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),proofServiceLocator.getProfessionalProofServiceWSDDServiceName());
			
			
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = proofServiceLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapJustificantesProfSegaWSHandler.class);
			
			Map<String, Object> filesConfig = new HashMap<String, Object>();
			filesConfig.put(SoapJustificantesProfSegaWSHandler.PROPERTY_KEY_ID, numExpediente);
			logHandlerInfo.setHandlerConfig(filesConfig);

			list.add(logHandlerInfo);
			
			professionalProofWS = proofServiceLocator.getProfessionalProofService(miUrl);
			stubJustificanteProf = (ProfessionalProofWSBindingStub) professionalProofWS;
			stubJustificanteProf.setTimeout(Integer.parseInt(timeOut));
		}
		return stubJustificanteProf;
	}
	
	private String recogerXml(String xmlEnviar, BigDecimal numExpediente) {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.JUSTIFICANTES, ConstantesGestorFicheros.ENVIO, 
					fecha, xmlEnviar, ConstantesGestorFicheros.EXTENSION_XML);
			if(ficheroAenviar != null && ficheroAenviar.getFile() != null){
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				byte[] by = IOUtils.toByteArray(fis);
				xml = new String (by, UTF_8);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, numExpediente.toString());
		}
		return xml;
	}
	
	@Override
	public void cambiarEstadoJustificanteProfSolicitud(BigDecimal idJustificanteInterno, EstadoJustificante estadoNuevo, BigDecimal idUsuario) {
		if(idJustificanteInterno != null) {
			servicioJustificanteProfesional.cambiarEstadoConEvolucion(idJustificanteInterno.longValue(),estadoNuevo,idUsuario);
		}
	}
	
	@Override
	public void cambiarEstadoJustificanteProfSolicitudSega(BigDecimal numExpediente,EstadoJustificante estadoAnterior, EstadoJustificante estadoNuevo, BigDecimal idUsuario) {
		if(numExpediente != null) {
			servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(numExpediente,estadoAnterior,estadoNuevo,idUsuario);
		}
	}
	
	@Override
	public void tratarDevolverCredito(BigDecimal idJustificanteInterno,	BigDecimal idUsuario) {
		if(idJustificanteInterno != null){
			servicioJustificanteProfesional.devolverCreditos(idJustificanteInterno.longValue(),idUsuario);
		}
	}
	
	@Override
	public void tratarDevolverCreditoSega(BigDecimal numExpediente,	BigDecimal idUsuario) {
		if(numExpediente != null){
			servicioJustificanteProfesional.devolverCreditosSega(numExpediente,idUsuario, new BigDecimal(EstadoJustificante.Finalizado_Con_Error.getValorEnum()));
		}
	}
	
	@Override
	public ResultadoJustificanteProfesionalBean verificacionJustificanteProf(ColaBean solicitud) {
		ResultadoJustificanteProfesionalBean resultadoWS = new ResultadoJustificanteProfesionalBean(Boolean.FALSE);
		try {
			JustificanteProfVO justificanteProfVO = servicioJustificanteProfesional.getJustificanteProfesionalPorIDInternoVO(solicitud.getIdTramite().longValue());
			if(justificanteProfVO != null){
				if(justificanteProfVO.getCodigoVerificacion() != null && !justificanteProfVO.getCodigoVerificacion().isEmpty()){
					VppsReturn respuestaWS =  getStubVerificacionJustificante(justificanteProfVO.getNumExpediente()).verifyProfessionalProof(justificanteProfVO.getCodigoVerificacion());
					log.info("-------" + ProcesosEnum.VERIFICACIONJPROFSEGA.toString() + " Recibida Respuesta WS ------" );
					if(respuestaWS != null){
						resultadoWS = gestionarResultadoVerificacionWS(respuestaWS,justificanteProfVO.getIdJustificanteInterno(),justificanteProfVO.getNumExpediente());
					}else{
						resultadoWS.setError(Boolean.TRUE);
						resultadoWS.setMensajeError("Ha sucedido un error a la hora realizar la peticion del justificante Profesional.");
					}
				} else {
					resultadoWS.setError(Boolean.TRUE);
					resultadoWS.setMensajeError("No se puede verificar el justificante con idInterno " + justificanteProfVO.getIdJustificanteInterno() + " porque no se ha informado el CVS");
				}
			} else {
				resultadoWS.setError(Boolean.TRUE);
				resultadoWS.setMensajeError("No existen datos del justificante.");
			}
		} catch (Throwable e) {
			resultadoWS.setExcepcion(new Exception(e.toString()));
		}
		return resultadoWS;
	}

	private ResultadoJustificanteProfesionalBean gestionarResultadoVerificacionWS(VppsReturn respuestaWS, Long idJustificanteInterno, BigDecimal numExpediente) {
		ResultadoJustificanteProfesionalBean resultado = new ResultadoJustificanteProfesionalBean(Boolean.FALSE);
		if(respuestaWS == null){
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(new Exception("Ha sucedido un error en la respuesta del WS de VERIFICACIONJPROF_SEGA y está se encuentra vacia"));
		} else {
			if(respuestaWS.isVerificationSuccessful()){
				if(respuestaWS.getErrors() != null && respuestaWS.getErrors().length > 0){
					for(PpsError errorWS : respuestaWS.getErrors()){
						resultado.addMensajeLista(errorWS.getCode() + " - " + errorWS.getMessage());
					}
				}
				resultado.setVerificado(Boolean.TRUE);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setVerificado(Boolean.FALSE);
				if(respuestaWS.getErrors() != null && respuestaWS.getErrors().length > 0){
					for(PpsError errorWS : respuestaWS.getErrors()){
						resultado.addMensajeLista(errorWS.getCode() + " - " + errorWS.getMessage());
					}
				} else {
					resultado.setMensajeError("El WS no ha devuelto errores pero no ha confirmado la validez del justificante.");
				}
			}
		}
		return resultado;
	}

	private ProfessionalProofWSBindingStub getStubVerificacionJustificante(BigDecimal numExpediente) throws MalformedURLException, ServiceException {
		ProfessionalProofWSBindingStub stubJustificanteProf = null;
		String timeOut = gestorPropiedades.valorPropertie(TIMEOUT_PROP_JUSTIFICANTES);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(WEBSERVICE_JUSTIFICANTES_PRO));
		ProfessionalProofWS professionalProofWS = null;
		if(miUrl != null){
			ProfessionalProofServiceLocator proofServiceLocator = new ProfessionalProofServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),proofServiceLocator.getProfessionalProofServiceWSDDServiceName());
			
			
			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = proofServiceLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapVerificacionJustProfSegaWSHandler.class);
			
			Map<String, Object> filesConfig = new HashMap<String, Object>();
			filesConfig.put(SoapVerificacionJustProfSegaWSHandler.PROPERTY_KEY_ID, numExpediente);
			logHandlerInfo.setHandlerConfig(filesConfig);

			list.add(logHandlerInfo);
			
			professionalProofWS = proofServiceLocator.getProfessionalProofService(miUrl);
			stubJustificanteProf = (ProfessionalProofWSBindingStub) professionalProofWS;
			stubJustificanteProf.setTimeout(Integer.parseInt(timeOut));
		}
		return stubJustificanteProf;
	}
	
	@Override
	public ResultadoJustificanteProfesionalBean actualizarVerificacionJustProf(BigDecimal idJustifianteInterno, EstadoJustificante estado, Boolean verificado, BigDecimal idUsuario, List<String> listaMensajes) {
		ResultadoJustificanteProfesionalBean resultado = new ResultadoJustificanteProfesionalBean(Boolean.FALSE);
		try {
			JustificanteProfDto justificantePro = servicioJustificanteProfesional.getJustificanteProfesional(idJustifianteInterno.longValue());
			if(justificantePro != null){
				if(verificado){
					justificantePro.setVerificado(ConstantesProcesos.SI);
					if (justificantePro.getFechaInicio() == null) {
						justificantePro.setFechaInicio(Calendar.getInstance().getTime());
					}
				} else if(!verificado) {
					justificantePro.setVerificado(ConstantesProcesos.NO);
				}
				BigDecimal estadoAnt = justificantePro.getEstado();
				justificantePro.setEstado(new BigDecimal(estado.getValorEnum()));
				ResultBean resultBean = servicioJustificanteProfesional.guardarJustificanteProfesional(justificantePro, justificantePro.getNumExpediente(), idUsuario, estadoAnt, new BigDecimal(estado.getValorEnum()), montarRespuesta(listaMensajes, MAXIMA_LONGITUD_COMENTARIOS_EVOLUCION));
				if(resultBean.getError()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError(resultBean.getMensaje());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se ha podido actualizar el justificante con los resultados del WS porque no se encuentra en la base de datos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el justificante, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de actualizar el justificante.");
		}
		return resultado;
	}
	
	private String montarRespuesta(List<String> listaMensajes, int maxlength) {
		StringBuilder sb = null;
		sb = new StringBuilder();
		if (listaMensajes != null) {
			for (String mensaje : listaMensajes) {
				if (sb.length() > 0) {
					sb.append("; ");
				}
				sb.append(mensaje);
			}
			// Si la longitud de los mensajes es mayor de lo que cabe en la tabla, se corta
			if (sb.length() > maxlength) {
				sb.setLength(maxlength);
			}
		}
		return sb != null ? sb.toString() : null;
	}

	@Override
	public void cambiarEstadoJustificanteProfSolicitudSega(BigDecimal numExpediente, EstadoJustificante estadoNuevo,
			BigDecimal idUsuario) {
		// TODO Auto-generated method stub
		
	}
}
