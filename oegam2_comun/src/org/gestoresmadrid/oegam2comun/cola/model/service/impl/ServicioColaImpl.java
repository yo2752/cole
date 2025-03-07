package org.gestoresmadrid.oegam2comun.cola.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.cola.enumerados.EstadoCola;
import org.gestoresmadrid.core.cola.model.dao.ColaDao;
import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.hibernate.HibernateException;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service(value = "servicioCola")
public class ServicioColaImpl implements ServicioCola {

	private static final long serialVersionUID = 458327394317722599L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioColaImpl.class);
	
	private static final String PROPIEDAD_NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	private static final String PROPIEDAD_NOMBRE_HOST_SOLICITUD_2 = "nombreHostSolicitudProcesos2";

	@Autowired
	private ColaDao colaDao;

	@Autowired
	private ServicioProcesos servicioProcesos;

	@Autowired
	private ServicioAccionTramite servicioAccionTramite;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioNotificacion servicioNotificacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	private BigDecimal estadoIniciado = new BigDecimal(1);

	@Override
	@Transactional(readOnly = true)
	public Boolean comprobarColasProcesoActivas(String proceso, BigDecimal idContrato, String xmlEnviar) {
		try {
			List<ColaVO> listaColasActivas = colaDao.getColasActivasProceso(proceso, idContrato, xmlEnviar);
			if (listaColasActivas == null || listaColasActivas.isEmpty()) {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar si existen colas activas, error: ", e);

		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional
	public List<ColaVO> getSolicitudesActivasProceso(String proceso, String nodo) {
		try {
			return colaDao.getColasActivasProceso(proceso, nodo);
		} catch (Exception e) {
			log.error("Error al obtener el numero de solicitudes activas", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ColaVO getCola(Long idEnvio) {
		try {
			return colaDao.getCola(idEnvio);
		} catch (Exception e) {
			log.error("Error el obtener la cola", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ColaVO getColaIdTramite(BigDecimal idTramite, String proceso) {
		try {
			return colaDao.getColaPorIdTramite(idTramite, proceso);
		} catch (Exception e) {
			log.error("Error el obtener la cola");
		}
		return null;
	}

	@Override
	@Transactional
	public ColaVO getColaPrincipal(String proceso, String cola, String nodo) {
		try {
			return colaDao.getColaPrincipal(proceso, cola, nodo);
		} catch (Exception e) {
			log.error("Error el obtener la cola principal", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void solicitudCorrecta(Long idEnvio, String resultado, String proceso, String nodo) {
		ColaVO cola = colaDao.getCola(idEnvio);
		colaDao.borrar(cola);

		String entornoProceso = gestorPropiedades.valorPropertie("entornoProcesos");
		ProcesoVO procesoVO = null;
		if ("DESARROLLO".equals(entornoProceso)) {
			procesoVO = servicioProcesos.getProcesoPorProcesoYNodo(proceso, nodo);
		} else {
			procesoVO = servicioProcesos.getProceso(proceso);
		}

		if (procesoVO != null && "1".equals(procesoVO.getAccionNotificacion())) {
			servicioAccionTramite.cerrarAccionTramite(cola.getIdUsuario(), cola.getIdTramite(), procesoVO.getAccion(), resultado);
			crearNotificacion(cola, procesoVO.getAccion());
		}
	}

	private void crearNotificacion(ColaVO cola, String accion) {
		NotificacionDto dto = new NotificacionDto();

		dto.setIdUsuario(cola.getIdUsuario().longValue());
		dto.setTipoTramite(cola.getTipoTramite());
		dto.setIdTramite(cola.getIdTramite());
		dto.setDescripcion("PROCESO " + accion + " FINALIZADO");
		dto.setEstadoAnt(BigDecimal.ZERO);
		dto.setEstadoNue(BigDecimal.ZERO);

		servicioNotificacion.crearNotificacionConFechaNotificacion(dto);
	}

	@Override
	@Transactional
	public void solicitudErronea(Long idEnvio) {
		ColaVO cola = colaDao.getCola(idEnvio);
		cola.setFechaHora(new Date());
		cola.setEstado(new BigDecimal(EstadoCola.PENDIENTES_ENVIO.getValorEnum()));
		colaDao.actualizar(cola);
	}

	@Override
	@Transactional
	public void actualizar(ColaVO cola) {
		colaDao.actualizar(cola);
	}

	@Override
	@Transactional
	public void borrar(ColaVO cola) {
		colaDao.borrar(cola);
	}

	@Override
	@Transactional
	public void errorServicio(Long idEnvio, String respuesta) {
		ColaVO cola = colaDao.getCola(idEnvio);
		cola.setRespuesta(respuesta);
		cola.setEstado(new BigDecimal(EstadoCola.ERROR_SERVICIO.getValorEnum()));
		colaDao.actualizar(cola);
	}

	@Override
	@Transactional
	public void solicitudErroneaImprimirTramites(BigDecimal idEnvio) {
		try {
			ColaVO cola = colaDao.getCola(idEnvio.longValue());
			cola.setFechaHora(new Date());
			cola.setEstado(estadoIniciado);
			colaDao.actualizar(cola);
		} catch (Exception e) {
			log.error("Error al actualizar la cola", e);
		}
	}

	@Override
	@Transactional
	public void eliminar(BigDecimal idTramite, String proceso) {
		try {
			ColaVO cola = getColaIdTramite(idTramite, proceso);
			if (cola != null) {
				colaDao.borrar(cola);
			}
		} catch (Exception e) {
			log.error("Error al eliminar de la cola", e);
		}
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultBean crearSolicitud(String proceso, String xmlEnviar, String nodo, String tipoTramite, String idTramite, BigDecimal idUsuario, String respuesta, BigDecimal idContrato)
			throws OegamExcepcion {

		Log.info("Dentro de ServicioColaImpl.java:crearSolicitud " + " proceso " + proceso + " nodo " + nodo + " tipoTramite " + tipoTramite + idUsuario + respuesta + idContrato);
		ResultBean resultBean = new ResultBean();
		String entornoProceso = gestorPropiedades.valorPropertie("entornoProcesos");
		ProcesoVO procesoVO = null;
		if ("DESARROLLO".equals(entornoProceso)) {
			procesoVO = servicioProcesos.getProcesoPorProcesoYNodo(proceso, nodo);
		} else {
			procesoVO = servicioProcesos.getProceso(proceso);
		}
		if (procesoVO != null && procesoVO.getId() != null) {
			ColaVO colaVO = nuevaCola(proceso, procesoVO.getId().getNodo(), tipoTramite, idTramite, idUsuario, xmlEnviar, respuesta, idContrato);
			log.info("Creacion nueva solicitud proceso " + proceso);
			if (idTramite != null) {
				List<ColaVO> listaCola = colaDao.getListaColaTramites(colaVO);
				if (listaCola != null && listaCola.isEmpty()) {
					colaVO = generarCola(colaVO);
					if ("1".equals(procesoVO.getAccionNotificacion())) {
						servicioAccionTramite.crearAccionTramite(colaVO.getIdUsuario(), colaVO.getIdTramite(), procesoVO.getAccion(), null, null);
					}
				} else {
					log.error("El trámite ya se encuentra en la cola, por lo que no se duplicara");
					throw new OegamExcepcion(EnumError.error_00002, "El trámite ya se encuentra en la cola para el proceso " + colaVO.getProceso() + " por lo que no se duplicara");
				}
			} else {
				colaVO = generarCola(colaVO);
			}
		} else {
			if (entornoProceso.equals("DESARROLLO")) {
				log.error("No existe datos para ese nodo '" + nodo + "' y ese proceso '" + proceso + "'");
			} else {
				log.error("No existe datos para ese proceso '" + proceso + "'");
			}
			throw new OegamExcepcion(EnumError.error_00001, "No existe datos para ese nodo y ese proceso");
		}
		return resultBean;
	}

	@Override
	@Transactional
	public ColaVO tomarSolicitud(String proceso, String cola, String nodo) {
		ColaVO colaVO = colaDao.getColaSolicitudProceso(proceso, cola, nodo);
		if (colaVO != null) {
			int nIntentos = colaVO.getNIntento().intValue();
			nIntentos = nIntentos + 1;
			colaVO.setNIntento(new BigDecimal(nIntentos));
			colaVO.setEstado(new BigDecimal(EstadoCola.EJECUTANDO.getValorEnum()));
			colaDao.actualizar(colaVO);
		} else {
			log.info("Error al recuperar la solicitud del proceso " + proceso + " en el hilo " + cola + ". No existe ninguna solicitud para la cola.");
		}
		return colaVO;
	}

	@Override
	@Transactional
	public boolean comprobarEstados(ColaVO colaVO, String proceso) {
		if (ConstantesProcesos.PROCESO_MATW.equals(proceso) || ConstantesProcesos.PROCESO_CHECKCTIT.equals(proceso) || ConstantesProcesos.PROCESO_FULLCTIT.equals(proceso)
				|| ConstantesProcesos.PROCESO_NOTIFICATIONCTIT.equals(proceso) || ConstantesProcesos.PROCESO_TRADECTIT.equals(proceso) || ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF.equals(
						proceso)) {
			TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(colaVO.getIdTramite(), false);
			if (tramite != null) {
				if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramite.getEstado().toString()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()
						.equals(tramite.getEstado().toString())) {
					log.info("Error al recuperar la solicitud del proceso " + proceso
							+ ". El trámite se encuentra en estado finalizado telematicamente o finalizado telematicamente impreso, no se va a reenviar la información.");
					colaDao.borrar(colaVO);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@Transactional
	public ColaVO nuevaCola(String proceso, String nodo, String tipoTramite, String idTramite, BigDecimal idUsuario, String xmlEnviar, String respuesta, BigDecimal idContrato) throws OegamExcepcion {
		ColaVO colaVO = new ColaVO();
		colaVO.setProceso(proceso);
		colaVO.setNodo(nodo);
		colaVO.setFechaHora(new Date());
		colaVO.setEstado(new BigDecimal(1));
		colaVO.setnIntento(new BigDecimal(0));
		colaVO.setTipoTramite(tipoTramite);
		if (idTramite != null) {
			colaVO.setIdTramite(new BigDecimal(idTramite));
		}
		if (idUsuario != null) {
			colaVO.setIdUsuario(idUsuario);
		}

		if (idContrato != null) {
			colaVO.setIdContrato(idContrato);
		}
		colaVO.setXmlEnviar(xmlEnviar);
		colaVO.setRespuesta(respuesta);

		colaVO.setCola(getHiloCola(colaVO));

		return colaVO;
	}

	@Transactional
	@Override
	public String getHiloCola(ColaVO colaVO) throws OegamExcepcion {
		// List<ColaVO> listaColaHilo = colaDao.getHiloNuevo(colaVO);

		// if(listaColaHilo == null){
		String hilo = colaDao.getHilo(colaVO);
		if (hilo == null) {
			throw new OegamExcepcion("Debe de crear la cola para el proceso " + colaVO.getProceso() + " para poder encolar una nueva solicitud");
			// }else{
			// ColaVO cola = listaColaHilo.get(0);
			// return cola.getCola();
		}
		return hilo;
	}

	@Override
	@Transactional
	public ColaVO generarCola(ColaVO colaVO) {
		try {
			colaVO.setIdEnvio((Long) colaDao.guardar(colaVO));
		} catch (HibernateException e) {
			log.error("Error al encolar", e);
			throw new TransactionalException(e);
		}
		return colaVO;
	}

	@Override
	@Transactional
	public boolean establecerMaxPrioridad(Long idEnvio) {
		boolean result = false;
		if (idEnvio != null) {
			String[] namedParemeters = { "idEnvio" };
			Long[] namedValues = { idEnvio };
			int updates = colaDao.executeNamedQuery(ColaVO.ESTABLECER_MAX_PRIORIDAD, namedParemeters, namedValues);
			if (updates == 1) {
				result = true;
			} else if (updates > 0) {
				Log.error("Se deberia haber actualizado una unica columna y se han actualizado " + updates + ". Se realiza rollback");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> solicitudesAgrupadasPorHilos(String proceso) {
		return colaDao.solicitudesAgrupadasPorHilos(proceso);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> solicitudesAgrupadasPorHilos() {
		return colaDao.solicitudesAgrupadasPorHilos();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer obtenerMaxCola() {
		String maxCola = colaDao.obtenerMaxCola();
		if (StringUtils.isNotBlank(maxCola)) {
			Integer intNumColas = Integer.parseInt(maxCola);
			return intNumColas;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ColaVO> getSolicitudesColaMonitorizacion(String proceso, String estado, FechaFraccionada fecha) {

		String hostPeticiones1 = gestorPropiedades.valorPropertie(PROPIEDAD_NOMBRE_HOST_SOLICITUD);
		String hostPeticiones2 = gestorPropiedades.valorPropertie(PROPIEDAD_NOMBRE_HOST_SOLICITUD_2);

		
		return colaDao.getSolicitudesColaMonitorizacion(proceso, estado, fecha, hostPeticiones1, hostPeticiones2);
	}
}
