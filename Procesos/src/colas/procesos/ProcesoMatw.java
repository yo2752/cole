package colas.procesos;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;

import javax.net.ssl.SSLHandshakeException;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasVO;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoDgt;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinasTransactional;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.ConstantesPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.EstadoPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.TipoPegatinas;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.dgt.DGTMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCardMatwDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import matw.beans.RespuestaMatw;
import matw.beans.RespuestaMatwListadoErroresError;
import matw.beans.RespuestaMatwResultado;
import oegam.constantes.ConstantesPQ;
import procesos.enumerados.RetornoProceso;
import trafico.beans.DatosCardMatwBean;
import trafico.matriculacion.matw.DGTSolicitudMatriculacionMatw;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloDGTWS;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloVehiculo;
import trafico.utiles.XMLMatwFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.GuardarMatriculacionExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TrataMensajeExcepcion;

public class ProcesoMatw extends ProcesoBase {
	private static final String ERROR_EN_LA_VALIDACION_DEL_XML = "Error en la validacion del xml";
	private static final String CORRECTO = "CORRECTO";
	private static final String GTMS2011 = "GTMS2011";
	private static final String UTF_8 = "UTF-8";
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoMatw.class);
	private Integer hiloActivo;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private ServicioMensajesProcesos servicioMensajesProcesos;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioPegatinasTransactional servicioPegatinasTransactional;

	@Autowired
	ServicioDistintivoDgt servicioDistintivo;

	@Autowired
	private DGTMatriculacion dgtMatriculacion;

	@Autowired
	private ServicioProcesos servicioProcesos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	Utiles utiles;

	private ModeloDGTWS modeloDGTWS;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloMatriculacion modeloMatriculacion;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		Boolean esLaborable = true;
		Boolean forzarEjecucion = false;
		// Comprueba si el dia es laborable y no es festivo nacional
		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FECHA_LABORABLE))) {
			try {
				esLaborable = utilesFecha.esFechaLaborable("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.COMPROBAR_FESTIVO_NACIONAL)));
			} catch (OegamExcepcion e1) {
				log.error("Ha sucedido un error a la hora de comprobar las fechas laborables, error: ", e1);
			}
		}

		if ("SI".equals(gestorPropiedades.valorPropertie(ConstantesProcesos.FORZAR_EJECUCION_MATRICULACION))) {
			forzarEjecucion = true;
		}

		if (esLaborable || forzarEjecucion) {
			// Ya se hace en el padre ProcesoBase
			// Forzamos la inyección de dependencias de las clases anotadas como 'Autowired'
			//SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

			hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
			ColaBean solicitud = new ColaBean();
			DatosCardMatwBean datosCardMatwBean = new DatosCardMatwBean();
			DatosCardMatwDto datosCardMatwDto = new DatosCardMatwDto();

			try {
				log.info("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Buscando Solicitud");
				solicitud = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_MATW, hiloActivo.toString());

				String deshabilitarPQ = gestorPropiedades.valorPropertie("deshabilitar.pq.dgtws");

				if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
					datosCardMatwDto = servicioTramiteTraficoMatriculacion.datosCardMatw(solicitud.getIdTramite());
				} else {
					datosCardMatwBean = getModeloDGTWS().datosCardMatw(solicitud.getIdTramite());
				}

				/** Cambio Gestor de Documentos **/
				/* Dummy process check begins */
				ProcesoDummy dummyP = new ProcesoDummyImpl(solicitud, ProcesoMatw.class, jobExecutionContext);
				/* Dummy process check ends */
				Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(solicitud.getIdTramite());
				File ficheroAenviar = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.ENVIO, fechaBusqueda, solicitud.getIdTramite().toString())
						.getFiles().get(0);

				XMLMatwFactory xMLMatwFactory = new XMLMatwFactory();
				String peticionXML = xMLMatwFactory.xmlFileToString(ficheroAenviar);
				String valido = xMLMatwFactory.validarXMLMATW(ficheroAenviar);

				if (peticionXML != null && valido.equals(CORRECTO)) {
					log.info("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Solicitud " + ConstantesProcesos.PROCESO_MATW + " encontrada, llamando a WS");
					llamadaWS(jobExecutionContext, solicitud, datosCardMatwBean, datosCardMatwDto, peticionXML, deshabilitarPQ);
				} else {
					try {
						finalizarTransaccionConErrorNoRecuperable(solicitud, ERROR_EN_LA_VALIDACION_DEL_XML, jobExecutionContext);
					} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
						tratarRecuperable(jobExecutionContext, solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
						log.error(cambiarEstadoTramiteTraficoExcepcion);
					}
				}
			} catch (ConnectException eCon) {
				log.error("ConnectException");
				log.error(eCon);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.TIMEOUT);
			} catch (SSLHandshakeException e) {
				log.error("SSLHandshakeException");
				log.error(e);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_SSL_EN_LA_COMUNICACION_CON_EL_WEBSERVICE);
			} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion) {
				log.error("No Hay Solicitudes");
				sinPeticiones(jobExecutionContext);
				log.info(sinSolicitudesExcepcion.getMensajeError1());
			} catch (GuardarMatriculacionExcepcion gme) {
				log.error("FinalizarTransaccionConErrorNoRecuperable");
				log.error(gme);
				try {
					finalizarTransaccionConErrorNoRecuperable(solicitud, gme.getMensajeError1(), jobExecutionContext);
				} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
					tratarRecuperable(jobExecutionContext, solicitud, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
					log.error(cambiarEstadoTramiteTraficoExcepcion);
				} catch (BorrarSolicitudExcepcion e) {
					marcarSolicitudConErrorServicio(solicitud, e.getMensajeError1(), jobExecutionContext);
				}
			} catch (OegamExcepcion e) {
				log.logarOegamExcepcion("OegamExcepcion", e);
				marcarSolicitudConErrorServicio(solicitud, e.getMensajeError1(), jobExecutionContext);
			} catch (Exception e) {
				log.error("Excepcion General");
				log.error(e);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + e.getMessage());
			} catch (Throwable e) {
				log.error("Thowable");
				log.error("Throwable", e);
				tratarRecuperable(jobExecutionContext, solicitud, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + e.getMessage());
			}
		} else {
			peticionCorrecta(jobExecutionContext);
		}
		// Fin de XML válido solicitud sin error.
	}

	private void llamadaWS(JobExecutionContext jobExecutionContext, ColaBean cola, DatosCardMatwBean datosCardMatwBean, DatosCardMatwDto datosCardMatwDto, String peticionXML, String deshabilitarPQ)
			throws Exception, OegamExcepcion {
		RespuestaMatw respuestaMatw = new RespuestaMatw();
		String deshabilitarEjecucionDao = gestorPropiedades.valorPropertie("deshabilitar.ejecucion.dao");
		try {
			// ---------------PASAMOS A INVOCAR AL WEBSERVICE-----------
			log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Procesando petición de " + cola.getIdTramite());

			if (deshabilitarPQ != null && "SI".equals(deshabilitarPQ)) {
				respuestaMatw = dgtMatriculacion.presentarDGTMatw(peticionXML, datosCardMatwDto, cola.getIdTramite());
			} else {
				respuestaMatw = DGTSolicitudMatriculacionMatw.presentarDGTMatw(peticionXML, datosCardMatwBean, cola.getIdTramite());
			}
			log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Peticion Procesada de " + cola.getIdTramite());

			try {
				if (respuestaMatw != null && respuestaMatw.getResultado() != null && respuestaMatw.getResultado().getValue().equals(RespuestaMatwResultado.OK.getValue())) {
					log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Respuesta recibida");
					cola.setRespuesta(respuestaMatw.getResultado().getValue());
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA, cola.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_CORRECTA, cola.getRespuesta(),ConstantesProcesos.PROCESO_MATW);
					}
					log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Proceso ejecutado correctamente");
				} else if (respuestaMatw != null) {
					String respuesta = "";
					if (respuestaMatw.getListadoErrores() != null && respuestaMatw.getListadoErrores().length > 0) {
						for (RespuestaMatwListadoErroresError error : respuestaMatw.getListadoErrores()) {
							respuesta += error.getCodigo() + " - " + error.getDescripcion() + "; ";
						}
						cola.setRespuesta(respuesta);
					} else if (respuestaMatw.getResultado() != null) {
						cola.setRespuesta(respuestaMatw.getResultado().getValue());
					} else {
						cola.setRespuesta(RespuestaMatwResultado.ERROR.getValue());
					}
					log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Error Recibido: " + cola.getRespuesta());
					// Mantis 0013025
					if (cola.getRespuesta().contains("generico") || cola.getRespuesta().contains("timeout") || cola.getRespuesta().contains("MATW1")) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, cola.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION ,cola.getRespuesta(),ConstantesProcesos.PROCESO_MATW);
						}
						log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Se ha producido una excepción");
					} else {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, cola.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,cola.getRespuesta(),ConstantesProcesos.PROCESO_MATW);
						}
					log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- El Proceso Devolvió error ");
					}
					// Fin Mantis 0013025
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.MATW.getNombreEnum(), e);
			}
			// FIN : Incidencia : 0000814

			// MANEJAMOS LA RESPUESTA DEL WS
			if (respuestaMatw.getResultado().getValue().equals(RespuestaMatwResultado.OK.getValue())) {
				respuestaOk(cola, respuestaMatw, jobExecutionContext);
			}

			/*---------Error del webservice-------------*/
			if (!respuestaMatw.getResultado().getValue().equals(RespuestaMatwResultado.OK.getValue())) {
				respuestaError(cola, respuestaMatw, jobExecutionContext);
			}
			/*------Fin de error del webservice---------*/

			// 01-10-2012. Ricardo Rodriguez. Incidencia : 0000814
		} catch (Exception ex) {
			try {
				log.error("ERROR MATW -> " + ex.getMessage());
				String error = null;
				if (ex.getMessage() == null || ex.getMessage().equals("")) {
					error = ex.toString();
				} else {
					error = ex.toString() + " " + ex.getMessage();
				}

				if (error.length() > 499) {
					error = error.substring(0, 498);
				}

				if (cola.getRespuesta() == null) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error:"sin mensaje", ConstantesProcesos.PROCESO_MATW);
					}
				} else if (cola.getRespuesta().contains("generico") || cola.getRespuesta().contains("timeout") || cola.getRespuesta().contains("MATW1")) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, cola.getRespuesta() != null ? cola.getRespuesta():"sin mensaje");
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, cola.getRespuesta() != null ? cola.getRespuesta() : "sin mensaje", ConstantesProcesos.PROCESO_MATW);
					}
				} else if (!ConstantesProcesos.OK.equals(cola.getRespuesta())) {
					if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
						servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, cola.getRespuesta());
					} else {
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,cola.getRespuesta(),ConstantesProcesos.PROCESO_MATW);
					}
					log.error("Proceso Matw: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.MATW.getNombreEnum(), e);
			}
			throw ex;
		} catch (OegamExcepcion ex) {
			try {
				String error = null;
				if ((ex.getMessage() == null || ex.getMessage().equals("")) && (ex.getMensajeError1() == null || ex.getMensajeError1().equals(""))) {
					error = ex.toString();
				} else {
					error = ex.toString() + " " + ex.getMessage() + " " + ex.getMensajeError1();
				}
				// Modificación MATW jbc
				if (error.length() < 500) {
					if (cola.getRespuesta() == null) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje",ConstantesProcesos.PROCESO_MATW);	
						}
					} else if (cola.getRespuesta().contains("generico") || cola.getRespuesta().contains("timeout") || cola.getRespuesta().contains("MATW1")) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, cola.getRespuesta()!=null?cola.getRespuesta():"sin mensaje");
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION ,cola.getRespuesta()!=null?cola.getRespuesta():"sin mensaje",ConstantesProcesos.PROCESO_MATW);	
						}

					} else if (!ConstantesProcesos.OK.equals(cola.getRespuesta())) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, cola.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,cola.getRespuesta(),ConstantesProcesos.PROCESO_MATW);	
						}
						log.error("Proceso Matw: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex);
					}
				} else {
					error = error.substring(0, 498);
					if (cola.getRespuesta() == null) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, error != null ? error:"sin mensaje");
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION ,error != null ? error:"sin mensaje",ConstantesProcesos.PROCESO_MATW);	
						}

					} else if (cola.getRespuesta().contains("generico") || cola.getRespuesta().contains("timeout") || cola.getRespuesta().contains("MATW1")) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION, cola.getRespuesta()!=null?cola.getRespuesta():"sin mensaje");
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_EXCEPCION ,cola.getRespuesta()!=null?cola.getRespuesta():"sin mensaje",ConstantesProcesos.PROCESO_MATW);	
						}
					} else if (!ConstantesProcesos.OK.equals(cola.getRespuesta())) {
						if (deshabilitarEjecucionDao != null && "SI".equals(deshabilitarEjecucionDao)) {
							servicioProcesos.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA, cola.getRespuesta());
						} else {
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(cola, ConstantesProcesos.EJECUCION_NO_CORRECTA ,cola.getRespuesta(),ConstantesProcesos.PROCESO_MATW);	
						}
						log.error("Proceso Matw: Se ha devuelto un error de ejecucion no correcta como resultado de: " + ex, ex);
					}
				}
			} catch (Exception e) {
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + ProcesosEnum.MATW.getNombreEnum(), e);
			}
			throw ex;
		}
		// FIN : Incidencia : 0000814
	}

	public void marcarBastidorMatriculado(BigDecimal numExpediente) {
		ModeloVehiculo modelo = new ModeloVehiculo();
		modelo.updateBastidorMatriculado(Long.valueOf(numExpediente.toString()), 1);
	}

	private void respuestaError(ColaBean cola, RespuestaMatw respuestaMatw, JobExecutionContext jobExecutionContext) throws TrataMensajeExcepcion, CambiarEstadoTramiteTraficoExcepcion,
			BorrarSolicitudExcepcion {

		log.error("error del webservice en expediente:" + cola.getIdTramite());
		// Hay error, Evaluar error_tecnico o error_funcional
		RespuestaMatwListadoErroresError[] listadoErrores = respuestaMatw.getListadoErrores();
		listarErroresMatw(listadoErrores);
		String respuestaError = getMensajeError(listadoErrores);
		// Reencolar la petición dependiendo del tipo de error

		boolean recuperable = compruebaErroresRecuperables(listadoErrores);

		if (!recuperable) {
			// Inicio 0008994: Marca de Agua - Bastidor Duplicado
			boolean bastidorMatriculado = false;
			for (RespuestaMatwListadoErroresError error : listadoErrores) {
				if (error.getCodigo().equals(ConstantesProcesos.CODIGO_ERROR_BASTIDOR_YA_MATRICULADO)) {
					bastidorMatriculado = true;
				}
			}

			// Mantis 13412. David Sierra: Actualmente los bastidores pueden duplicarse
			// se comenta la comprobación del properties
			// boolean habBastDuplicado = false;
			// if(gestorPropiedades.valorPropertie("matw.bastidorDuplicado.habilitado")!=null
			// && gestorPropiedades.valorPropertie("matw.bastidorDuplicado.habilitado").equals("SI")){
			// habBastDuplicado = true;
			// }

			if (bastidorMatriculado) {
				marcarBastidorMatriculado(cola.getIdTramite());
			}
			// Fin 0008994.

			finalizarTransaccionConErrorNoRecuperable(cola, respuestaError, jobExecutionContext);
		} else {
			tratarRecuperable(jobExecutionContext, cola, respuestaError);
		}
	}

	public void respuestaOk(ColaBean cola, RespuestaMatw respuestaMatw, JobExecutionContext jobExecutionContext) throws Exception, OegamExcepcion {
		/** Cambios Gestor de Documentos **/
		guardarPermisoTemporalCirculacion(cola.getIdTramite(), respuestaMatw);
		guardarMatriculacion(cola.getIdTramite(), respuestaMatw);
		/**
		 * Se guarda la ficha tecnica si viene en la respuesta del web service Inci Mantis 6974
		 **/
		guardarFichaTecnica(cola.getIdTramite(), respuestaMatw);
		log.error("Proceso " + ConstantesProcesos.PROCESO_MATW + " -- Se va a descontar para: " + cola.getIdTramite());
		descontarCreditos(cola, getModeloTrafico().obtenerIdContratoTramite(cola.getIdTramite()), cola.getIdTramite());

		if (respuestaMatw.getDistintivo() != null && !respuestaMatw.getDistintivo().isEmpty()){
			String numColegiado = servicioTramiteTrafico.obtenerNumColegiadoByNumExpediente(cola.getIdTramite());
			guardarDistintivo(cola.getIdTramite(), respuestaMatw.getDistintivo(), respuestaMatw.getInfoMatricula().getMatricula());
			TramiteTrafDto tramiteTrafDto = servicioTramiteTrafico.getTramiteDto(cola.getIdTramite(), true);
			servicioTramiteTraficoMatriculacion.actualizarPegatina(cola.getIdTramite());
			actualizarPegatinaEvolucion(cola.getIdTramite(), respuestaMatw.getInfoMatricula().getMatricula(), respuestaMatw.getDistintivoTipo(), numColegiado, tramiteTrafDto.getJefaturaTraficoDto().getJefaturaProvincial());
		}
		try {
			ResultadoDistintivoDgtBean resultado = servicioDistintivo.solicitarDistintivo(cola.getIdTramite(), cola.getIdUsuario());
			if (resultado.isError()) {
				log.error("Ha sucedido un error a la hora de solicitar el distintivo mediambiental, error: ", resultado.getMensaje());
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de solicitar el distintivo mediambiental");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar el distintivo mediambiental, error: ", e);
		}
		ResultBean resultBeanBorrarSolicitud = (ResultBean) getModeloSolicitud().borrarSolicitudExcep(cola.getIdEnvio(), ConstantesProcesos.OK).get(ConstantesPQ.RESULTBEAN);
		if (resultBeanBorrarSolicitud.getError())
			log.error(resultBeanBorrarSolicitud.getMensaje());
		log.error("Se ha producido una excepcion al borrar la solicitud de la cola");
		if (!resultBeanBorrarSolicitud.getError()) {
			log.info(resultBeanBorrarSolicitud.getMensaje());
			peticionCorrecta(jobExecutionContext);
		}
	}

	private boolean descontarCreditos(ColaBean cola, BigDecimal idcontrato, BigDecimal idTramite) {
		HashMap<String, Object> map = getModeloCreditosTrafico().descontarCreditos(idcontrato.toString(), new BigDecimal(1), TipoTramiteTrafico.Matriculacion.getValorEnum(), cola.getIdUsuario());
		ResultBean res = (ResultBean) map.get(ConstantesPQ.RESULTBEAN);
		if (res.getError()) {
			log.error("ERROR DESCONTAR CREDITOS");
			log.error("CONTRATO: " + idcontrato.toString());
			log.error("ID_USUARIO: " + cola.getIdUsuario());
		} else {
			try {
				ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
				if (servicioCreditoFacturado != null) {
					servicioCreditoFacturado.anotarGasto(new Integer(1), ConceptoCreditoFacturado.TMT, idcontrato.longValue(), "T1", idTramite.toString());
				}
			} catch (Exception e) {
				log.error("Se ha producido un error al guardar historico de creditos para el tramite: " + idTramite, e);
			}
		}
		return !res.getError();
	}

	private void guardarMatriculacion(BigDecimal idTramite, RespuestaMatw respuestaMatw) throws GuardarMatriculacionExcepcion {
		getModeloMatriculacion().guardarMatriculacion(idTramite, respuestaMatw.getInfoMatricula().getMatricula());
	}

	/**
	 * @param idTramite
	 * @param respuestaMatw
	 * @throws Exception
	 * @throws IOException
	 */
	public void guardarFichaTecnica(BigDecimal idTramite, RespuestaMatw respuestaMatw) throws Exception, IOException {

		String fichaTecnica = respuestaMatw.getInfoMatricula().getFicha_tecnica_PDF();

		if (null != fichaTecnica && !fichaTecnica.isEmpty()) {
			try {
				/** Cambio Gestor de Documentos **/
				byte[] ptcBytes = utiles.doBase64Decode(fichaTecnica, UTF_8);
				FicheroBean fichero = new FicheroBean();

				fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
				fichero.setSubTipo(ConstantesGestorFicheros.FICHATECNICA);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
				String nombreFichero = idTramite + ConstantesProcesos.FICHA_TECNICA;
				if (idTramite != null) {
					fichero.setFecha(Utilidades.transformExpedienteFecha(idTramite.toString()));
					fichero.setNombreDocumento(nombreFichero);
				} else {
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);
				}

				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);

			} catch (OegamExcepcion e) {
				log.error(e);
			}
		} else {
			log.error("La respuesta del tramite " + idTramite + " no trae ficha tecnica");
		}
	}

	/**
	 * @param idTramite
	 * @param respuestaMatw
	 * @throws Exception
	 */
	private void guardarPermisoTemporalCirculacion(BigDecimal idTramite, RespuestaMatw respuestaMatw) throws Exception {
		String permisoTemporalCirculacion = respuestaMatw.getInfoMatricula().getPC_Temporal_PDF();
		// Guardamos el fichero de permiso temporal de circulación en un archivo
		if (permisoTemporalCirculacion != null && !permisoTemporalCirculacion.isEmpty()) {
			try {
				/** Cambio Gestor de Documentos **/
				byte[] ptcBytes = utiles.doBase64Decode(permisoTemporalCirculacion, UTF_8);

				FicheroBean fichero = new FicheroBean();

				fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
				fichero.setSubTipo(ConstantesGestorFicheros.PTC);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);

				if (idTramite != null) {
					fichero.setFecha(Utilidades.transformExpedienteFecha(idTramite));
					fichero.setNombreDocumento(idTramite.toString());
				} else {
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);
				}
				fichero.setFicheroByte(ptcBytes);
				gestorDocumentos.guardarByte(fichero);

			} catch (OegamExcepcion e) {
				log.error(e);
			}
		} else {
			log.error("La respuesta del tramite " + idTramite + " no trae permiso temporal de circulacion");
		}
	}

	private void guardarDistintivo(BigDecimal idTramite, String pdf, String matricula) throws Exception {
		if (pdf != null && !pdf.isEmpty()) {

			try {
				byte[] pdfPegatinaBytes = utiles.doBase64Decode(pdf, UTF_8);

				FicheroBean fichero = new FicheroBean();

				fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
				fichero.setSubTipo(ConstantesGestorFicheros.PDF_PEGATINA);
				fichero.setSobreescribir(true);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);

				if (idTramite != null) {
					fichero.setFecha(Utilidades.transformExpedienteFecha(idTramite));
					fichero.setNombreDocumento(matricula);
				} else {
					throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_NOMBRE_DOCUMENTO);
				}

				fichero.setFicheroByte(pdfPegatinaBytes);
				gestorDocumentos.guardarByte(fichero);

			} catch (OegamExcepcion e) {
				log.error(e);
			}
		} else {
			log.error("La respuesta del tramite " + idTramite + " no trae pegatina");
		}
	}

	private void actualizarPegatinaEvolucion(BigDecimal idTramite, String matricula, String tipo, String numColegiado, String jefaturaProvincial){
		PegatinasVO pegatina = new PegatinasVO();
		pegatina.setMatricula(matricula);
		pegatina.setNumExpediente(idTramite);
		pegatina.setTipo(TipoPegatinas.valueOf(ConstantesPegatinas.DISTINTIVO+tipo).getValorEnum());
		pegatina.setEstado(EstadoPegatinas.PENDIENTE_IMPRESION.getValorEnum());
		pegatina.setDescrEstado(EstadoPegatinas.PENDIENTE_IMPRESION.getNombreEnum());
		pegatina.setFechaAlta(new Date());
		pegatina.setNumColegiado(numColegiado);
		pegatina.setJefatura(jefaturaProvincial);
		int idPegatina = servicioPegatinasTransactional.insertarPegatina(pegatina);
		PegatinasEvolucionVO evo = new PegatinasEvolucionVO();
		evo.setEstado("Creada");
		evo.setFecha(new Date());
		evo.setNumColegiado(numColegiado);
		evo.setIdPegatina(idPegatina);
		servicioPegatinasTransactional.insertarPegatinasEvolucion(evo);
	}

	private void listarErroresMatw(RespuestaMatwListadoErroresError[] listadoErrores) {
		for (int i = 0; i < listadoErrores.length; i++) {
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.CODIGO + listadoErrores[i].getCodigo());
			log.error(ConstantesProcesos.ERROR_DE_WEBSERVICE + ConstantesProcesos.DESCRIPCION + listadoErrores[i].getDescripcion());
		}
	}

	private boolean compruebaErroresRecuperables(RespuestaMatwListadoErroresError[] listadoErrores) throws TrataMensajeExcepcion {
		boolean recuperable = true;

		for (RespuestaMatwListadoErroresError error : listadoErrores) {
			recuperable = servicioMensajesProcesos.tratarMensaje(error.getCodigo(), error.getDescripcion());
			if (!recuperable) {
				break;
			}
		}
		return recuperable;
	}

	private String getMensajeError(RespuestaMatwListadoErroresError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getCodigo());
			mensajeError.append(" - ");
			mensajeError.append(listadoErrores[i].getDescripcion());
			if (listadoErrores.length > 1) {
				mensajeError.append(" - ");
			}
		}
		return mensajeError.toString();
	}

	@SuppressWarnings("unused")
	private boolean errorTecnico(RespuestaMatwListadoErroresError[] listadoErrores) {
		for (int i = 0; i < listadoErrores.length; i++) {
			if (listadoErrores[i].getCodigo().equals(GTMS2011))
				return true;
		}
		return false;
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Proceso " + ConstantesProcesos.PROCESO_MATW);
	}

	/* **************************************************************** */
	/* MODELOS ******************************************************** */
	/* **************************************************************** */

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public ModeloDGTWS getModeloDGTWS() {
		if (modeloDGTWS == null) {
			modeloDGTWS = new ModeloDGTWS();
		}
		return modeloDGTWS;
	}

	public void setModeloDGTWS(ModeloDGTWS modeloDGTWS) {
		this.modeloDGTWS = modeloDGTWS;
	}

	public ModeloMatriculacion getModeloMatriculacion() {
		if (modeloMatriculacion == null) {
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

	@Override
	protected void marcarSolicitudConErrorServicio(ColaBean cola, String respuestaError, JobExecutionContext jobExecutionContext) {
		getModeloSolicitud().errorServicio(cola.getIdEnvio(), respuestaError);
		servicioTramiteTraficoMatriculacion.actualizarEstadoFinalizadoConError(cola.getIdTramite());
		getModeloSolicitud().notificarErrorServicio(cola,respuestaError);
		jobExecutionContext.getMergedJobDataMap().put(RETORNO, RetornoProceso.CORRECTO);
	}

}