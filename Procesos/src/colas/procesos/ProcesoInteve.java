package colas.procesos;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.consultasTGate.model.enumerados.OrigenTGate;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
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
import oegam.constantes.ConstantesPQ;
import trafico.avpogestbasti.TN3270.Bsti;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.avpobastigest.AvpoBean;
import trafico.beans.avpobastigest.BstiBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.inteve.AplicacionInteve;
import trafico.inteve.ConstantesInteve;
import trafico.inteve.MotivoConsultaInteve;
import trafico.modelo.ModeloAcciones;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.utiles.enumerados.ResultadoAvpo;
import trafico.utiles.enumerados.TipoAccion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.BorrarSolicitudExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TramiteNoRecuperadoExcepcion;

/**
 * Gestiona las solicitudes de informes telemáticos de vehículos (INTEVE)
 *
 */
public class ProcesoInteve extends ProcesoBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesoInteve.class);
	private Integer hiloActivo;

	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloSolicitudDatos modeloSolicitudDatos;
	private ModeloAcciones modeloAcciones;

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
		ColaBean colaBean = new  ColaBean(); 
		int informesRecibidos = 0;
		try {
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE + " -- Buscando Solicitud");
			// Recupera la solicitud de la tabla cola:
			colaBean = getModeloSolicitud().tomarSolicitud(ConstantesProcesos.PROCESO_INTEVE, hiloActivo.toString());
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE + " -- Solicitud " + ConstantesProcesos.PROCESO_INTEVE + " encontrada");
			SolicitudDatosBean solicitudDatosBean = new SolicitudDatosBean(true);
			ContratoUsuarioVO contrato = utilesColegiado.getContratoUsuario(colaBean.getIdUsuario().toString());
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE + " -- Solicitud " + ConstantesProcesos.PROCESO_INTEVE + " encontrada.");
			if(contrato == null || contrato.getId().getIdContrato() == null){
				String mensajeError = "No se ha recuperado el identificador del contrato de la solicitud con identificador: " + colaBean.getIdEnvio();
				log.error(mensajeError);
				throw new Exception(mensajeError);
			}
			
			// Recupera los detalles del trámite:
			HashMap<String, Object> resultadoObtenerDetalle = getModeloSolicitudDatos().obtenerDetalle(colaBean.getIdTramite(), 
					colaBean.getIdUsuario(), new BigDecimal(contrato.getId().getIdContrato()), contrato.getContrato().getColegiado().getNumColegiado());
			if(resultadoObtenerDetalle == null){
				String mensajeError = "No se ha recuperado el tramite de la solicitud";
				log.error(mensajeError);
				
				// DRC@08-03-2013 Incidencia Mantis: 3522
				getModeloAcciones().cerrarAccion(TipoAccion.SOLICITUDINTEVE.getValorEnum(),
						colaBean.getIdUsuario(),
						colaBean.getIdTramite(),
						mensajeError);
				
				throw new TramiteNoRecuperadoExcepcion(mensajeError);
			}
			
			// Extrae las solicitudes de informes:
			solicitudDatosBean = (SolicitudDatosBean) resultadoObtenerDetalle.get(ConstantesPQ.BEANPANTALLA);

			// *****************************************************************************************************************************************************
			ResultBean resultBean= new ResultBean();

			List<AvpoBean> listaAvpo = new ArrayList<AvpoBean>();
			AvpoBean bean = null;
			List <SolicitudVehiculoBean> solicitudesAux = new ArrayList<SolicitudVehiculoBean>();

			if (solicitudDatosBean.getTramiteTrafico().getEstado()!= null && 
					solicitudDatosBean.getTramiteTrafico().getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF)){
				log.error("El estado del trámite no permite la solicitud de informes. Identificador de la solicitud: " + colaBean.getIdEnvio());
			}

			// Recupera el valor del parámetro 'Motivo de la consulta' desde una propiedad:
			MotivoConsultaInteve motivoConsultaInteve = getMotivoConsulta();
			if(motivoConsultaInteve == null) {
				String mensajeError = "No se ha configurado correctamente la propiedad que establece el valor del parámetro 'Motivo de la consulta'";
				log.error(mensajeError);
				throw new Exception(mensajeError);
			}

			// Mapa para construir el zip (contendrá los informes del expediente)
			HashMap<String, byte[]> map = new HashMap<String, byte[]>();

			// Contadores:
			int solicitudesDeInforme = solicitudDatosBean.getSolicitudesVehiculos().size();
			informesRecibidos = 0;
			
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE + " -- Procesando petición");
			// Recorre las solicitudes del trámite:
			for (SolicitudVehiculoBean solicitudAux : solicitudDatosBean.getSolicitudesVehiculos()) {
				String matricula =solicitudAux.getVehiculo().getMatricula();
				// Verifica que la solicitud del vehículo está pendiente:
				if (solicitudAux.getEstado() != null && solicitudAux.getEstado().equals(ResultadoAvpo.Pendiente)){
					bean = new AvpoBean();
					//Si no hay matricula primero lanza la consulta BASTI:					
					if (matricula== null || "".equals(matricula)){
						Bsti basti = new Bsti();		
						BstiBean bstiBean = basti.obtenerMatricula(solicitudAux.getVehiculo().getBastidor(), solicitudAux.getNumExpediente(), colaBean.getIdUsuario(), contrato.getContrato().getColegiado().getNumColegiado(), solicitudAux.getVehiculo().getIdVehiculo().longValue(), OrigenTGate.ProcesoInteve.getValorEnum());
						if(bstiBean.getError()){
							bean.setError(Boolean.TRUE);
							bean.setRepetir(true);
							bean.setMatricula("");
							bean.setTasa(solicitudAux.getTasa().getCodigoTasa());
							bean.setMensaje(bstiBean.getMensaje());
						}else{
							matricula = bstiBean.getMatricula();
						}
					}

					if (matricula != null && !"".equals(matricula)){
						solicitudAux.getVehiculo().setMatricula(matricula);
						if(solicitudAux.getTasa() != null && solicitudAux.getVehiculo() != null){
							log.error("INTEVE. TRAZA DE CONTROL. Se va a realizar solicitud de INTEVE con la tasa de codigo: " + solicitudAux.getTasa().getCodigoTasa() +
									" para la matricula: " + solicitudAux.getVehiculo().getMatricula());
						}
						AplicacionInteve aplicacionInteve = new AplicacionInteve(solicitudAux.getTasa().getCodigoTasa(),solicitudAux.getVehiculo().getMatricula(),
								null, motivoConsultaInteve);
						ResultBean resultBeanInteve = aplicacionInteve.solicitarInforme();
						bean = convertirResultBeanAvpoBean(solicitudAux.getVehiculo().getMatricula(), solicitudAux.getTasa().getCodigoTasa(), resultBeanInteve);
						// Mete los datos en el mapa que luego los grabará en el disco:
						if(bean.getError() == false){
							String nombreFichero = solicitudDatosBean.getTramiteTrafico().getNumExpediente().toString() + "_" + 
									matricula + ".zip";
							// Extrae del zip el xml del justificante de salida del informe de la DGT:
							byte[] sinXml = AplicacionInteve.descargarSeleccionJustiticantesZip((byte[]) resultBeanInteve.getAttachment("bytesFichero"));
							map.put(nombreFichero, sinXml);
							// Descomentar cuando se parametrize la documentación a descargar desde la página del 'imprimir':
							// map.put(nombreFichero,(byte[])resultBeanInteve.getAttachment("bytesFichero"));
							informesRecibidos ++;
						}else{
							// Hay error pero no implica que no se puedan solicitar los siguientes informes. 
							// Actualiza última ejecución con error, pero no detiene la ejecución del hilo
							colaBean.setRespuesta(bean.getMensaje());
							ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_NO_CORRECTA ,colaBean.getRespuesta(),ConstantesProcesos.PROCESO_INTEVE);
							//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_NO_CORRECTA, null);
						}
					}

					bean.setFechaInforme(new Date());

					if (bean.isRepetir()){
						solicitudAux.setEstado(ResultadoAvpo.Pendiente.getValorEnum());
					}else{

						solicitudAux.setEstado(ResultadoAvpo.Recibido.getValorEnum());

						// Descuento de crédito:
						HashMap<String, Object> resultado = getModeloCreditosTrafico().descontarCreditos(
								contrato.getId().getIdContrato().toString(),
								utiles.convertirIntegerABigDecimal(1),
								"T4",colaBean.getIdUsuario());

						ResultBean resultadoProcedimiento =(ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);

						if(resultadoProcedimiento.getError()){
							String mensajeError = "Error al descontar créditos de la operación";
							resultBean.setError(true);
							log.error(mensajeError);
							throw new DescontarCreditosExcepcion(resultadoProcedimiento.getMensaje());
						} else {
							try {
								servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.AVPO, contrato.getId().getIdContrato(), "T4", solicitudDatosBean.getTramiteTrafico().getNumExpediente().toString());
							} catch(Exception e) {
								log.error("No se pudo trazar el gasto de creditos", e);
							}
						}

					}
					solicitudAux.setResultado(bean.getMensaje());
					listaAvpo.add(bean);
				}

				//Añado la solicitud
				solicitudesAux.add(solicitudAux);
			}
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE + " -- Peticion Procesada");

			try {
				if (map != null && map.size()>0) {
					guardaInforme(map, solicitudDatosBean);
				}
			} catch (Throwable e) {
				String mensajeError = "Error creando el fichero de los informes recibidos";
				log.error(mensajeError, e);
				
				// DRC@08-03-2013 Incidencia Mantis: 3522
				getModeloAcciones().cerrarAccion(TipoAccion.SOLICITUDINTEVE.getValorEnum(),
						colaBean.getIdUsuario(),
						colaBean.getIdTramite(),
						mensajeError);
				
				throw new Exception(mensajeError);
			}

			solicitudDatosBean.setSolicitudesVehiculos(solicitudesAux);
			
			// Cambia al estado correspondiente:
			EstadoTramiteTrafico nuevoEstado = null;
			if(informesRecibidos == 0){
				nuevoEstado = EstadoTramiteTrafico.Finalizado_Con_Error;
			}else if(informesRecibidos < solicitudesDeInforme){
				nuevoEstado = EstadoTramiteTrafico.Finalizado_Parcialmente;
			}else{
				nuevoEstado = EstadoTramiteTrafico.Finalizado_PDF;
			}
			// WORKAROUND para no sacar de la cola los trámites finalizados con error
			if (!EstadoTramiteTrafico.Finalizado_Con_Error.equals(nuevoEstado)) {
				BeanPQCambiarEstadoTramite beanPQ = new BeanPQCambiarEstadoTramite();
				beanPQ.setP_ESTADO(new BigDecimal(nuevoEstado.getValorEnum()));
				beanPQ.setP_NUM_EXPEDIENTE(solicitudDatosBean.getTramiteTrafico().getNumExpediente());
				beanPQ.setP_ID_USUARIO(colaBean.getIdUsuario());
				HashMap<String, Object> resultado = getModeloTrafico().cambiarEstadoTramite(beanPQ);
				resultBean = (ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);
			} else {
				nuevoEstado = EstadoTramiteTrafico.Pendiente_Envio;
				resultBean.setError(true);
			}
			

			if (resultBean.getError()==true){
				// Ha ocurrido un error cambiando el estado del trámite:
				String mensajeError = resultBean.getMensaje();
				
				// DRC@08-03-2013 Incidencia Mantis: 3522
				getModeloAcciones().cerrarAccion(TipoAccion.SOLICITUDINTEVE.getValorEnum(),
						colaBean.getIdUsuario(),
						colaBean.getIdTramite(),
						"Error del tramite");
				
				throw new CambiarEstadoTramiteTraficoExcepcion(mensajeError);
			}
			solicitudDatosBean.getTramiteTrafico().setEstado(nuevoEstado.getValorEnum());
			
			// Guarda el trámite:
			HashMap<String, Object> resultadoGuardar = getModeloSolicitudDatos().guardarSolicitudDatos(solicitudDatosBean,
					colaBean.getIdUsuario(), solicitudDatosBean.getTramiteTrafico().getIdContrato(), solicitudDatosBean.getTramiteTrafico().getNumColegiado());
			resultBean = (ResultBean) resultadoGuardar.get(ConstantesPQ.RESULTBEAN);
			solicitudDatosBean = (SolicitudDatosBean) resultadoGuardar.get(ConstantesPQ.BEANPANTALLA);
			if (resultBean.getError()==true){
				String mensajeError = "Error al guardar el trámite de la petición con identificador: " + colaBean.getIdEnvio();
				log.error(mensajeError);
				
				// DRC@08-03-2013 Incidencia Mantis: 3522
				getModeloAcciones().cerrarAccion(TipoAccion.SOLICITUDINTEVE.getValorEnum(),
						colaBean.getIdUsuario(),
						colaBean.getIdTramite(),
						"Error al guardar el trámite");
				throw new Exception(mensajeError);
			}

			// ******************************************************************************************************************************************************
			
			String mensajeEjecucion = null;
			String respuesta = "INTEVE ";
			if(informesRecibidos == 0){
				mensajeEjecucion = ConstantesProcesos.INTEVE_MENSAJE_EJECUCION_CORRECTA_SIN_INFORMES;
				respuesta += "NO SE HA CONTEMPLADO .COMPLETADA PARCIALMENTE";
			}else if(informesRecibidos < solicitudesDeInforme){
				mensajeEjecucion = ConstantesProcesos.INTEVE_MENSAJE_EJECUCION_PARCIALMENTE_CORRECTA;
				respuesta += "NO SE HA CONTEMPLADO .COMPLETADA PARCIALMENTE";
			}else{
				mensajeEjecucion = ConstantesProcesos.INTEVE_MENSAJE_EJECUCION_CORRECTA;
				respuesta += "COMPLETADA"; 
			}
			
			// DRC@08-03-2013 Incidencia Mantis: 3522
			getModeloAcciones().cerrarAccion(TipoAccion.SOLICITUDINTEVE.getValorEnum(),
					colaBean.getIdUsuario(),
					colaBean.getIdTramite(),
					respuesta);
			
			colaBean.setRespuesta(mensajeEjecucion);
			finalizacionCorrectaInteve(jobExecutionContext, colaBean, null);
			log.info("Proceso " + ConstantesProcesos.PROCESO_INTEVE + " -- Proceso ejecutado correctamente");

		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
			sinPeticiones(jobExecutionContext);
			log.info(sinSolicitudesExcepcion.getMensajeError1());			
		} catch (TramiteNoRecuperadoExcepcion tramiteNoRecuperadoExcepcion){
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
			log.logarOegamExcepcion(tramiteNoRecuperadoExcepcion.getMensajeError1(), tramiteNoRecuperadoExcepcion);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO,ConstantesProcesos.PROCESO_INTEVE);
			//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, 
				//	ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
		} catch (DescontarCreditosExcepcion descontarCreditosExcepcion){
			try {
				finalizarTransaccionConError(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				marcarSolicitudConErrorServicio(colaBean, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(), jobExecutionContext);
			} catch (BorrarSolicitudExcepcion e) {
				marcarSolicitudConErrorServicio(colaBean,e.getMensajeError1(),jobExecutionContext);
			}
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS,ConstantesProcesos.PROCESO_INTEVE);
			//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion){
			if (informesRecibidos == 0) {
				marcarSolicitudConErrorServicio(colaBean,"Incidencias en el servicio INTEVE",jobExecutionContext);
			} else {
				tratarRecuperable(jobExecutionContext, colaBean, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO,ConstantesProcesos.PROCESO_INTEVE);
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO);
			}
		} catch (OegamExcepcion oegamEx){
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR); 
			log.logarOegamExcepcion(oegamEx.getMensajeError1(), oegamEx);
			String error = oegamEx.getMensajeError1();
			if(error == null || error.equals("")){
				error = oegamEx.toString();
			}
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_INTEVE);
			//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		} catch (Exception e){
			log.error("Excepcion Proceso INTEVE", e); 
			String error = e.getMessage();
			if(error == null || error.equals("")){
				error = e.toString();
			}
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error); 
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_INTEVE);
			// ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		} catch (Throwable e){
			log.error("Excepcion Proceso INTEVE", e); 
			String error = e.getMessage();
			if(error == null || error.equals("")){
				error = e.toString();
			}
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ " : " + error);
			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION ,error,ConstantesProcesos.PROCESO_INTEVE);
			//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		}
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.debug("cambiar numero instancias en ProcesoInteve");

	}

	/**
	 * Construye un AvpoBean.
	 * @param ResultBean
	 * @return AvpoBean
	 */
	private AvpoBean convertirResultBeanAvpoBean(String matricula, String codigoTasa, ResultBean resultBean)throws Exception {
		AvpoBean avpoBean = new AvpoBean();
		avpoBean.setMatricula(matricula);
		avpoBean.setTasa(codigoTasa);
		
		if(resultBean.getError() == true) {
			avpoBean.setMensaje(resultBean.getMensaje());
			avpoBean.setError(Boolean.TRUE);
			avpoBean.setRepetir(true);
		} else {
			avpoBean.setMensaje(resultBean.getMensaje());
			avpoBean.setError(Boolean.FALSE);
			avpoBean.setRepetir(false);
		}
		return avpoBean;
	}

	/**
	 * Lee un properties para recuperar el valor del parámetro motivo consulta con el que hay
	 * que realizar las peticiones de los inteves
	 * @return Instancia de la enumeración MotivoConsultaInteve
	 * @throws OegamExcepcion 
	 */
	public MotivoConsultaInteve getMotivoConsulta() throws OegamExcepcion {
		String propiedadMotivoConsulta = gestorPropiedades.valorPropertie(ConstantesInteve.REF_PROP_INTEVE_MOTIVO_CONSULTA);
		MotivoConsultaInteve motivoConsultaInteve = MotivoConsultaInteve.recuperar(propiedadMotivoConsulta);
		return motivoConsultaInteve;
	}

	/**
	 * Guarda un zip con los documentos
	 * @param datos
	 * @param nombreFichero
	 * @throws Throwable
	 */
	private void guardaInforme(Map<String, byte[]> datos, SolicitudDatosBean solicitud) throws IOException, OegamExcepcion {
		log.trace("Entra en guardaInforme");

		FicheroBean fichero = new FicheroBean();
		fichero.setSobreescribir(false);
		fichero.setTipoDocumento(ConstantesGestorFicheros.SOLICITUD_INFORMACION);
		fichero.setFecha(Utilidades.transformExpedienteFecha(solicitud.getTramiteTrafico().getNumExpediente().toString()));
		fichero.setNombreZip("Consultas_" + solicitud.getTramiteTrafico().getNumExpediente().toString());
		fichero.setListaByte(datos);

		gestorDocumentos.empaquetarEnZipByte(fichero);
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

	public ModeloSolicitudDatos getModeloSolicitudDatos() {
		if (modeloSolicitudDatos == null) {
			modeloSolicitudDatos = new ModeloSolicitudDatos();
		}
		return modeloSolicitudDatos;
	}

	public void setModeloSolicitudDatos(ModeloSolicitudDatos modeloSolicitudDatos) {
		this.modeloSolicitudDatos = modeloSolicitudDatos;
	}


}