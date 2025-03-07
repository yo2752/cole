package colas.procesos;
//TODO MPC. Cambio IVTM. Esta clase está aquí bien.
import java.io.IOException;
import java.math.BigDecimal;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.axis.AxisFault;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.ivtm.PeticionSincronaAlta;
import com.ivtm.PeticionSincronaConsulta;
import com.ivtm.PeticionSincronaModificacion;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import es.map.scsp.esquemas.datosespecificos.DatosEspecificosAltaProyectoIVTMRespuesta;
import es.map.scsp.esquemas.datosespecificos.DatosEspecificosModificacionProyectoIVTMRespuesta;
import trafico.dto.TramiteTraficoDto;
import trafico.dto.matriculacion.TramiteTrafMatrDto;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.modelo.ivtm.IVTMConsModeloMatriculacionInterface;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMConsModeloMatriculacionImpl;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.UtilesWSMatw;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.EstadoIVTM;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;

public class ProcesoIvtmMadrid extends ProcesoBase {
	
	private Integer hiloActivo;
	
	private ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoIvtmMadrid.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		ColaBean solicitud = new ColaBean();
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_IVTM + " -- Buscando Solicitud");
			solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_IVTM, hiloActivo.toString());
			log.info("Proceso " + ConstantesProcesos.PROCESO_IVTM + " -- Solicitud " + ConstantesProcesos.PROCESO_IVTM + " encontrada, llamando a WS");
			if(solicitud.getXmlEnviar().equalsIgnoreCase(ConstantesIVTM.TIPO_ALTA_IVTM_WS)){//ALTA
				llamadaWS(jobExecutionContext, solicitud);
			}else if(solicitud.getXmlEnviar().equalsIgnoreCase(ConstantesIVTM.TIPO_MODIFICACION_IVTM_WS)){//MOD
				llamadaWSMod(jobExecutionContext, solicitud);
			}else if(solicitud.getXmlEnviar().contains(ConstantesIVTM.TIPO_CONSULTA_IVTM_WS)){//CDEUDA
				llamadaWSConsulta(jobExecutionContext, solicitud);
			}
		} catch (SinSolicitudesExcepcion e) {
			sinPeticiones(jobExecutionContext); 
			UtilesExcepciones.logarOegamExcepcion(e, e.getMensajeError1(), log);
		}
	}
	
	
	public void llamadaWS(JobExecutionContext jobExecutionContext, ColaBean cola){
		try{
			new UtilesWSMatw().cargarAlmacenesTrafico();
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Proceso de Alta de Autoliquidacón IVTM");
			ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();
			IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
			TramiteTraficoDto tramiteTrafico = modeloTramite.recuperarDtoPorNumExp(cola.getIdTramite());
			if (tramiteTrafico.getTramiteTrafMatr()== null || tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto()==null){
				log.info(ConstantesIVTM.TEXTO_LOG_PROCESO+" -- Error recuperando los datos");
				return;
			}
			TramiteTrafMatrDto tramiteTrafMatDto = tramiteTrafico.getTramiteTrafMatr();
			IvtmMatriculacionDto ivtmMatriculacion = tramiteTrafMatDto.getIvtmMatriculacionDto();
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Procesando petición");
			es.map.scsp.esquemas.v2.peticion.altaivtm.Peticion peticionAlta = modeloIVTM.damePeticionAlta(tramiteTrafico);
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Peticion Procesada");
			PeticionSincronaAlta peticion = new PeticionSincronaAlta(obtenerURL());
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Llamando a WS " + ConstantesProcesos.PROCESO_IVTM + " peticionAlta");
			es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta response = (es.map.scsp.esquemas.v2.respuesta.altaivtm.Respuesta) peticion.doPeticion(peticionAlta, peticionAlta.getAtributos().getIdPeticion());
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Fin de llamada a WS");
			if(response!=null && response.getTransmisiones()!=null && response.getTransmisiones().getTransmisionDatos() != null && response.getTransmisiones().getTransmisionDatos().size()>0 && response.getTransmisiones().getTransmisionDatos().get(0)!=null && response.getTransmisiones().getTransmisionDatos().get(0).getDatosEspecificos()!=null){
				log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Respuesta recibida");
				DatosEspecificosAltaProyectoIVTMRespuesta datosEspecificosRespuesta = (DatosEspecificosAltaProyectoIVTMRespuesta) response.getTransmisiones().getTransmisionDatos().get(0).getDatosEspecificos();
				if (datosEspecificosRespuesta.getErrores() != null && datosEspecificosRespuesta.getErrores().getMenserr() != null	&& !datosEspecificosRespuesta.getErrores().getMenserr().isEmpty()) {
					ivtmMatriculacion.setMensaje(datosEspecificosRespuesta.getErrores().getMenserr());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO+" -- Error Recibido: "+ ivtmMatriculacion.getMensaje());
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error.getValorEnum()));
					cola.setRespuesta(ivtmMatriculacion.getMensaje());
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,"Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje(),ConstantesProcesos.PROCESO_IVTM);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje());
					finalizarTransaccionIvtmKO(jobExecutionContext, cola, ivtmMatriculacion.getMensaje());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Fin de procesamiento de errores");
					
					
					
					
				} else if("".equals(datosEspecificosRespuesta.getImporte()) || "".equals(datosEspecificosRespuesta.getImporteanual()))
					
				{
					ivtmMatriculacion.setMensaje(datosEspecificosRespuesta.getErrores().getMenserr());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO+"-- Error Recibido: "+ivtmMatriculacion.getMensaje());
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum()));
					cola.setRespuesta(ivtmMatriculacion.getMensaje());
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,"Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje(),ConstantesProcesos.PROCESO_IVTM);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje());
					finalizarTransaccionIvtmKO(jobExecutionContext, cola, ivtmMatriculacion.getMensaje());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Fin de procesamiento de errores");
				
					
				}else{				
					
					//RESPUESTA OK, RECUPERAMOS LOS DATOS Y LOS GUARDAMOS
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Recibida respuesta correcta. Procesando respuesta");
					ivtmMatriculacion.setNrc(datosEspecificosRespuesta.getNumautoliquidacion());
					ivtmMatriculacion.setCodGestor(datosEspecificosRespuesta.getCodgestor());
					ivtmMatriculacion.setDigitoControl(datosEspecificosRespuesta.getDigitos());
					ivtmMatriculacion.setEmisor(datosEspecificosRespuesta.getEmisor());
					if(datosEspecificosRespuesta.getImporte()!= null && !datosEspecificosRespuesta.getImporte().equals("")){						
						ivtmMatriculacion.setImporte(new BigDecimal(datosEspecificosRespuesta.getImporte()));
					}
					if(datosEspecificosRespuesta.getImporteanual()!=null && !datosEspecificosRespuesta.getImporteanual().equals("")){ 
						ivtmMatriculacion.setImporteAnual(new BigDecimal(datosEspecificosRespuesta.getImporte()));
					}
					ivtmMatriculacion.setMensaje("OK");
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Ok.getValorEnum()));
					cola.setRespuesta("OK");
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA ,"Proceso correcto. Nº Autoliquidacion: "+datosEspecificosRespuesta.getNumautoliquidacion(),ConstantesProcesos.PROCESO_IVTM);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA, "Proceso correcto. Nº Autoliquidacion: "+datosEspecificosRespuesta.getNumautoliquidacion());
					finalizarTransaccionIvtmOk(jobExecutionContext, cola);
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Proceso ejecutado correctamente");
				}
				cambiarEstadoAIniciado(tramiteTrafico);
			}else{
				log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+" No hay respuesta del servicio");
				ivtmMatriculacion.setMensaje("No hay respuesta del servicio.");
				ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error.getValorEnum()));
				cola.setRespuesta("No hay respuesta del servicio");
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,"No se ha recibido respuesta",ConstantesProcesos.PROCESO_IVTM);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, "No se ha recibido respuesta");
				finalizarTransaccionConAccionNoRecuperable(cola, "Error en el proceso de alta ivtm", jobExecutionContext);
				getModeloSolicitud().borrarSolicitudExcep(cola.getIdEnvio(), "IVTM SIN RESPUESTA");
				cambiarEstadoAIniciado(tramiteTrafico);
			}
			modeloIVTM.actualizar(ivtmMatriculacion);
		} catch (AxisFault e) {
			log.error("PROCESO ALTA IVTM - EXCEPCION AxisFault e: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);			
		} catch (OegamExcepcion e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION OegamExcepcion: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA",jobExecutionContext);			
		} catch (TransformerConfigurationException e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION:TransformerConfigurationException " + e);
			ejecutarExcepcion(cola, true, e, "ALTA",jobExecutionContext);			
		} catch (JAXBException e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION:JAXBException " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);			
		} catch (ParserConfigurationException e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION ParserConfigurationException: " + e);
			ejecutarExcepcion(cola, true, e,"ALTA", jobExecutionContext);
		} catch (SAXException e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION SAXException: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);
		} catch (IOException e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION IOException: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);			
		} catch (TransformerException e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION TransformerException: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);
		} catch (TransformerFactoryConfigurationError e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION TransformerFactoryConfigurationError: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);
		} catch (ServiceException e) {
			log.error(" PROCESO  ALTA IVTM - EXCEPCION ServiceException: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);
		} catch (Exception e) {
			log.error(" PROCESO ALTA  IVTM - EXCEPCION Exception: " + e);
			ejecutarExcepcion(cola, true, e, "ALTA", jobExecutionContext);
		} finally{
			;
		}
	}
	
	
	private BigDecimal convertirABigDecimalConComasAPuntos (String cadena){
		return new BigDecimal(cadena.replace(".", "").replace(",", "."));
	}
		
	private BigDecimal convertirABigDecimal (String cadena){
		return new BigDecimal(cadena);
	}
	
	private void ejecutarExcepcion(ColaBean solicitud, boolean alta, Throwable error, String tipoLlamada, JobExecutionContext jobExecutionContext) {
		try {
			IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
			if("CONSULTA".equals(tipoLlamada)){

				
				IVTMConsModeloMatriculacionInterface modeloIvtmConsulta = new IVTMConsModeloMatriculacionImpl();
				IvtmConsultaMatriculacionDto  ivtmConsultaDto = new IvtmConsultaMatriculacionDto();
				ivtmConsultaDto = modeloIvtmConsulta.buscarIvtmConsultaIdPeticion(solicitud.getIdTramite());
				ivtmConsultaDto.setMensaje(error.toString());
				modeloIvtmConsulta.actualizar(ivtmConsultaDto);
				
				
				solicitud.setRespuesta("Excepción"+ error.toString());
				errorConsulta(jobExecutionContext,solicitud);
			}else{
				log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+" -- Error en el proceso, se tratará la excepción");
				log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+" -- "+ error.getMessage() );
				ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();
				
				TramiteTraficoDto tramiteTrafico = modeloTramite.recuperarDtoPorNumExp(solicitud.getIdTramite());
				if (tramiteTrafico.getTramiteTrafMatr()== null || tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto()==null){
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO+" -- Error recuperando los datos");
					return;
				}
				TramiteTrafMatrDto tramiteTrafMatDto = tramiteTrafico.getTramiteTrafMatr();
				IvtmMatriculacionDto ivtmMatriculacion = tramiteTrafMatDto.getIvtmMatriculacionDto();
				if (error !=null && error.getMessage()!=null) {
					ivtmMatriculacion.setMensaje(error.getMessage());
				} else {
					ivtmMatriculacion.setMensaje("No hay respuesta del servicio.");
				}
				if (alta) {
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error.getValorEnum()));
				} else {
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum()));
				}
//				ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, "No se ha recibido respuesta");
//				try {
//					getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), "IVTM SIN RESPUESTA");
//				} catch (BorrarSolicitudExcepcion e1) {
//					log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+"Error al borrar la solicitud");
//				}
				
				//Notificamos el error por correo
				getModeloSolicitud().notificarErrorServicio(solicitud,error.toString());
				if (alta) {
					TramiteTraficoDto tramiteDto = modeloTramite.recuperarDtoPorNumExp(new BigDecimal(tramiteTrafMatDto.getNumExpediente()));
					cambiarEstadoAIniciado(tramiteDto);
				}
				modeloIVTM.actualizar(ivtmMatriculacion);
			}
			
			solicitud.setRespuesta("Excepción"+ error.toString());
			finalizarTransaccionIvtmKO(jobExecutionContext, solicitud, "SIN RESPUESTA");
			//errorConsulta(jobExecutionContext,solicitud);
			
	
		} catch (OegamExcepcion e) {
			log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+"Error al guardar los datos de matriculación");
			}
	}
		
	public void llamadaWSMod(JobExecutionContext jobExecutionContext, ColaBean cola){
		try{
			new UtilesWSMatw().cargarAlmacenesTrafico();
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Proceso de Modificación de Autoliquidacón IVTM");
			ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();
			IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
			TramiteTraficoDto tramiteTrafico = modeloTramite.recuperarDtoPorNumExp(cola.getIdTramite());
			if (tramiteTrafico.getTramiteTrafMatr()== null || tramiteTrafico.getTramiteTrafMatr().getIvtmMatriculacionDto()==null){
				log.info(ConstantesIVTM.TEXTO_LOG_PROCESO+" -- Error recuperando los datos");
				return;
			}
			TramiteTrafMatrDto tramiteTrafMatDto = tramiteTrafico.getTramiteTrafMatr();
			IvtmMatriculacionDto ivtmMatriculacion = tramiteTrafMatDto.getIvtmMatriculacionDto();
						
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Construyendo petición de modificación");
			es.map.scsp.esquemas.v2.peticion.modificacionivtm.Peticion peticionModificacion = modeloIVTM.damePeticionModificacion(tramiteTrafico);
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Petición construida correctamente");
			PeticionSincronaModificacion peticion = new PeticionSincronaModificacion(obtenerURL());
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Procesando petición de modificacion con ID: ["+cola.getCola()+"]"); 
			es.map.scsp.esquemas.v2.respuesta.modificacionivtm.Respuesta response = (es.map.scsp.esquemas.v2.respuesta.modificacionivtm.Respuesta) peticion.doPeticion(peticionModificacion, peticionModificacion.getAtributos().getIdPeticion());
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Peticion procesada");
			if(response!=null && response.getTransmisiones()!=null && response.getTransmisiones().getTransmisionDatos()!=null && response.getTransmisiones().getTransmisionDatos().size()>0 && response.getTransmisiones().getTransmisionDatos().get(0)!=null && response.getTransmisiones().getTransmisionDatos().get(0).getDatosEspecificos()!=null){
				log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Procesando la respuesta recibida");
				DatosEspecificosModificacionProyectoIVTMRespuesta datosEspecificosRespuesta = (DatosEspecificosModificacionProyectoIVTMRespuesta) response.getTransmisiones().getTransmisionDatos().get(0).getDatosEspecificos();
				if (datosEspecificosRespuesta.getErrores() != null && datosEspecificosRespuesta.getErrores().getMenserr() != null	&& !datosEspecificosRespuesta.getErrores().getMenserr().isEmpty()) {
					ivtmMatriculacion.setMensaje(datosEspecificosRespuesta.getErrores().getMenserr());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO+"-- Error Recibido: "+ivtmMatriculacion.getMensaje());
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum()));
					cola.setRespuesta(ivtmMatriculacion.getMensaje());
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,"Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje(),ConstantesProcesos.PROCESO_IVTM);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje());
					finalizarTransaccionIvtmKO(jobExecutionContext, cola, ivtmMatriculacion.getMensaje());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Fin de procesamiento de errores");
				} else if("".equals(datosEspecificosRespuesta.getImporte()) || "".equals(datosEspecificosRespuesta.getImporteanual()))
				
					{
					ivtmMatriculacion.setMensaje(datosEspecificosRespuesta.getErrores().getMenserr());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO+"-- Error Recibido: "+ivtmMatriculacion.getMensaje());
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum()));
					cola.setRespuesta(ivtmMatriculacion.getMensaje());
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,"Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje(),ConstantesProcesos.PROCESO_IVTM);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Proceso Incorrecto. Error: "+ivtmMatriculacion.getMensaje());
					finalizarTransaccionIvtmKO(jobExecutionContext, cola, ivtmMatriculacion.getMensaje());
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Fin de procesamiento de errores");
					
					}
				else{
					//RESPUESTA OK, RECUPERAMOS LOS DATOS Y LOS GUARDAMOS
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Recibida respuesta correcta. Procesando respuesta");
					ivtmMatriculacion.setNrc(datosEspecificosRespuesta.getNumautoliquidacion());
					ivtmMatriculacion.setCodGestor(datosEspecificosRespuesta.getCodgestor());
					ivtmMatriculacion.setDigitoControl(datosEspecificosRespuesta.getDigitos());
					ivtmMatriculacion.setEmisor(datosEspecificosRespuesta.getEmisor());
					if(datosEspecificosRespuesta.getImporte()!=null && !datosEspecificosRespuesta.getImporte().equals("")){
						ivtmMatriculacion.setImporte(new BigDecimal(datosEspecificosRespuesta.getImporte()));					
					}
					if(datosEspecificosRespuesta.getImporteanual()!=null && !datosEspecificosRespuesta.getImporteanual().equals("")){
						ivtmMatriculacion.setImporteAnual(new BigDecimal(datosEspecificosRespuesta.getImporte()));				
					}					
					
					ivtmMatriculacion.setMensaje("OK");
					ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Ok_Modificacion.getValorEnum()));
					cola.setRespuesta("OK");
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA ,"Modificación correcto. Nº Autoliquidacion: "+datosEspecificosRespuesta.getNumautoliquidacion(),ConstantesProcesos.PROCESO_IVTM);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA, "Modificación correcto. Nº Autoliquidacion: "+datosEspecificosRespuesta.getNumautoliquidacion());
					finalizarTransaccionIvtmOk(jobExecutionContext, cola);
					log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Respuesta procesada correctamente");
				}
			}else{
				log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Se ha producido un error en el servicio. Sin respuesta.");
				ivtmMatriculacion.setMensaje("No hay respuesta del servicio.");
				ivtmMatriculacion.setEstadoIvtm(new BigDecimal(EstadoIVTM.Ivtm_Error_Modificacion.getValorEnum()));
				cola.setRespuesta("OK");
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA , "No se ha recibido respuesta",ConstantesProcesos.PROCESO_IVTM);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, "No se ha recibido respuesta");
				finalizarTransaccionIvtmKO(jobExecutionContext, cola, "SIN RESPUESTA DEL SERVICIO");
				getModeloSolicitud().borrarSolicitud(cola.getIdEnvio(), "IVTM SIN RESPUESTA");
			}
			modeloIVTM.actualizar(ivtmMatriculacion);
		} catch (AxisFault e) {
			log.error(" PROCESO MODIFICACION IVTM - EXCEPCION AxisFault e: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);			
		} catch (OegamExcepcion e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION OegamExcepcion: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);			
		} catch (TransformerConfigurationException e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION:TransformerConfigurationException " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);			
		} catch (JAXBException e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION:JAXBException " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);			
		} catch (ParserConfigurationException e) {
			log.error(" PROCESO MODIFICACION IVTM - EXCEPCION ParserConfigurationException: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);
		} catch (SAXException e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION SAXException: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);
		} catch (IOException e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION IOException: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);			
		} catch (TransformerException e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION TransformerException: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);
		} catch (TransformerFactoryConfigurationError e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION TransformerFactoryConfigurationError: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);
		} catch (ServiceException e) {
			log.error(" PROCESO  MODIFICACION  IVTM - EXCEPCION ServiceException: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);
		} catch (Exception e) {
			log.error(" PROCESO MODIFICACION  IVTM - EXCEPCION Exception: " + e);
			ejecutarExcepcion(cola, true, e, "MODIFICACION", jobExecutionContext);
		} finally{
			;
		}
	}
	
	public void llamadaWSConsulta(JobExecutionContext jobExecutionContext, ColaBean cola) {
		try{
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Proceso de Consulta de Deuda IVTM Ayto. Madrid");
			IVTMConsModeloMatriculacionInterface modeloIVTM = new IVTMConsModeloMatriculacionImpl();
			IvtmConsultaMatriculacionDto consultaIVTM = modeloIVTM.buscarIvtmConsultaIdPeticion(cola.getIdTramite());
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Creando petición con id: ["+consultaIVTM.getIdPeticion()+"]");
			es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion peticion = modeloIVTM.damePeticionConsulta(consultaIVTM);
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Peticion creada");
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Creando Stub");
			

			PeticionSincronaConsulta stub = new PeticionSincronaConsulta(obtenerURL());
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Stub creado");
			new UtilesWSMatw().cargarAlmacenesTrafico();
			log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Llamado a WS...");
			es.map.scsp.esquemas.v2.respuesta.consultaivtm.Respuesta resp = (es.map.scsp.esquemas.v2.respuesta.consultaivtm.Respuesta) stub.doPeticion(peticion, peticion.getAtributos().getIdPeticion());
			if(resp!=null){
				log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Recibida respuesa");
				if (resp.getTransmisiones()!=null && resp.getTransmisiones().getTransmisionDatos()!=null && resp.getTransmisiones().getTransmisionDatos().size()>0){
					es.map.scsp.esquemas.v2.respuesta.consultaivtm.TransmisionDatos tx = resp.getTransmisiones().getTransmisionDatos().get(0);
					if (tx==null || tx.getDatosEspecificos()==null){
						errorConsulta(jobExecutionContext,cola);
					} else {
						if(tx.getDatosEspecificos().getMensaje()!=null && !tx.getDatosEspecificos().getMensaje().equals("")){
							consultaIVTM.setMensaje(tx.getDatosEspecificos().getMensaje());
							cola.setRespuesta(tx.getDatosEspecificos().getMensaje());
							log.info(ConstantesIVTM.TEXTO_LOG_PROCESO +" -- Respuesta recibida: "+tx.getDatosEspecificos().getMensaje());
						}else{
							consultaIVTM.setMensaje(ConstantesIVTM.RESULTADO_CONSULTAWS_VACIO);
							cola.setRespuesta(ConstantesIVTM.RESULTADO_CONSULTAWS_VACIO);
							log.info(ConstantesIVTM.TEXTO_LOG_PROCESO +" -- Respuesta recibida: "+ ConstantesIVTM.RESULTADO_CONSULTAWS_VACIO +tx.getDatosEspecificos().getMensaje());
							
						}
						if(tx.getDatosEspecificos().getErrores()!=null){
							if(tx.getDatosEspecificos().getErrores().getMenserr()!=null){
								consultaIVTM.setMensaje(tx.getDatosEspecificos().getErrores().getMenserr());
								cola.setRespuesta(tx.getDatosEspecificos().getMensaje());
								log.info("Proceso " + ConstantesProcesos.PROCESO_IVTM +" -- Error recibido: "+tx.getDatosEspecificos().getErrores().getMenserr());
							}
						}
						log.info("Proceso " + ConstantesProcesos.PROCESO_IVTM + " -- Actualizando Ejecución del Proceso");
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA ,"Consulta correcta. Respuesta: "+consultaIVTM.getMensaje(),ConstantesProcesos.PROCESO_IVTM);
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA, "Consulta correcta. Respuesta: "+consultaIVTM.getMensaje());
						finalizarTransaccionIvtmOk(jobExecutionContext, cola);
						log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Proceso Ejecutado Correctamente");
					}
				} else {
					errorConsulta(jobExecutionContext, cola);
				}
			}else{
				errorConsulta(jobExecutionContext, cola);
			}
			modeloIVTM.actualizar(consultaIVTM);
		} catch (AxisFault e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION AxisFault e: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext, cola,e);		
		} catch (OegamExcepcion e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION OegamExcepcion: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext, cola,e);		
		} catch (TransformerConfigurationException e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION:TransformerConfigurationException " + e);
			ejecutarExcepcionConsulta(jobExecutionContext, cola,e);			
		} catch (JAXBException e) {
			log.error(" PROCESO  CONSULTA IVTM - EXCEPCION:JAXBException " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);			
		} catch (ParserConfigurationException e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION ParserConfigurationException: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);
		} catch (SAXException e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION SAXException: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);
		} catch (IOException e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION IOException: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);
		} catch (TransformerException e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION TransformerException: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);
		} catch (TransformerFactoryConfigurationError e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION TransformerFactoryConfigurationError: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);
		} catch (ServiceException e) {
			log.error(" PROCESO CONSULTA IVTM - EXCEPCION ServiceException: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);
		} catch (Exception e) {
			log.error("PROCESO CONSULTA IVTM - EXCEPCION Exception: " + e);
			ejecutarExcepcionConsulta(jobExecutionContext,cola,e);
		} 
	}
	
	private void errorConsulta(JobExecutionContext jobExecutionContext, ColaBean cola) throws OegamExcepcion{
		log.info(ConstantesIVTM.TEXTO_LOG_PROCESO + " -- Error: No se ha recibido respuesta");
		ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,"Consulta Errónea. Sin respuesta del servicio",ConstantesProcesos.PROCESO_IVTM);
		//ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, "Consulta Errónea. Sin respuesta del servicio");
		finalizarTransaccionIvtmKO(jobExecutionContext, cola, "SIN RESPUESTA");
	}
	
	private void ejecutarExcepcionConsulta(JobExecutionContext jobExecutionContext, ColaBean solicitud, Throwable error){
		log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+" -- Error en el proceso, se tratará la excepción");
		IVTMConsModeloMatriculacionInterface modeloIVTM = new IVTMConsModeloMatriculacionImpl();
		IvtmConsultaMatriculacionDto consultaIVTM = modeloIVTM.buscarIvtmConsultaIdPeticion(solicitud.getIdTramite());
		if (error !=null && error.getMessage()!=null) {
			consultaIVTM.setMensaje(error.getMessage());
		} else {
			consultaIVTM.setMensaje("No hay respuesta del servicio.");
		}
		ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION ,error.toString(),ConstantesProcesos.PROCESO_IVTM);
		//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, error.toString());
		try {
			finalizarTransaccionConAccionNoRecuperable(solicitud, "Error en el proceso de consulta ivtm", jobExecutionContext);
			getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), "IVTM SIN RESPUESTA");
		} catch (BorrarSolicitudExcepcion | CambiarEstadoTramiteTraficoExcepcion e1) {
			log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+" Error al borrar la solicitud: "+ solicitud.getIdEnvio());
		}
		getModeloSolicitud().notificarErrorServicio(solicitud,error.toString());
		try {
			modeloIVTM.actualizar(consultaIVTM);
		} catch (OegamExcepcion e) {
			log.error(ConstantesIVTM.TEXTO_LOG_PROCESO+"Error al guardar los datos de matriculación");
		}

	}

	
	public boolean cambiarEstadoAIniciado(TramiteTraficoDto tramiteTraficoDto){
		return modeloTramite.cambiarEstado(new BigDecimal(tramiteTraficoDto.getNumExpediente()), EstadoTramiteTrafico.Iniciado);
	}
	
	private String obtenerURL(){
		return gestorPropiedades.valorPropertie(ConstantesIVTM.KEY_URL_PETICION_IVTM);
	}

	private void finalizarTransaccionIvtmOk(JobExecutionContext jobExecutionContext, ColaBean solicitud) throws OegamExcepcion{
		log.info("Finalización de transacción IVTM OK");
		peticionCorrecta(jobExecutionContext);
		ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,"CONSULTA IVTM OK",ConstantesProcesos.PROCESO_IVTM);
		//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, "CONSULTA IVTM OK");
		getModeloSolicitud().borrarSolicitudExcep(solicitud.getIdEnvio(), "SOLICITUD CONSULTA IVTM OK");
	}
	
	private void finalizarTransaccionIvtmKO(JobExecutionContext jobExecutionContext, ColaBean solicitud, String mensaje) throws OegamExcepcion{
		log.info("Finalización de transacción IVTM KO");
		ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA ,"ERROR EN LA RESPUESTA",ConstantesProcesos.PROCESO_IVTM);
		//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_NO_CORRECTA, "ERROR EN LA RESPUESTA");
		finalizarTransaccionConAccionNoRecuperable(solicitud, mensaje, jobExecutionContext);
		marcarSolicitudConErrorServicio(solicitud, mensaje, jobExecutionContext);
	}


	@Override
	public void cambioNumeroInstancias(int numero) {
		log.error("Se intenta cambiar el número de instancias del proceso del IVTM, pero no sabemos para que sirve");
		
	}
	
}
