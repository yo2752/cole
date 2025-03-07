package colas.procesos;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import escrituras.beans.ResultBean;
import facturacion.dao.DatosDAO;
import hibernate.dao.trafico.TramiteTraficoDAO;
import hibernate.entities.general.Colegiado;
import hibernate.entities.personas.Persona;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.TramiteTrafSolInfo;
import hibernate.entities.trafico.TramiteTrafico;
import net.sf.jasperreports.engine.JRException;
import trafico.beans.RespuestaAtemBean;
import trafico.modelo.ModeloAcciones;
import trafico.modelo.ModeloAtem;
import trafico.utiles.UtilesWSMate;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.ResultadoAvpo;
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

/**
 * Gestiona las solicitudes de informes telemáticos de vehículos (INTEVE)
 *
 */
public class ProcesoAtem extends ProcesoBase {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoAtem.class);
	private Map<String, List<TramiteTrafSolInfo>> mapaDeReferencias;
	private List<TramiteTrafSolInfo> matriculas;
	private List<TramiteTrafSolInfo> bastidores;
	private Map<String, byte[]> mapaBytesApdf;

	
	private static final String NOMBRE_NODO = "vehiculo";
	private static final int MAX_RESULT_LENGTH = 300;
	private static final String ERROR_RESPUESTA_NO_PROCESADA = " La respuesta obtenida no se ha podido procesar";
	private static final String ERROR_REFERENCIA = "Se ha producido un error al obtener las solicitudes de la referencia: ";
	private static final String ERROR_COMUNICACION_DGT = "Se ha producido un error de comunicación con DGT: ";
	private static final String ERROR_DETALLE_MATRICULA = "Se ha producido un error al obtener el detalle de la matricula: ";
	private static final String ERROR_PETICION_MASIVA_MATRICULA = "Se ha producido un error al realizar petición masiva para matriculas: ";
	private static final String ERROR_DETALLE_BASTIDOR = "Se ha producido un error al obtener el detalle del bastidor: ";
	private static final String ERROR_PETICION_MASIVA_BASTIDOR = "Se ha producido un error al realizar petición masiva para bastidores: ";

	private Boolean guardarXmlActivo = false;
	private ModeloAcciones modeloAcciones;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {	
		
		// Hilo/cola en ejecución:
		Integer hiloActivo = jobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
		ColaBean colaBean = new  ColaBean();

		// En el caso de error, en los dos siguientes String se guarda el resultado para actualizar la ultima ejecucion del proceso
		String resultadoEjecucion = null;
		String excepcion = null;
		try {

			log.info("Proceso " + ProcesosEnum.ATEM.getNombreEnum() + " -- Buscando Solicitud");
			// Recupera la solicitud de la tabla cola:
			colaBean = getModeloSolicitud().tomarSolicitud(ProcesosEnum.ATEM.getNombreEnum(), hiloActivo.toString());
			log.info("Proceso " + ProcesosEnum.ATEM.getNombreEnum() + " -- Solicitud " + ProcesosEnum.ATEM.getNombreEnum() + " encontrada [" + colaBean.getIdEnvio() + "]");

			if (colaBean.getIdTramite() == null) {
				String mensajeError = "No se ha recuperado el identificador del tramite de la solicitud con identificador: " + colaBean.getIdEnvio();
				log.error(mensajeError);
				throw new Exception(mensajeError);
				
			}

			// Recupera los detalles del trámite:
			TramiteTraficoDAO tramiteTraficoDAO = new TramiteTraficoDAO();
			TramiteTrafico tramiteTrafico = tramiteTraficoDAO.get(colaBean.getIdTramite().longValue(), "tramiteTrafSolInfo", "tramiteTrafSolInfo.vehiculo", "tramiteTrafSolInfo.tasa", "contrato", TramiteTrafico.class.getMethod("getIntervinienteTraficos"));

			if(tramiteTrafico == null){
				String mensajeError = "No se ha recuperado el tramite de la solicitud";
				log.error(mensajeError);
				
				getModeloAcciones().cerrarAccion(TipoAccion.ATEM.getValorEnum(),
						colaBean.getIdUsuario(),
						colaBean.getIdTramite(),
						mensajeError);
				
				throw new TramiteNoRecuperadoExcepcion(mensajeError);
			}

			// *****************************************************************************************************************************************************
			
			
			//Si el trámite está finalizado PDF no se debe volver a lanzar ninguna petición.
			if (tramiteTrafico.getEstado()!= null && 
					tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_PDF)){
				log.error("El estado del trámite no permite la solicitud de informes. Identificador de la solicitud: " + colaBean.getIdEnvio());
				throw new ExpedienteSinSolicitudesExcepcion("El estado del trámite no permite la solicitud de informes. Identificador de la solicitud: " + colaBean.getIdEnvio());
			}
			
			//Si tiene un estado que no es 25, 22 u 11 se lanza un error porque algo raro ha tenido que pasar.
			if (tramiteTrafico.getEstado()!= null && 
					(tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Con_Error))
					|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Finalizado_Parcialmente)
					|| tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Recibida_Referencia_Atem)){
				log.error("El estado del trámite se encuentra en un estado no válido. Identificador de la solicitud: " + colaBean.getIdEnvio());
				throw new ExpedienteSinSolicitudesExcepcion("El estado del trámite se encuentra en un estado no válido. Identificador de la solicitud: " + colaBean.getIdEnvio());
			}

			if(tramiteTrafico.getTramiteTrafSolInfo() == null || tramiteTrafico.getTramiteTrafSolInfo().isEmpty()){
				log.error("Proceso Atem. Se produjo un error al recoger la información del trámite " + tramiteTrafico.getNumExpediente() + " ya que este no existe");
				throw new Exception("Proceso Atem. Se produjo un error al recoger la información del trámite " + tramiteTrafico.getNumExpediente() + " ya que este no existe");
			}


			// Cargar almacenes de certificado
			new UtilesWSMate().cargarAlmacenesTrafico();

			ModeloAtem modelo = new ModeloAtem();
			/*
			 * Inicialización de variables para que cada que ejecute el proceso no tenga valores de una ejecución anterior.
			 */
			mapaDeReferencias = new HashMap<String, List<TramiteTrafSolInfo>>();
			matriculas = new ArrayList<TramiteTrafSolInfo>();
			bastidores = new ArrayList<TramiteTrafSolInfo>();
			mapaBytesApdf = new HashMap<String, byte[]>();
			
			/*
			 * Empieza a procesar las solicitudes que hay en este trámite. 
			 * Los tipos de solicitudes que puede obtener son:
			 * - Referencia: Se envío la solicitud y se recibió una referencia.
			 * - Matricula: Si hay más de una matricula se genera una masiva. Si sólo hay una matricula se genera una petición sola.
			 * - Bastidor: Si hay más de un bastidor se genera una masiva. Si sólo hay un bastidor se genera una petición sola.
			 * 
			 * En función de las solicitudes se crearan diferentes objetos con los que luego se llamará a las diferentes peticiones. 
			 */
			agrupaSolicitudesPorTipo(tramiteTrafico.getTramiteTrafSolInfo());
			 
			
			/*
			 * Una vez se han creado los objetos de "referencia, matricula masiva y unitaria, bastidor masivo y unitario" se llama a los métodos del modelo que se encargarán
			 * de hacer la petición al web service adecuado. Posteriormente hay que procesar la respuesta.
			 */
			
			/* 
			 * REFERENCIA
			 * Dentro de la lista de referencias puede haber más de una referencia.
			 * Ej. Una referencia para matriculas y otra para bastidores.
			 */
			procesaReferencias(mapaDeReferencias, modelo);


			Boolean masivasActivas;
			try {
				masivasActivas = "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ConstantesTrafico.INTEVE_ATEM_MASIVO_ACTIVO));
				try {
					guardarXmlActivo = "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ConstantesTrafico.INTEVE_GUARDAR_COPIA_XML));
				} catch (Exception e) {
					// Si no viene definido este parametro, no se guardan y ya está, sólo son ficheros temporales.
					guardarXmlActivo = false;
				}
			} catch (Exception e){
				throw new OegamExcepcion("Error al leer fichero de configuración");
			}

			/*
			 * MATRICULAS MASIVAS
			 */
			if (masivasActivas && matriculas.size() > 1){
				procesaMatriculasMasivas(modelo, colaBean, tramiteTrafico);
			}
			
			/*
			 * BASTIDORES MASIVOS
			 */
			if (masivasActivas && bastidores.size() > 1){
				procesaBastidoresMasivos(modelo, colaBean, tramiteTrafico);
			}
			
			/*
			 * MATRICULA UNITARIA
			 */
			if (!masivasActivas || matriculas.size() == 1){
				procesaMatriculaUnitaria(modelo, colaBean, tramiteTrafico);
			}
			
			/*
			 * BASTIDOR UNITARIO
			 */
			if (!masivasActivas || bastidores.size() == 1){
				procesaBastidorUnitario(modelo, colaBean, tramiteTrafico);
			}
			
			/*
			 * Si se ha recibido información de algún vehiculo se genera el informe pdf.
			 */
			if (mapaBytesApdf!=null && mapaBytesApdf.size()>0) {
				modelo.guardarInformesSolInfo(new BigDecimal(tramiteTrafico.getNumExpediente()), mapaBytesApdf);
			}
			
			/*
			 * Despúes de procesar las solicitudes hay que comprobar como se encuentran estas para cambiar el estado del trámite 
			 */
			
			String estadoTramite = modelo.cambiarEstadoTramite(colaBean.getIdUsuario(), tramiteTrafico);

			String mensajeEjecucion = null;
			if(estadoTramite.equals(EstadoTramiteTrafico.Finalizado_PDF.getValorEnum())){
				mensajeEjecucion = ConstantesProcesos.ATEM_MENSAJE_EJECUCION_CORRECTA;
			}else if(estadoTramite.equals(EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum())){
				mensajeEjecucion = ConstantesProcesos.ATEM_MENSAJE_EJECUCION_PARCIALMENTE_CORRECTA;
			}else if(estadoTramite.equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())){
				mensajeEjecucion = ConstantesProcesos.ATEM_MENSAJE_EJECUCION_CON_ERRORES;
			}if(estadoTramite.equals(EstadoTramiteTrafico.Recibida_Referencia_Atem.getValorEnum())){
				mensajeEjecucion = ConstantesProcesos.ATEM_MENSAJE_EJECUCION_CORRECTA;
			}

			colaBean.setRespuesta(mensajeEjecucion);
			finalizacionCorrectaAtem(jobExecutionContext, colaBean, null);
			log.info("Proceso " + ProcesosEnum.ATEM.getNombreEnum() + " -- Proceso ejecutado correctamente");
			
		}catch(RemoteException re) {
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = ConstantesProcesos.ERROR_DE_WEBSERVICE;
			marcarSolicitudConErrorServicio(colaBean,re.getMessage(),jobExecutionContext);
		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion){
			sinPeticiones(jobExecutionContext);
			log.info(sinSolicitudesExcepcion.getMensajeError1());
		} catch (TramiteNoRecuperadoExcepcion tramiteNoRecuperadoExcepcion){
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO;
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
			log.error(tramiteNoRecuperadoExcepcion.getMensajeError1(), tramiteNoRecuperadoExcepcion);
		} catch (DescontarCreditosExcepcion descontarCreditosExcepcion){
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS;
			try {
				finalizarTransaccionConError(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_AL_DESCONTAR_CRÉDITOS);
			} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
				marcarSolicitudConErrorServicio(colaBean, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1(), jobExecutionContext);
			} catch (BorrarSolicitudExcepcion e) {
				marcarSolicitudConErrorServicio(colaBean,e.getMensajeError1(),jobExecutionContext);
			}
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion){
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO;
			tratarRecuperable(jobExecutionContext, colaBean, cambiarEstadoTramiteTraficoExcepcion.getMensajeError1());
		} catch (ExpedienteSinSolicitudesExcepcion e){
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO;
			marcarSolicitudConErrorServicio(colaBean,e.getMensajeError1(),jobExecutionContext);
		} catch (OegamExcepcion oegamEx){
			log.error(oegamEx.getMensajeError1(), oegamEx);
			String error = oegamEx.getMensajeError1();
			if(error == null || error.equals("")){
				error = oegamEx.toString();
			}
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = error;
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR); 
		} catch (Exception e){
			log.error("Excepcion Proceso ATEM", e); 
			String error = e.getMessage();
			if(error == null || error.equals("")){
				error = e.toString();
			}
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = error;
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error);
		} catch (Throwable e){
			log.error("Excepcion Proceso ATEM", e); 
			String error = e.getMessage();
			if(error == null || error.equals("")){
				error = e.toString();
			}
			resultadoEjecucion = ConstantesProcesos.EJECUCION_EXCEPCION;
			excepcion = error;
			tratarRecuperable(jobExecutionContext, colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR+ " : " + error); 
		} finally {
			if (ConstantesProcesos.EJECUCION_EXCEPCION.equals(resultadoEjecucion)) {
				try {
					ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean,ConstantesProcesos.EJECUCION_EXCEPCION ,resultadoEjecucion,ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
					//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, resultadoEjecucion, excepcion);
				} catch (Exception e) {
					log.error("Error actualizando ultima ejecucion del proceso ATEM", e);
				}
			}
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
	private byte[] generarInformeATEM(TramiteTrafSolInfo infoTramite, String xmlDatos, String tagCabecera, String formatoSalida) throws UnknownHostException, JRException, ParserConfigurationException, OegamExcepcion {

		byte[] byteArray = null; 
		
		String rutaCarpeta;
		String nombreInforme;
		
		try {
			rutaCarpeta = gestorPropiedades.valorPropertie(ConstantesTrafico.INTEVE_MASIVO_PLANTILLAS);
			nombreInforme = gestorPropiedades.valorPropertie(ConstantesTrafico.INTEVE_MASIVO_PLANTILLAS_NOMBRE);
		} catch (Exception e){
			throw new OegamExcepcion("Error al generar el informe solicitado. No se ha podido recuperar la plantilla");
		}
		
		// Añadimos los parametros del informe
		Map<String, Object> params = new HashMap<String, Object>() ;
		params.put("SUBREPORT_DIR", rutaCarpeta);
		params.put("rutaImagenes", rutaCarpeta);
		params.put("TASA", infoTramite.getTasa().getCodigoTasa());
		
		// datos del representante del solicitante		
		Colegiado representanteSolicitante = utilesColegiado.getColegiado(infoTramite.getTramiteTrafico().getNumColegiado());
		
		params.put("NOMBREREPRESOLICITANTE",representanteSolicitante.getUsuario().getApellidosNombre());
		params.put("DOCUMENTOREPRESOLICITANTE", representanteSolicitante.getUsuario().getNif());
		params.put("COLEGIOREPRESOLICITANTE", "COLEGIO DE GESTORES ADMINITRATIVOS DE MADRID");
		params.put("NUMEROCOLEGIADOREPRESOLICITANTE",representanteSolicitante.getUsuario().getNumColegiado());

		
		// datos del solicitante
        // ...
        String nifSolicitante = null;
        String nombreSolicitante = "";
        for (IntervinienteTrafico interviniente: infoTramite.getTramiteTrafico().getIntervinienteTraficos()){
               if (TipoInterviniente.Solicitante.getValorEnum().equals(interviniente.getTipoIntervinienteBean().getTipoInterviniente())){
                      nifSolicitante = interviniente.getId().getNif();
                      if (nifSolicitante != null) {
	                      DatosDAO datosDAO = new DatosDAO();
	                      Persona persona = datosDAO.buscarPersona(nifSolicitante, representanteSolicitante.getUsuario().getNumColegiado());
	                      
	                      if (persona != null) {
	                    	  if (persona.getNombre() != null) {
	                    		  nombreSolicitante = persona.getNombre();
	                    	  }
	                    	  
	                    	  if (persona.getApellido1RazonSocial() != null) {	                    		                      		  
	                    		  nombreSolicitante += " " + persona.getApellido1RazonSocial();
	                    	  }
	                    	  
	                    	  if (persona.getApellido2() != null) {	                    		                      		  
	                    		  nombreSolicitante += " " + persona.getApellido2();
	                    	  }
	                    	  
	                    	  nombreSolicitante = nombreSolicitante.trim();
	                      }
	                      
                      }
                      break;
               }
        }

		params.put("NOMBRESOLICITANTE", nombreSolicitante.isEmpty()?null:nombreSolicitante);
		params.put("DOCUMENTOSOLICITANTE", nifSolicitante);
		
		ReportExporter reportExporter = new ReportExporter();
		
		try {
			byteArray = reportExporter.generarInforme(rutaCarpeta, nombreInforme, formatoSalida, xmlDatos, tagCabecera, params, null);			
		} catch (JRException e) {
			log.error("Error generando PDF", e);
			throw e;
		}  catch (ParserConfigurationException e) {
			log.error("Error generando PDF", e);
			throw e;
		}
		
		return byteArray;
	}

	private void agrupaReferencias(TramiteTrafSolInfo tramiteTrafSolInfo){
		//Si la referencia se encuentra en el mapa, se incluye, y si no se inicializa la lista
		if (!mapaDeReferencias.containsKey(tramiteTrafSolInfo.getReferenciaAtem())){
			mapaDeReferencias.put(tramiteTrafSolInfo.getReferenciaAtem(), new ArrayList<TramiteTrafSolInfo>());
		}
		mapaDeReferencias.get(tramiteTrafSolInfo.getReferenciaAtem()).add(tramiteTrafSolInfo);
	}

	private void agrupaSolicitudesPorTipo(Set<TramiteTrafSolInfo> listaTramiteTrafSolInfo) throws OegamExcepcion{
		for (TramiteTrafSolInfo tramiteTrafSolInfo: listaTramiteTrafSolInfo){
			//Al recojer todas las solicitudes puede haber algunas correctamente tramitadas. Entonces estarían en estado "1 - Recibido". No habría que hacer nada con ellas.
			if (!tramiteTrafSolInfo.getEstado().equals(new BigDecimal(ResultadoAvpo.Recibido.getValorEnum()))){
				//Si tiene referencia.
				if (tramiteTrafSolInfo.getReferenciaAtem()!=null && !tramiteTrafSolInfo.getReferenciaAtem().equals("")){
					agrupaReferencias(tramiteTrafSolInfo);
				// Si son por matricula
				} else if (tramiteTrafSolInfo.getVehiculo().getMatricula()!=null && !tramiteTrafSolInfo.getVehiculo().getMatricula().isEmpty()){
					matriculas.add(tramiteTrafSolInfo);
				// Si son por bastidor
				} else if (tramiteTrafSolInfo.getVehiculo().getBastidor()!=null && !tramiteTrafSolInfo.getVehiculo().getBastidor().isEmpty()){
					bastidores.add(tramiteTrafSolInfo);
				}
			}
		}
	}

	private void procesaReferencias(Map<String, List<TramiteTrafSolInfo>> mapaDeReferencias, ModeloAtem modelo) throws RemoteException{
		if(!mapaDeReferencias.isEmpty()){
			for (String referenciaAtem: mapaDeReferencias.keySet()){
				try{
					RespuestaAtemBean resultBeanReferencia = modelo.generaPeticionReferencias(mapaDeReferencias.get(referenciaAtem));

					// Se guarda copia del resultado obtenido en la consulta WS de ATEM
					if (guardarXmlActivo) {
						modelo.guardarXmlAtemSolInfo(new BigDecimal(mapaDeReferencias.get(referenciaAtem).get(0).getTramiteTrafico().getNumExpediente()), resultBeanReferencia);
					}

					//Si solo tiene un elemento es que ha habido un error general para todas las referencias. Ej.: no existe la referencia informada.
					if (resultBeanReferencia.getResultadosUnitarios().size() == 1){
						modelo.estableceSolicitudesConError(mapaDeReferencias.get(referenciaAtem),
								resultBeanReferencia.getResultadosUnitarios().get(0L).getMensaje());
					}else{
						//Obtengo todos los idVehiculo de esta referencia. Itero por cada idVehiculo para coger del mapa la respuesta (bien error, bien correcto).
						List<TramiteTrafSolInfo> listaTramiteTrafSolInfoReferencia = mapaDeReferencias.get(referenciaAtem);
						boolean algunoBien = compruebaAlgunoBien(resultBeanReferencia.getResultadosUnitarios().values());
						for (TramiteTrafSolInfo tramiteTrafSolInfoReferencia : listaTramiteTrafSolInfoReferencia){
							ResultBean resultadoUnitario = resultBeanReferencia.getResultadosUnitarios().get(tramiteTrafSolInfoReferencia.getVehiculo().getId().getIdVehiculo());
							if (resultadoUnitario.getError()){
								if (algunoBien) {
									tramiteTrafSolInfoReferencia.setReferenciaAtem(null);
									tramiteTrafSolInfoReferencia.setResultado(resultadoUnitario.getMensaje());
								} else {
									tramiteTrafSolInfoReferencia.setResultado("Referencia " + tramiteTrafSolInfoReferencia.getReferenciaAtem() + " - Error " + resultadoUnitario.getMensaje());
									if (tramiteTrafSolInfoReferencia.getResultado().length()>MAX_RESULT_LENGTH) {
										tramiteTrafSolInfoReferencia.setResultado(tramiteTrafSolInfoReferencia.getResultado().substring(0, MAX_RESULT_LENGTH-1));
									}
								}
								modelo.estableceSolicitudConError(tramiteTrafSolInfoReferencia);
							}else{
								tramiteTrafSolInfoReferencia.setResultado(Constantes.SOLICITUD_OK);
								modelo.estableceSolicitudRecibido(tramiteTrafSolInfoReferencia);
								// Llamar al metodo que se encarga de hacer el pdf.
								byte[] fichero;
								try {
									fichero = generarInformeATEM(tramiteTrafSolInfoReferencia, resultadoUnitario.getMensaje(), NOMBRE_NODO, Constantes.VALOR_TRAMITE_TRAFICO_PDF);
									mapaBytesApdf.put(tramiteTrafSolInfoReferencia.getTramiteTrafico().getNumExpediente().toString() + "_" + tramiteTrafSolInfoReferencia.getVehiculo().getMatricula() 
											+ "."+ Constantes.VALOR_TRAMITE_TRAFICO_PDF, fichero);
								} catch (JRException e) {
									log.error("Se ha producido un error al realizar petición procesar referencias: " , e);
									tramiteTrafSolInfoReferencia.setResultado(e.getMessage());
									modelo.estableceSolicitudConError(tramiteTrafSolInfoReferencia);
								}
								
							}
						}
					}
				}catch(TransformerException e){
					log.error(ERROR_REFERENCIA + referenciaAtem, e);
					modelo.estableceSolicitudesDeReferenciaConError(mapaDeReferencias.get(referenciaAtem));
				}catch(OegamExcepcion e){
					log.error(ERROR_REFERENCIA + referenciaAtem, e);
					modelo.estableceSolicitudesDeReferenciaConError(mapaDeReferencias.get(referenciaAtem));
				}catch(RemoteException e){
					log.error(ERROR_COMUNICACION_DGT + referenciaAtem, e);
					throw e;
				}catch(IOException e){
					log.error(ERROR_COMUNICACION_DGT + referenciaAtem, e);
					modelo.estableceSolicitudesDeReferenciaConError(mapaDeReferencias.get(referenciaAtem));
				}catch(SAXException e){
					log.error(ERROR_REFERENCIA + referenciaAtem 
							+ ERROR_RESPUESTA_NO_PROCESADA, e);
					modelo.estableceSolicitudesDeReferenciaConError(mapaDeReferencias.get(referenciaAtem));
				}catch(ParserConfigurationException e){
					log.error(ERROR_REFERENCIA + referenciaAtem 
							+ ERROR_RESPUESTA_NO_PROCESADA, e);
					modelo.estableceSolicitudesDeReferenciaConError(mapaDeReferencias.get(referenciaAtem));
				}catch(Exception e){
					log.error(ERROR_REFERENCIA + referenciaAtem 
							+ ERROR_RESPUESTA_NO_PROCESADA, e);
					modelo.estableceSolicitudesDeReferenciaConError(mapaDeReferencias.get(referenciaAtem));
				}
			}
		}
	}
	
	private void procesaMatriculasMasivas(ModeloAtem modelo, ColaBean colaBean, TramiteTrafico tramiteTrafico) throws RemoteException{
		if (!matriculas.isEmpty()){
			try{
				RespuestaAtemBean resultBeanMasivo = modelo.invocaPeticionMatriculaMasiva(matriculas);

				// Se guarda copia del resultado obtenido en la consulta WS de ATEM
				if (guardarXmlActivo) {
					modelo.guardarXmlAtemSolInfo(new BigDecimal(tramiteTrafico.getNumExpediente()), resultBeanMasivo);
				}

				// se guarda la referencia.
				if (resultBeanMasivo.getReferencia()){
					modelo.guardaReferencia(matriculas, resultBeanMasivo.getReferenciaAtem());
					modelo.cambiarEstadoTramite(colaBean.getIdUsuario(), new BigDecimal(tramiteTrafico.getNumExpediente()), EstadoTramiteTrafico.Recibida_Referencia_Atem);
				}
				//Si solo tiene un elemento es que ha habido un error general para todas las matriculas.
				else if (resultBeanMasivo.getResultadosUnitarios().size() == 1){
					modelo.estableceSolicitudesConError(matriculas, resultBeanMasivo.getResultadosUnitarios().get(0L).getMensaje());
				}else{
					//Si entra aquí es que se procesaron las matriculas. El if es porque algunas pueden tener errores y otras pueden haber salido bien.
					for (TramiteTrafSolInfo tramiteTrafSolInfo: matriculas){
						ResultBean resultadoUnitario = resultBeanMasivo.getResultadosUnitarios().get(tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo());
						if (resultadoUnitario.getError()){
							tramiteTrafSolInfo.setResultado(resultadoUnitario.getMensaje());
							modelo.estableceSolicitudConError(tramiteTrafSolInfo);
						}else{
							// Llamar al metodo que se encarga de hacer el pdf.
							tramiteTrafSolInfo.setResultado(Constantes.SOLICITUD_OK);
							modelo.estableceSolicitudRecibido(tramiteTrafSolInfo);
							try{
								byte[] fichero = generarInformeATEM(tramiteTrafSolInfo, resultadoUnitario.getMensaje(), NOMBRE_NODO, Constantes.VALOR_TRAMITE_TRAFICO_PDF);
								mapaBytesApdf.put(tramiteTrafico.getNumExpediente().toString() + "_" + tramiteTrafSolInfo.getVehiculo().getMatricula() 
										+ "."+ Constantes.VALOR_TRAMITE_TRAFICO_PDF, fichero);
								modelo.descontarCreditos(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario());
								modelo.anotarGasto(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario(), tramiteTrafico);
							} catch (JRException e) {
								log.error(ERROR_PETICION_MASIVA_MATRICULA , e);
								tramiteTrafSolInfo.setResultado(e.getMessage());
								modelo.estableceSolicitudConError(tramiteTrafSolInfo);		
							}
						}
					}
				}

			}catch(TransformerException e){
				log.error(ERROR_PETICION_MASIVA_MATRICULA , e);
				modelo.estableceSolicitudesConError(matriculas, e.getMessage());
			}catch(OegamExcepcion e){
				log.error(ERROR_PETICION_MASIVA_MATRICULA , e);
				modelo.estableceSolicitudesConError(matriculas, e.getMessage());
			}catch(RemoteException e){
				log.error(ERROR_PETICION_MASIVA_MATRICULA , e);
				throw e;
			}catch(IOException e){
				log.error(ERROR_PETICION_MASIVA_MATRICULA , e);
				modelo.estableceSolicitudesConError(matriculas, e.getMessage());
			}catch(SAXException e){
				log.error(ERROR_PETICION_MASIVA_MATRICULA  
						+ ERROR_RESPUESTA_NO_PROCESADA, e);
				modelo.estableceSolicitudesConError(matriculas, e.getMessage());
			}catch(ParserConfigurationException e){
				log.error(ERROR_PETICION_MASIVA_MATRICULA  
						+ ERROR_RESPUESTA_NO_PROCESADA, e);
				modelo.estableceSolicitudesConError(matriculas, e.getMessage());
			}catch(Exception e){
				log.error(ERROR_PETICION_MASIVA_MATRICULA , e);
				modelo.estableceSolicitudesConError(matriculas, e.getMessage());
			}
		}
	}
	
	private void procesaBastidoresMasivos(ModeloAtem modelo, ColaBean colaBean, TramiteTrafico tramiteTrafico) throws RemoteException{
		if(!bastidores.isEmpty()){
			try{
				RespuestaAtemBean resultBeanMasivo = modelo.invocaPeticionBastidorMasivo(bastidores);

				// Se guarda copia del resultado obtenido en la consulta WS de ATEM
				if (guardarXmlActivo) {
					modelo.guardarXmlAtemSolInfo(new BigDecimal(tramiteTrafico.getNumExpediente()), resultBeanMasivo);
				}
				
				//En caso de haber recibido la referencia se guarda la referencia.
				if (resultBeanMasivo.getReferencia()){
					modelo.guardaReferencia(bastidores, resultBeanMasivo.getReferenciaAtem());
					modelo.cambiarEstadoTramite(colaBean.getIdUsuario(), new BigDecimal(tramiteTrafico.getNumExpediente()), EstadoTramiteTrafico.Recibida_Referencia_Atem);
				}
				//Si solo tiene un elemento es que ha habido un error general para todas las matriculas.
				else if (resultBeanMasivo.getResultadosUnitarios().size() == 1){
					modelo.estableceSolicitudesConError(bastidores, resultBeanMasivo.getResultadosUnitarios().get(0L).getMensaje());
				}else{
					//Si entra aquí es que se procesaron las matriculas. El if es porque algunas pueden tener errores y otras pueden haber salido bien.
					for (TramiteTrafSolInfo tramiteTrafSolInfo: bastidores){
						ResultBean resultadoUnitario = resultBeanMasivo.getResultadosUnitarios().get(tramiteTrafSolInfo.getVehiculo().getId().getIdVehiculo());
						if (resultadoUnitario.getError()){
							tramiteTrafSolInfo.setResultado(resultadoUnitario.getMensaje());
							modelo.estableceSolicitudConError(tramiteTrafSolInfo);
						}else{
							// Llamar al metodo que se encarga de hacer el pdf.
							tramiteTrafSolInfo.setResultado(Constantes.SOLICITUD_OK);
							modelo.estableceSolicitudRecibido(tramiteTrafSolInfo);
							try {
								byte[] fichero = generarInformeATEM(tramiteTrafSolInfo, resultadoUnitario.getMensaje(), NOMBRE_NODO, Constantes.VALOR_TRAMITE_TRAFICO_PDF);
								mapaBytesApdf.put(tramiteTrafico.getNumExpediente().toString() + "_" + tramiteTrafSolInfo.getVehiculo().getBastidor() 
										+ "."+ Constantes.VALOR_TRAMITE_TRAFICO_PDF, fichero);
								modelo.descontarCreditos(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario());
								modelo.anotarGasto(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario(), tramiteTrafico);
							} catch (JRException e) {
								log.error(ERROR_PETICION_MASIVA_BASTIDOR , e);
								tramiteTrafSolInfo.setResultado(e.getMessage());
								modelo.estableceSolicitudConError(tramiteTrafSolInfo);
							}
						}
					}
				}	
			}catch(TransformerException e){
				log.error(ERROR_PETICION_MASIVA_BASTIDOR , e);
				modelo.estableceSolicitudesConError(bastidores, e.getMessage());	
			}catch(OegamExcepcion e){
				log.error(ERROR_PETICION_MASIVA_BASTIDOR , e);
				modelo.estableceSolicitudesConError(bastidores, e.getMessage());
			}catch(RemoteException e){
				log.error(ERROR_PETICION_MASIVA_BASTIDOR , e);
				throw e;
			}catch(IOException e){
				log.error(ERROR_PETICION_MASIVA_BASTIDOR , e);
				modelo.estableceSolicitudesConError(bastidores, e.getMessage());
			}catch(SAXException e){
				log.error(ERROR_PETICION_MASIVA_BASTIDOR  
						+ ERROR_RESPUESTA_NO_PROCESADA, e);
				modelo.estableceSolicitudesConError(bastidores, e.getMessage());
			}catch(ParserConfigurationException e){
				log.error(ERROR_PETICION_MASIVA_BASTIDOR  
						+ ERROR_RESPUESTA_NO_PROCESADA, e);
				modelo.estableceSolicitudesConError(bastidores, e.getMessage());
			}catch(Exception e){
				log.error(ERROR_PETICION_MASIVA_BASTIDOR , e);
				modelo.estableceSolicitudesConError(bastidores, e.getMessage());	
			}
		}
	}
	
	private void procesaMatriculaUnitaria(ModeloAtem modelo, ColaBean colaBean, TramiteTrafico tramiteTrafico) throws RemoteException{
		for(TramiteTrafSolInfo matriculaUnitaria: matriculas) {
			if (matriculaUnitaria.getTramiteTrafico()!=null){
				try{
					RespuestaAtemBean resultbeanMatUni = modelo.invocaPeticionMatriculaUnitaria(matriculaUnitaria);

					// Se guarda copia del resultado obtenido en la consulta WS de ATEM
					if (guardarXmlActivo) {
						modelo.guardarXmlAtemSolInfo(new BigDecimal(tramiteTrafico.getNumExpediente()), resultbeanMatUni);
					}

					if (resultbeanMatUni.getError()){
						matriculaUnitaria.setResultado((resultbeanMatUni.getResultadosUnitarios().get(matriculaUnitaria.getId().getIdVehiculo())).getMensaje());
						modelo.estableceSolicitudConError(matriculaUnitaria);
					}else{
						matriculaUnitaria.setResultado(Constantes.SOLICITUD_OK);
						modelo.estableceSolicitudRecibido(matriculaUnitaria);
						byte[] fichero = generarInformeATEM(matriculaUnitaria, resultbeanMatUni.getRespuestaCompleta(), NOMBRE_NODO, Constantes.VALOR_TRAMITE_TRAFICO_PDF);
						mapaBytesApdf.put(tramiteTrafico.getNumExpediente().toString() + "_" + matriculaUnitaria.getVehiculo().getMatricula() 
								+ "."+ Constantes.VALOR_TRAMITE_TRAFICO_PDF, fichero);
						modelo.descontarCreditos(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario());
						modelo.anotarGasto(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario(), tramiteTrafico);
					}
				} catch (JRException e) {
					log.error(ERROR_DETALLE_MATRICULA , e);
					matriculaUnitaria.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(matriculaUnitaria);
				}catch(TransformerException e){
					log.error(ERROR_DETALLE_MATRICULA , e);
					matriculaUnitaria.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(matriculaUnitaria);	
				}catch(OegamExcepcion e){
					log.error(ERROR_DETALLE_MATRICULA , e);
					matriculaUnitaria.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(matriculaUnitaria);
				}catch(RemoteException e){
					log.error(ERROR_DETALLE_MATRICULA , e);
					throw e;
				}catch(IOException e){
					log.error(ERROR_DETALLE_MATRICULA , e);
					matriculaUnitaria.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(matriculaUnitaria);
				}catch(SAXException e){
					log.error(ERROR_DETALLE_MATRICULA  
							+ ERROR_RESPUESTA_NO_PROCESADA, e);
					matriculaUnitaria.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(matriculaUnitaria);
				}catch(ParserConfigurationException e){
					log.error(ERROR_DETALLE_MATRICULA  
							+ ERROR_RESPUESTA_NO_PROCESADA, e);
					matriculaUnitaria.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(matriculaUnitaria);
				}
			}
		}
	}

	private void procesaBastidorUnitario(ModeloAtem modelo, ColaBean colaBean, TramiteTrafico tramiteTrafico) throws RemoteException{
		for (TramiteTrafSolInfo bastidorUnitario: bastidores) {
			if (bastidorUnitario.getTramiteTrafico()!=null){
				// Mapa para construir el zip (contendrá los informes del expediente)
				try{
					RespuestaAtemBean resultbeanBastidorUni = modelo.invocaPeticionBastidorUnitario(bastidorUnitario);

					// Se guarda copia del resultado obtenido en la consulta WS de ATEM
					if (guardarXmlActivo) {
						modelo.guardarXmlAtemSolInfo(new BigDecimal(tramiteTrafico.getNumExpediente()), resultbeanBastidorUni);
					}

					if (resultbeanBastidorUni.getError()){
						bastidorUnitario.setResultado((resultbeanBastidorUni.getResultadosUnitarios().get(bastidorUnitario.getId().getIdVehiculo())).getMensaje());
						modelo.estableceSolicitudConError(bastidorUnitario);
					}else{
						bastidorUnitario.setResultado(Constantes.SOLICITUD_OK);
						modelo.estableceSolicitudRecibido(bastidorUnitario);
						byte[] fichero = generarInformeATEM(bastidorUnitario, resultbeanBastidorUni.getRespuestaCompleta(), NOMBRE_NODO, Constantes.VALOR_TRAMITE_TRAFICO_PDF);
						mapaBytesApdf.put(tramiteTrafico.getNumExpediente().toString() + "_" + bastidorUnitario.getVehiculo().getBastidor() 
								+ "."+ Constantes.VALOR_TRAMITE_TRAFICO_PDF, fichero);
						modelo.descontarCreditos(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario());
						modelo.anotarGasto(tramiteTrafico.getContrato().getIdContrato().toString(), colaBean.getIdUsuario(), tramiteTrafico);
					}
				} catch (JRException e) {
					log.error(ERROR_DETALLE_BASTIDOR , e);
					bastidorUnitario.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(bastidorUnitario);
				}catch(TransformerException e){
					log.error(ERROR_DETALLE_BASTIDOR , e);
					bastidorUnitario.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(bastidorUnitario);
				}catch(OegamExcepcion e){
					log.error(ERROR_DETALLE_BASTIDOR , e);
					bastidorUnitario.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(bastidorUnitario);
				}catch(RemoteException e){
					log.error(ERROR_DETALLE_BASTIDOR , e);
					throw e;
				}catch(IOException e){
					log.error(ERROR_DETALLE_BASTIDOR , e);
					bastidorUnitario.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(bastidorUnitario);
				}catch(SAXException e){
					log.error(ERROR_DETALLE_BASTIDOR  
							+ ERROR_RESPUESTA_NO_PROCESADA, e);
					bastidorUnitario.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(bastidorUnitario);
				}catch(ParserConfigurationException e){
					log.error(ERROR_DETALLE_BASTIDOR  
							+ ERROR_RESPUESTA_NO_PROCESADA, e);
					bastidorUnitario.setResultado(e.getMessage());
					modelo.estableceSolicitudConError(bastidorUnitario);
				}
			}
		}
	}

	
	private boolean compruebaAlgunoBien(Collection<ResultBean> resultBeans) {
		Iterator<ResultBean> it = resultBeans.iterator();
		while (it.hasNext()) {
			Boolean error = it.next().getError();
			if (error != null && !error) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void cambioNumeroInstancias(int numero) {
		log.info("Soy el proceso Atem ejecutando el cambio de Número de instancias");
	}

	private void finalizacionCorrectaAtem(JobExecutionContext jobExecutionContext, ColaBean solicitud, String respuesta) throws BorrarSolicitudExcepcion  {
		log.info("Ejecucion correcta del proceso ATEM"); 
		peticionCorrecta(jobExecutionContext); 	
		getModeloSolicitud().borrarSolicitud(solicitud.getIdEnvio(),respuesta);
		ejecucionesProcesosDAO.actualizarUltimaEjecucion(solicitud,ConstantesProcesos.EJECUCION_CORRECTA ,respuesta,ConstantesProcesos.PROCESO_ATEM_ANUNTIS);
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


}