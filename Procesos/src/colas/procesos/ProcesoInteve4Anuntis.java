package colas.procesos;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
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
import hibernate.dao.trafico.TramiteAnuntisDAO;
import hibernate.entities.trafico.TramiteAnuntis;
import oegam.constantes.ConstantesPQ;
import trafico.inteve.AplicacionInteve;
import trafico.inteve.ConstantesInteve;
import trafico.inteve.MotivoConsultaInteve;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.utiles.enumerados.EstadoTramiteAnuntis;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TramiteNoRecuperadoExcepcion;

/**
 * Gestiona las solicitudes de informes telemáticos de vehículos (INTEVE) para
 * los tramites de Anuntis
 */
public class ProcesoInteve4Anuntis extends ProcesoBase {
	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoInteve4Anuntis.class);
	private Integer hiloActivo;

	private ModeloCreditosTrafico modeloCreditosTrafico;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCreditoFacturado servicioCreditoFacturado;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		// Ya se hace en el padre ProcesoBase
		// Forzamos la inyección de dependencias de las clases anotadas como 'Autowired'
		//SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		// Hilo/cola en ejecución:
		hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		ColaBean colaBean = new ColaBean();
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " -- Buscando Solicitud");
			// Recupera la solicitud de la tabla cola:
			colaBean = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_INTEVE_ANUNTIS, hiloActivo.toString());
			ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(colaBean.getIdUsuario().toString());
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " -- Solicitud " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " encontrada.");
			if (contrato == null || contrato.getId().getIdContrato() == null) {
				String mensajeError = "No se ha recuperado el identificador del contrato de la solicitud con identificador: " + colaBean.getIdEnvio();
				log.error(mensajeError);
				throw new Exception(mensajeError);
			}
			// Recupera los detalles del trámite:
			// DAO de Tramites de anuntis
			TramiteAnuntisDAO tramiteAnuntisDAO = new TramiteAnuntisDAO();
			TramiteAnuntis tramite = tramiteAnuntisDAO.get(colaBean.getIdTramite().longValue(), "tasa");

			if (tramite == null) {
				String mensajeError = "No se ha recuperado el tramite de la solicitud";
				log.error(mensajeError);
				throw new TramiteNoRecuperadoExcepcion(mensajeError);
			}

			// *****************************************************************************************************************************************************

			ResultBean resultBean = new ResultBean();

			if (tramite.getEstado() != null
					&& (tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Consultado_AVPO.getValorEnum()))==0
							|| tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Procesado.getValorEnum()))==0)) {
				log.error("El estado del trámite no permite la solicitud de informes. Identificador de la solicitud: " + colaBean.getIdEnvio());
			}

			// Recupera el valor del parámetro 'Motivo de la consulta' desde una
			// propiedad:
			MotivoConsultaInteve motivoConsultaInteve = getMotivoConsulta();
			if (motivoConsultaInteve == null) {
				String mensajeError = "No se ha configurado correctamente la propiedad que establece el valor del parámetro 'Motivo de la consulta'";
				log.error(mensajeError);
				throw new Exception(mensajeError);
			}

			// Contendrá el informe del expediente)
			byte[] contentFile = null;

			int informesRecibidos = 0;

			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " -- Procesando petición");
			// Verifica que la solicitud del vehículo está pendiente:
			if (tramite.getEstado() != null && tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Pendiente_Envio.getValorEnum().toString()))==0) {

				if (tramite.getMatricula() != null && !tramite.getMatricula().trim().isEmpty()) {
					AplicacionInteve aplicacionInteve = new AplicacionInteve(tramite.getTasa().getCodigoTasa(), tramite.getMatricula(), null, motivoConsultaInteve);
					ResultBean resultBeanInteve = aplicacionInteve.solicitarInforme();

					// Guardamos los datos que luego se grabarán en el disco:
					if (!resultBeanInteve.getError()) {
						// Extrae del zip el xml del justificante de salida del
						// informe de la DGT:
						contentFile = AplicacionInteve.descargarSeleccionJustiticantesZip((byte[]) resultBeanInteve.getAttachment("bytesFichero"));

					} else {
						// Hay error pero no implica que no se puedan solicitar
						// los siguientes informes.
						// Actualiza última ejecución con error, pero no detiene
						// la ejecución del hilo
						colaBean.setRespuesta(resultBeanInteve.getMensaje());
						ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_NO_CORRECTA ,colaBean.getRespuesta(),ConstantesProcesos.PROCESO_INTEVE);
						//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
					}

					if (resultBeanInteve.getError()) {
						tramite.setEstado(new BigDecimal(EstadoTramiteAnuntis.Pendiente_Envio.getValorEnum()));

					} else {

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
							resultBean.setError(true);
							log.error(mensajeError);
							throw new DescontarCreditosExcepcion(resultadoProcedimiento.getMensaje());
						} else {
							try {
								servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.AVPO, utilesColegiado.getIdContratoSession(), "T4", tramite.getNumExpediente().toString());
							} catch(Exception e) {
								log.error("No se pudo trazar el gasto de creditos", e);
							}
						}

						try {
							escribeZipEnDisco(contentFile, tramite.getNumExpediente(), tramite.getMatricula());
						} catch (Throwable e) {
							String mensajeError = "Error creando el fichero de los informes recibidos";
							log.error(mensajeError);
							throw new Exception(mensajeError);
						}

						informesRecibidos++;
					}
				}
			}
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " -- Peticion Procesada");

			// Cambio el estado del trámite
			if (tramite.getEstado().compareTo(new BigDecimal(EstadoTramiteAnuntis.Consultado_AVPO.getValorEnum()))==0) {
				tramite.setFechaPresentacion(Calendar.getInstance().getTime());
			} else {
				//tramite.setEstado(new BigDecimal(EstadoTramiteAnuntis.Finalizado_Con_Error.getValorEnum()));
				throw new CambiarEstadoTramiteTraficoExcepcion("Incidencias en el servicio INTEVE");
			}
			tramiteAnuntisDAO.saveOrUpdate(tramite);

			// ******************************************************************************************************************************************************


			String mensajeEjecucion = null;
			if (informesRecibidos == 0) {
				mensajeEjecucion = ConstantesProcesos.INTEVE_MENSAJE_EJECUCION_CORRECTA_SIN_INFORMES;
			} else {
				mensajeEjecucion = ConstantesProcesos.INTEVE_MENSAJE_EJECUCION_CORRECTA;
			}
			colaBean.setRespuesta(mensajeEjecucion);
			finalizacionCorrectaInteve(jobExecutionContext, colaBean, null);
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " -- Proceso ejecutado correctamente. " + colaBean.getRespuesta());

		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
			sinPeticiones(jobExecutionContext); 
			log.info(sinSolicitudesExcepcion.getMensajeError1());
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " -- Error Recibido: " + sinSolicitudesExcepcion.getMensajeError1());
		} catch (TramiteNoRecuperadoExcepcion tramiteNoRecuperadoExcepcion) {
			tratarRecuperable(
							jobExecutionContext,
							colaBean,
							ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
			log.logarOegamExcepcion(tramiteNoRecuperadoExcepcion.getMensajeError1(), tramiteNoRecuperadoExcepcion);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO,ConstantesProcesos.PROCESO_INTEVE);
			/*ejecucionesProcesosDAO.actualizarUltimaEjecucion(
							colaBean,
							ConstantesProcesos.EJECUCION_EXCEPCION,
							ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);*/
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE_ANUNTIS + " -- Error Recibido: " + ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
		} catch (DescontarCreditosExcepcion descontarCreditosExcepcion) {
			try {
				finalizarTransaccionConError(
						jobExecutionContext, colaBean,
						ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				marcarSolicitudConErrorServicio(colaBean,
								cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(),
								jobExecutionContext);
			} catch (BorrarSolicitudExcepcion e) {
				marcarSolicitudConErrorServicio(colaBean, e.getMensajeError1(), jobExecutionContext);
			}
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS,ConstantesProcesos.PROCESO_INTEVE);
		/*	ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean,
					ConstantesProcesos.EJECUCION_EXCEPCION,
					ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);*/
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
			if ("Incidencias en el servicio INTEVE".equals(cambiarEstadoTramiteTraficoExcepcion.getMessage())){
				marcarSolicitudConErrorServicio(colaBean, cambiarEstadoTramiteTraficoExcepcion.getMessage(), jobExecutionContext);
			} else {
				tratarRecuperable(jobExecutionContext, colaBean,
						cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO,ConstantesProcesos.PROCESO_INTEVE);
			/*	ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean,
						ConstantesProcesos.EJECUCION_EXCEPCION,
						ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO);*/
			}
		} catch (OegamExcepcion oegamEx) {
			tratarRecuperable(jobExecutionContext, colaBean,
					ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR);
			log.logarOegamExcepcion(oegamEx.getMensajeError1(), oegamEx);
			String error = oegamEx.getMensajeError1();
			if (error == null || error.equals("")) {
				error = oegamEx.toString();
			}
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_INTEVE);
			/*ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean,
					ConstantesProcesos.EJECUCION_EXCEPCION, error);*/
		} catch (Exception e) {
			log.error("Excepcion Proceso INTEVE4ANUNTIS", e);
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			tratarRecuperable(jobExecutionContext, colaBean,
					ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : "
							+ error);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_INTEVE);
			/*ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean,
					ConstantesProcesos.EJECUCION_EXCEPCION, error);*/
		} catch (Throwable e) {
			log.error("Excepcion Proceso INTEVE4ANUNTIS", e);
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			tratarRecuperable(jobExecutionContext, colaBean,
					ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : "
							+ error);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_INTEVE);
			/*ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean,
					ConstantesProcesos.EJECUCION_EXCEPCION, error);*/
		}
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.debug("cambiar numero instancias en ProcesoInteve4Anuntis");

	}

	/**
	 * Lee un properties para recuperar el valor del parámetro motivo consulta
	 * con el que hay que realizar las peticiones de los inteves
	 * 
	 * @return Instancia de la enumeración MotivoConsultaInteve
	 * @throws OegamExcepcion
	 */
	public MotivoConsultaInteve getMotivoConsulta() throws OegamExcepcion {
		String propiedadMotivoConsulta = gestorPropiedades.valorPropertie(ConstantesInteve.REF_PROP_INTEVE_MOTIVO_CONSULTA);
		MotivoConsultaInteve motivoConsultaInteve = MotivoConsultaInteve.recuperar(propiedadMotivoConsulta);
		return motivoConsultaInteve;
	}

	/**
	 * Guarda un zip en la carpeta temporal.
	 * 
	 * @param datos
	 * @param numExpediente
	 * @param matricula
	 * @throws Throwable
	 */
	public void escribeZipEnDisco(byte[] datos, Long numExpediente, String matricula) throws Throwable {
		log.trace("Entra en escribeZipEnDisco");

		FicheroBean fichero = new FicheroBean();
		fichero.setSobreescribir(false);
		fichero.setTipoDocumento(ConstantesGestorFicheros.ANUNTIS);
		fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente.toString()));
		fichero.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_CONSULTA+numExpediente);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_ZIP);
		fichero.setFicheroByte(datos);
		
		gestorDocumentos.guardarByte(fichero);
	}
	
	private void finalizacionCorrectaInteve(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta) throws BorrarSolicitudExcepcion  {
		log.info("Ejecucion correcta del proceso INTEVE"); 
		peticionCorrecta(jobExecutionContext); 	
		getModeloSolicitud().borrarSolicitud(solicitud.getIdEnvio(),respuesta);
		ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA ,solicitud.getRespuesta(),ConstantesProcesos.PROCESO_INTEVE);
		//ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud, ConstantesProcesos.EJECUCION_CORRECTA, null);
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

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
