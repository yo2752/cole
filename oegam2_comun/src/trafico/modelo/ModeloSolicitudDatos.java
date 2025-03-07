package trafico.modelo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.ResultBean;
import general.beans.RespuestaGenerica;
import general.modelo.ModeloBasePQ;
import general.utiles.OraUtiles;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ValoresSchemas;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.daos.BeanPQGeneral;
import trafico.beans.daos.BeanPQSolicitudDatosGuardar;
import trafico.beans.daos.SolicitudVehiculoCursor;
import trafico.beans.daos.pq_tramite_trafico.BeanPQDETALLE_SOLICITUD;
import trafico.beans.daos.pq_tramite_trafico.BeanPQELIMINAR_SOL_VEHICULO;
import trafico.beans.daos.pq_tramite_trafico.BeanPQGUARDAR_SOL_VEHICULO;
import trafico.beans.daos.pq_tramite_trafico.BeanPQREINICIAR_SOLICITUD;
import trafico.beans.utiles.IntervinienteTraficoBeanPQConversion;
import trafico.beans.utiles.SolicitudDatosBeanPQConversion;
import trafico.utiles.constantes.ConstantesDGT;
import utilidades.constantes.ValoresCatalog;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloSolicitudDatos  extends ModeloBasePQ{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloSolicitudDatos.class);

	private ModeloInterviniente modeloInterviniente;

	@Autowired
	SolicitudDatosBeanPQConversion solicitudDatosBeanPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	ValoresSchemas valoresSchemas;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloSolicitudDatos() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public HashMap<String,Object> guardarSolicitudDatos(SolicitudDatosBean solicitudDatosBean) {
		String numColegiado = "";
		if (!"".equalsIgnoreCase(solicitudDatosBean.getTramiteTrafico().getNumColegiado()) && solicitudDatosBean.getTramiteTrafico().getNumColegiado() != null)
			numColegiado = solicitudDatosBean.getTramiteTrafico().getNumColegiado();
		else
			numColegiado = utilesColegiado.getNumColegiadoSession();
		return guardarSolicitudDatos(solicitudDatosBean, utilesColegiado.getIdUsuarioSessionBigDecimal(),
				utilesColegiado.getIdContratoSessionBigDecimal(), numColegiado);
	}

	// Sobrescrito que recibe el número de colegiado en un parámetro
	public HashMap<String,Object> guardarSolicitudDatos(SolicitudDatosBean solicitudDatosBean, BigDecimal idUsuario,
			BigDecimal idContrato, String numColegiado) {
		HashMap<String,Object> resultadoGuardar = new HashMap<>();
		ResultBean resultBean = new ResultBean();
		resultBean.setError(false);
		String mensaje = "";
		SolicitudDatosBean beanPantallaSolicitud = new SolicitudDatosBean();

		//Si hay un trámite, se convierte y se guarda. Se necesita el número de Colegiado.
		//solicitudDatosBean.getTramiteTrafico().getVehiculo().setIdVehiculo(vehiculoAux.getIdVehiculo());
		BeanPQSolicitudDatosGuardar solicitudDatosPQ = solicitudDatosBeanPQConversion.convertirBeanToPQ(solicitudDatosBean.getTramiteTrafico(),
				idUsuario, idContrato, numColegiado);
		solicitudDatosPQ.setP_NUM_COLEGIADO(numColegiado);
		RespuestaGenerica resultadoSolicitud = ejecutarProc(solicitudDatosPQ,
				valoresSchemas.getSchema(),
				ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_SOLICITUD",
				BeanPQGeneral.class);
		beanPantallaSolicitud  = solicitudDatosBeanPQConversion.convertirPQToBean(resultadoSolicitud);
		//beanPantallaSolicitud.getTramiteTrafico().setVehiculo(vehiculoAux);

		BigDecimal pCodeSolicitud = (BigDecimal)resultadoSolicitud.getParametro(ConstantesPQ.P_CODE);
		log.debug(ConstantesPQ.LOG_P_CODE + " al guardar la solicitud: " + pCodeSolicitud);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoSolicitud.getParametro(ConstantesPQ.P_SQLERRM));
		log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoSolicitud.getParametro(ConstantesPQ.P_INFORMACION));
		BigDecimal numExpediente = (BigDecimal)resultadoSolicitud.getParametro("P_NUM_EXPEDIENTE");
		solicitudDatosBean.getTramiteTrafico().setNumExpediente(numExpediente);

		//Si ha ido bien la operación de guardar solicitud (P_CODE es 0 si todo fue bien).
		if (ConstantesPQ.pCodeOk.equals(pCodeSolicitud)) { //ConstantesPQ.pCodeOk
			log.debug("          LA SOLICITUD SE HA GUARDADO CORRECTAMENTE");

			if (solicitudDatosBean.getSolicitante() != null
					&& solicitudDatosBean.getSolicitante().getPersona() != null
					&& solicitudDatosBean.getSolicitante().getPersona().getNif() != null
					&& !"".equals(solicitudDatosBean.getSolicitante().getPersona().getNif())) {

				solicitudDatosBean.getSolicitante().setNumColegiado(numColegiado);
				solicitudDatosBean.getSolicitante().setTipoInterviniente(ConstantesDGT.TIPO_INTERVINIENTE_SOLICITANTE);
				solicitudDatosBean.getSolicitante().setNumExpediente(numExpediente);

				RespuestaGenerica resultadoSolicitante = ejecutarProc(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(solicitudDatosBean.getSolicitante(),
						idUsuario, idContrato, numColegiado),
						valoresSchemas.getSchema(),
						ValoresCatalog.PQ_TRAMITE_TRAFICO,"GUARDAR_INTERVINIENTE",
						BeanPQGeneral.class);
				beanPantallaSolicitud.setSolicitante(intervinienteTraficoBeanPQConversion.convertirPQToBean(resultadoSolicitante));
				BigDecimal pCodeSolicitante = (BigDecimal)resultadoSolicitante.getParametro(ConstantesPQ.P_CODE);
				log.debug(ConstantesPQ.LOG_P_CODE + " al guardar al interviniente: " + pCodeSolicitante);
				log.debug(ConstantesPQ.LOG_P_SQLERRM + (String)resultadoSolicitante.getParametro(ConstantesPQ.P_SQLERRM));
				log.debug(ConstantesPQ.LOG_P_INFORMACION + (String)resultadoSolicitante.getParametro(ConstantesPQ.P_INFORMACION));

				//Si ha ido bien la operación de guardar solicitud (P_CODE es 0 si todo fue bien).
				if (ConstantesPQ.pCodeOk.equals(pCodeSolicitante)) { //ConstantesPQ.pCodeOk
					log.debug("          EL SOLICITANTE SE HA GUARDADO CORRECTAMENTE");
				} else { //Si la operación de guardar fue erronea, se guarda el error en el resultado para devolverlo después.
					mensaje = ("- Error al guardar al solicitante. " +
							(String)resultadoSolicitante.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
					if (resultadoSolicitante.getParametro(ConstantesPQ.P_INFORMACION)!=null){
						mensaje += ((String)resultadoSolicitante.getParametro(ConstantesPQ.P_INFORMACION));
					}
					resultBean.setMensaje(mensaje);
					log.debug("          EL SOLICITANTE NO SE HA GUARDADO CORRECTAMENTE");
				}
			} else {
				log.debug("          NO HAY SOLICITANTE QUE GUARDAR");
			}

			// Si todo ha ido bien recorremos la lista de solicitudes y las guardamos
			BeanPQGUARDAR_SOL_VEHICULO pqGuardar = new BeanPQGUARDAR_SOL_VEHICULO();
			for (SolicitudVehiculoBean solicitudVehiculo : solicitudDatosBean.getSolicitudesVehiculos()) {

				if (solicitudVehiculo.getVehiculo().getMatricula() != null || solicitudVehiculo.getVehiculo().getBastidor() != null
						|| solicitudVehiculo.getVehiculo().getNive()!= null) {
					pqGuardar = new BeanPQGUARDAR_SOL_VEHICULO();
					pqGuardar = solicitudDatosBeanPQConversion.convertirSolicitudVehiculoToPQ(solicitudVehiculo,solicitudDatosBean.getTramiteTrafico().getNumExpediente(),
							solicitudDatosBean.getTramiteTrafico().getNumColegiado(), idUsuario, idContrato, numColegiado);
					pqGuardar.execute();
					if (ConstantesPQ.pCodeOk.equals(pqGuardar.getP_CODE())) {
						SolicitudVehiculoBean solicitudVehiculoAux = solicitudDatosBeanPQConversion.convertirSolicitudVehiculoPQToBean(pqGuardar);
						beanPantallaSolicitud.getSolicitudesVehiculos().add(solicitudVehiculoAux);
						log.debug("          LA SOLICITUD VEHICULO SE HA GUARDADO CORRECTAMENTE");
						resultBean.setMensaje("- Solicitud guardada correctamente");
					} else { //Si la operación de guardar fue erronea, se guarda el error en el resultado para devolverlo después.
						resultBean.setError(true);
						mensaje = ("- Error al guardar Solicitud Vehiculo. " + 
								OraUtiles.traducirError(pqGuardar.getP_SQLERRM()));
						if (pqGuardar.getP_INFORMACION() != null) {
							mensaje += (pqGuardar.getP_INFORMACION());
						}

						log.debug("          LA SOLICITUD VEHICULO NO SE HA GUARDADO CORRECTAMENTE");
						resultBean.setMensaje(mensaje);
					}
				} else {
					mensaje = ("- Error al guardar Solicitud Vehiculo. Falta Matrícula o Bastidor" +
							". ");
					resultBean.setMensaje(mensaje);
				}

			}
		} else { //Si la operación de guardar vehiculo fue erronea, se guarda el error en el resultado para devolverlo después.
			resultBean.setError(true);
			mensaje = ("- Error al guardar la solicitud. " +
					(String)resultadoSolicitud.getParametro(ConstantesPQ.P_SQLERRM) + ". ");
			if (resultadoSolicitud.getParametro(ConstantesPQ.P_INFORMACION)!=null){
				mensaje += ((String)resultadoSolicitud.getParametro(ConstantesPQ.P_INFORMACION));
			}
			resultBean.setMensaje(mensaje);
			log.debug("          LA SOLICITUD NO SE HA GUARDADO CORRECTAMENTE");
		}

		resultBean.addAttachment("numExpediente", beanPantallaSolicitud.getTramiteTrafico().getNumExpediente());
		resultadoGuardar.put(ConstantesPQ.RESULTBEAN, resultBean);
		resultadoGuardar.put(ConstantesPQ.BEANPANTALLA, beanPantallaSolicitud);
		return resultadoGuardar;
	}

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	public HashMap<String, Object> obtenerDetalle(BigDecimal numEXPEDIENTE, String numColegiado, BigDecimal idContrato) {
		return obtenerDetalle(numEXPEDIENTE, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), numColegiado);
	}

	public HashMap<String, Object> obtenerDetalle(BigDecimal numEXPEDIENTE, BigDecimal idUsuario, BigDecimal idContrato, String numColegiado) {
		HashMap<String, Object> detalle = new HashMap<>();
		SolicitudDatosBean solicitudBeanPantalla = new SolicitudDatosBean(true);
		ResultBean resultBean = new ResultBean();
		resultBean.setError(false);
		BeanPQDETALLE_SOLICITUD beanConsultaSolicitud = new BeanPQDETALLE_SOLICITUD();
		beanConsultaSolicitud.setP_NUM_EXPEDIENTE(numEXPEDIENTE);
		beanConsultaSolicitud.setP_ID_CONTRATO_SESSION(idContrato);
		beanConsultaSolicitud.setP_ID_USUARIO(idUsuario);
		List<Object> listaSolicitudVehiculos = beanConsultaSolicitud.execute(SolicitudVehiculoCursor.class);
		//Convertimos al bean de pantalla el resultado.
		solicitudBeanPantalla = solicitudDatosBeanPQConversion.convertirPQToBeanDetalle(beanConsultaSolicitud,listaSolicitudVehiculos);
		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = (BigDecimal)beanConsultaSolicitud.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanConsultaSolicitud.getP_SQLERRM());
		//Se controla el error y se actualiza para su devolución
		if (!ConstantesPQ.pCodeOk.equals(pCodeTramite)){
			resultBean.setError(true);
			resultBean.setMensaje(beanConsultaSolicitud.getP_SQLERRM());
		}
		//Interviniente
		List<IntervinienteTrafico> listaInterviniente = getModeloInterviniente().obtenerDetalleIntervinientes(numEXPEDIENTE,numColegiado,idContrato);
		for (IntervinienteTrafico intervinienteTrafico : listaInterviniente) {
			if(intervinienteTrafico.getTipoInterviniente().getValorEnum().equals(TipoInterviniente.Solicitante.getValorEnum())){
				solicitudBeanPantalla.setSolicitante(intervinienteTrafico);
			}
		}
		detalle.put(ConstantesPQ.BEANPANTALLA, solicitudBeanPantalla);
		detalle.put(ConstantesPQ.RESULTBEAN, resultBean);
		return detalle;
	}

	public HashMap<String, Object> borrarSolicitudDatos(SolicitudVehiculoBean solicitudBorrar) {
		HashMap<String, Object> resultado = new HashMap<>();

		ResultBean resultBean = new ResultBean();
		resultBean.setError(false);
		String mensaje = "";

		BeanPQELIMINAR_SOL_VEHICULO pqBorrar = new BeanPQELIMINAR_SOL_VEHICULO();
		pqBorrar = solicitudDatosBeanPQConversion.convertirSolicitudVehiculoBorrarToPQ(solicitudBorrar);
		pqBorrar.execute();

		if (!ConstantesPQ.pCodeOk.equals(pqBorrar.getP_CODE())) {
			resultBean.setError(true);
			mensaje = pqBorrar.getP_SQLERRM();
			if(mensaje.contains("ORA-01410")){
				mensaje = " No se puede borrar la solicitud en el estado actual del trámite. Guarde para volver a estado 'Iniciado'";
			}
			resultBean.setMensaje(mensaje);
		}

		resultado.put(ConstantesPQ.RESULTBEAN, resultBean);
		return resultado;
	}

	/**
	 * Reinicia todos los estados del trámite y de las avpo para que se puedan volver a consultar.
	 * @param numEXPEDIENTE
	 * @param idVehiculo
	 * @param numColegiado
	 * @return
	 */
	public HashMap<String, Object> reiniciarEstadosSolicitud(BigDecimal numEXPEDIENTE, BigDecimal idVehiculo, String numColegiado) {
		HashMap<String, Object> resultado = new HashMap<>();

		ResultBean resultBean = new ResultBean();
		resultBean.setError(false);
		resultBean.setMensaje("");
		String mensaje = "";
		BeanPQREINICIAR_SOLICITUD beanReiniciarSolicitud = new BeanPQREINICIAR_SOLICITUD();
		beanReiniciarSolicitud.setP_NUM_EXPEDIENTE(numEXPEDIENTE);
		beanReiniciarSolicitud.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanReiniciarSolicitud.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanReiniciarSolicitud.setP_ID_VEHICULO(idVehiculo);
		beanReiniciarSolicitud.setP_NUM_COLEGIADO(numColegiado);

		beanReiniciarSolicitud.execute();

		//Recuperamos información de respuesta
		BigDecimal pCode = beanReiniciarSolicitud.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCode);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanReiniciarSolicitud.getP_SQLERRM());
		//Se controla el error y se actualiza para su devolucion
		if (!ConstantesPQ.pCodeOk.equals(pCode)){
			resultBean.setError(true);
			mensaje += (" - Se ha producido un error al obtener el mensaje. " +
					beanReiniciarSolicitud.getP_SQLERRM() + ". ");
			resultBean.setMensaje(mensaje);
		}

		resultado.put(ConstantesPQ.RESULTBEAN, resultBean);
		return resultado;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyección por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloInterviniente getModeloInterviniente() {
		if (modeloInterviniente == null) {
			modeloInterviniente = new ModeloInterviniente();
		}
		return modeloInterviniente;
	}

	public void setModeloInterviniente(ModeloInterviniente modeloInterviniente) {
		this.modeloInterviniente = modeloInterviniente;
	}
}