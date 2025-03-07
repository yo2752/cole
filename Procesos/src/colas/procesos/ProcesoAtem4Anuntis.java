package colas.procesos;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import escrituras.beans.ResultBean;
import hibernate.dao.trafico.TramiteAnuntisDAO;
import hibernate.entities.trafico.TramiteAnuntis;
import net.sf.jasperreports.engine.JRException;
import oegam.constantes.ConstantesPQ;
import trafico.beans.RespuestaAtemBean;
import trafico.modelo.ModeloAcciones;
import trafico.modelo.ModeloAtem;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.EstadoTramiteAnuntis;
import trafico.utiles.enumerados.TipoAccion;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;
import utilidades.web.excepciones.ExpedienteSinSolicitudesExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TramiteNoRecuperadoExcepcion;

public class ProcesoAtem4Anuntis extends ProcesoBase {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoAtem4Anuntis.class);

	private static final String PROCESO = "Proceso ";
	private static final String NOMBRE_NODO = "vehiculo";

	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloAcciones modeloAcciones;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// Hilo/cola en ejecución:
		Integer hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		ColaBean colaBean = new ColaBean();
		try {
			ModeloAtem modelo = new ModeloAtem();
			log.info(PROCESO + ConstantesProcesos.PROCESO_ATEM_ANUNTIS + " -- Buscando Solicitud");

			// Recupera la solicitud de la tabla cola:
			colaBean = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_ATEM_ANUNTIS, hiloActivo.toString());
			log.info(PROCESO + ConstantesProcesos.PROCESO_ATEM_ANUNTIS + " -- Solicitud " + ConstantesProcesos.PROCESO_ATEM_ANUNTIS + " encontrada");

			ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(colaBean.getIdUsuario().toString());
			log.info(PROCESO + ConstantesProcesos.PROCESO_ATEM_ANUNTIS + " -- Solicitud " + ConstantesProcesos.PROCESO_ATEM_ANUNTIS + " encontrada.");

			if (contrato == null || contrato.getId().getIdContrato() == null) {
				String mensajeError = "No se ha recuperado el identificador del contrato de la solicitud con identificador: " + colaBean.getIdEnvio();
				log.error(mensajeError);
				throw new Exception(mensajeError);
			}

			// Recupera los detalles del trámite:
			// DAO de Tramites de Anuntis
			TramiteAnuntisDAO tramiteAnuntisDAO = new TramiteAnuntisDAO();
			TramiteAnuntis tramite = tramiteAnuntisDAO.get(colaBean.getIdTramite().longValue(), "tasa");

			if (tramite == null) {
				String mensajeError = "No se ha recuperado el tramite de la solicitud";
				log.error(mensajeError);

				// DRC@08-03-2013 Incidencia Mantis: 3522
				getModeloAcciones().cerrarAccion(TipoAccion.ATEM.getValorEnum(),
						colaBean.getIdUsuario(),
						colaBean.getIdTramite(),
						mensajeError);
				throw new TramiteNoRecuperadoExcepcion(mensajeError);
			}
			// *****************************************************************************************************************************************************
			// Estado
			if (tramite.getEstado() != null
					&& (tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Consultado_AVPO.getValorEnum())) == 0
							|| tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Procesado.getValorEnum())) == 0)) {
				log.error("El estado del trámite no permite la solicitud de informes. Identificador de la solicitud: " + colaBean.getIdEnvio());
			}

			int informesRecibidos = 0;
			// Verifica que la solicitud del vehículo está pendiente:
			if (tramite.getEstado() != null && tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Pendiente_Envio.getValorEnum())) == 0) {
				if (tramite.getMatricula() != null && !tramite.getMatricula().trim().isEmpty()
						&& tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null) {

					// Hacemos la llamada al WS de Atem
					RespuestaAtemBean resultbeanMatUni = modelo.invocaPeticionMatriculaUnitariaAnuntis(tramite.getMatricula(), tramite.getTasa().getCodigoTasa());
					// Si no tiene error
					if (!resultbeanMatUni.getError()) {
						tramite.setRespuesta(null);
						tramite.setEstado(new BigDecimal(EstadoTramiteAnuntis.Consultado_AVPO.getValorEnum()));

						// Descuento de crédito:
						HashMap<String, Object> resultado = getModeloCreditosTrafico()
								.descontarCreditos(contrato.getId().getIdContrato()
										.toString(), utiles
										.convertirIntegerABigDecimal(1), "T4",
										colaBean.getIdUsuario());

						ResultBean resultadoProcedimiento = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);

						if (resultadoProcedimiento.getError()) {
							String mensajeError = "Error al descontar créditos de la operación";
							resultbeanMatUni.setError(true);
							log.error(mensajeError);
							throw new DescontarCreditosExcepcion(resultadoProcedimiento.getMensaje());
						}

						byte[] fichero = generarInformeATEM(tramite.getTasa().getCodigoTasa(), resultbeanMatUni.getRespuestaCompleta(), NOMBRE_NODO, Constantes.VALOR_TRAMITE_TRAFICO_PDF);

						HashMap<String, byte[]> mapaBytesApdf = new HashMap<>();
						mapaBytesApdf.put(tramite.getNumExpediente().toString() + "_" + tramite.getMatricula() + "." + Constantes.VALOR_TRAMITE_TRAFICO_PDF, fichero);
						modelo.guardarInformesAnuntis(new BigDecimal(tramite.getNumExpediente()), mapaBytesApdf);

						informesRecibidos++;
					} else { // Si viene con error
						colaBean.setRespuesta(resultbeanMatUni.getResultadosUnitarios().get(new Long(1)).getMensaje());
						tramite.setRespuesta(resultbeanMatUni.getResultadosUnitarios().get(new Long(1)).getMensaje());
						tramite.setEstado(new BigDecimal(EstadoTramiteAnuntis.Pendiente_Envio.getValorEnum()));
					}
				}
			}
			log.info(PROCESO + ConstantesProcesos.PROCESO_ATEM_ANUNTIS + " -- Peticion Procesada");

			// Cambio el estado del trámite
			if (tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Consultado_AVPO.getValorEnum())) == 0) {
				tramite.setFechaPresentacion(Calendar.getInstance().getTime());
			} else {
				tramite.setEstado(new BigDecimal(EstadoTramiteAnuntis.Finalizado_Con_Error.getValorEnum()));
			}
			tramiteAnuntisDAO.saveOrUpdate(tramite);

			// ******************************************************************************************************************************************************
			String mensajeEjecucion = null;
			mensajeEjecucion = informesRecibidos == 0 ? ConstantesProcesos.INTEVE_MENSAJE_EJECUCION_CORRECTA_SIN_INFORMES : ConstantesProcesos.INTEVE_MENSAJE_EJECUCION_CORRECTA;

			colaBean.setRespuesta(mensajeEjecucion);
			finalizacionCorrectaAtem(jobExecutionContext, colaBean, null);
			log.info(PROCESO + ConstantesProcesos.PROCESO_ATEM_ANUNTIS + " -- Proceso ejecutado correctamente");
		} catch(RemoteException re) {
			marcarSolicitudConErrorServicio(colaBean,re.getMessage(),jobExecutionContext);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean,ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.ERROR_DE_WEBSERVICE,ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion) {
			sinPeticiones(jobExecutionContext);
			log.info(sinSolicitudesExcepcion.getMensajeError1());
		} catch (TramiteNoRecuperadoExcepcion tramiteNoRecuperadoExcepcion) {
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);

			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO,ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		} catch (DescontarCreditosExcepcion descontarCreditosExcepcion) {
			try {
				finalizarTransaccionConError(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				marcarSolicitudConErrorServicio(colaBean, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(), jobExecutionContext);
			} catch (BorrarSolicitudExcepcion e) {
				marcarSolicitudConErrorServicio(colaBean, e.getMensajeError1(), jobExecutionContext);
			}
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS, ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion){
			tratarRecuperable(jobExecutionContext, colaBean, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO, ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		} catch (ExpedienteSinSolicitudesExcepcion e) {
			marcarSolicitudConErrorServicio(colaBean, e.getMensajeError1(), jobExecutionContext);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO, ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		} catch (OegamExcepcion oegamEx){
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR);
			log.error(oegamEx.getMensajeError1(), oegamEx);
			String error = oegamEx.getMensajeError1();
			if (error == null || error.equals("")) {
				error = oegamEx.toString();
			}
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error, ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		} catch (Exception e) {
			log.error("Excepcion Proceso ATEM para Anuntis", e);
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error);
			marcarSolicitudConErrorServicio(colaBean, error, jobExecutionContext);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error, ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		} catch (Throwable e) {
			log.error("Excepcion Proceso ATEM para Anuntis", e);
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ " : " + error);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error, ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
		}
	}

	/**
	 * Método que nos devuelve el informe solicitado
	 * @param tramiteTrafSolInfo
	 * @param mensaje
	 * @return
	 * @throws ParserConfigurationException
	 * @throws JRException
	 * @throws UnknownHostException
	 * @throws OegamExcepcion
	 */
	private byte[] generarInformeATEM(String tasa, String xmlDatos, String tagCabecera, String formatoSalida)
			throws UnknownHostException, JRException, ParserConfigurationException, OegamExcepcion {
		byte[] byteArray = null;

		String rutaCarpeta;
		String nombreInforme;

		try {
			rutaCarpeta = gestorPropiedades.valorPropertie(ConstantesTrafico.INTEVE_MASIVO_PLANTILLAS);
			nombreInforme = gestorPropiedades.valorPropertie(ConstantesTrafico.INTEVE_MASIVO_PLANTILLAS_NOMBRE);
		} catch (Exception e) {
			throw new OegamExcepcion("Error al generar el informe solicitado. No se ha podido recuperar la plantilla");
		}

		// Añadimos los parámetros del informe
		Map<String, Object> params = new HashMap<>();
		params.put("SUBREPORT_DIR", rutaCarpeta);
		params.put("rutaImagenes", rutaCarpeta);
		params.put("TASA", tasa);

		params.put("NOMBREREPRESOLICITANTE", null);
		params.put("DOCUMENTOREPRESOLICITANTE", null);
		params.put("COLEGIOREPRESOLICITANTE", null);
		params.put("NUMEROCOLEGIADOREPRESOLICITANTE", null);
		params.put("NOMBRESOLICITANTE", null);
		params.put("DOCUMENTOSOLICITANTE", null);

		ReportExporter reportExporter = new ReportExporter();

		try {
			byteArray = reportExporter.generarInforme(rutaCarpeta, nombreInforme, formatoSalida, xmlDatos, tagCabecera, params, null);
		} catch (JRException e) {
			log.error("Error generando PDF", e);
			throw e;
		} catch (ParserConfigurationException e) {
			log.error("Error generando PDF", e);
			throw e;
		}
		return byteArray;
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Soy el proceso Atem ejecutando el cambio de Número de instancias");
	}

	private void finalizacionCorrectaAtem(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta) throws BorrarSolicitudExcepcion {
		log.info("Ejecucion correcta del proceso ATEM");
		peticionCorrecta(jobExecutionContext);
		getModeloSolicitud().borrarSolicitud(solicitud.getIdEnvio(), respuesta);
		ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_EXCEPCION, solicitud.getRespuesta(), ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
	}

	/* ********************************************************** */
	/* MODELOS ************************************************** */
	/* ********************************************************** */

	public ModeloAcciones getModeloAcciones() {
		if (modeloAcciones == null) {
			modeloAcciones = new ModeloAcciones();
		}
		return modeloAcciones;
	}

	public void setModeloAcciones(ModeloAcciones modeloAcciones) {
		this.modeloAcciones = modeloAcciones;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

}