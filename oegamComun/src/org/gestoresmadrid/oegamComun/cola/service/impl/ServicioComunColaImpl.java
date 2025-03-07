package org.gestoresmadrid.oegamComun.cola.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.cola.enumerados.EstadoCola;
import org.gestoresmadrid.core.cola.model.dao.ColaDao;
import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.cola.service.ServicioPersistenciaCola;
import org.gestoresmadrid.oegamComun.proceso.service.ServicioComunProcesos;
import org.gestoresmadrid.oegamComun.proceso.service.ServicioPersistenciaProcesos;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioComunColaImpl implements ServicioComunCola {

	private static final long serialVersionUID = 7126576259966573724L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunColaImpl.class);

	private static final String ENTORNO = "entornoProcesos";
	private static final String DESA = "DESARROLLO";
	private static final String HOST = "nombreHostProceso";

	@Autowired
	ColaDao colaDao;

	@Autowired
	ServicioComunProcesos servicioProcesos;

	@Autowired
	ServicioPersistenciaProcesos servicioPersistenciaProcesos;

	@Autowired
	ServicioPersistenciaCola servicioPersistenciaCola;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	@Transactional(readOnly = true)
	public int getNumColasProcesoPorContrato(String proceso, Long idContrato) {
		String entornoProceso = gestorPropiedades.valorPropertie(ENTORNO);
		if (DESA.equals(entornoProceso)) {
			return colaDao.getNumColasProceso(proceso, gestorPropiedades.valorPropertie(HOST), idContrato);
		} else {
			return colaDao.getNumColasProceso(proceso,null, idContrato);
		}
	}

	@Override
	@Transactional
	public ResultadoBean eliminarCola(BigDecimal idTramite, String proceso, String nodo) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ColaVO cola = colaDao.getColaPorIdTramite(idTramite, proceso);
			if (cola != null) {
				if (!EstadoCola.EJECUTANDO.getValorEnum().equals(cola.getEstado())) {
					colaDao.borrar(cola);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La cola no se ha podido eliminar al estar ejecutandose.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar la cola para el idTramite: " + idTramite + ", proceso: " + proceso + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar la cola.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public void borrarPeticion(Long idCola) {
		try {
			ColaVO colaVO = colaDao.getCola(idCola);
			if (colaVO != null && !EstadoCola.EJECUTANDO.getValorEnum().equals(colaVO.getEstado().toString())){
				colaDao.borrar(colaVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar la cola con id: " + idCola + ", error: ",e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existeColaProceso(String proceso, String nodo) {
		try {
			ProcesoVO procesoVO = null;
			if (DESA.equals(gestorPropiedades.valorPropertie(ENTORNO))) {
				procesoVO = servicioProcesos.getProcesoPorProcesoYNodo(proceso, nodo);
			} else {
				procesoVO = servicioProcesos.getProceso(proceso);
			}
			List<ColaVO> listaColasBBDD = colaDao.getColasCreadasPorProceso(proceso, procesoVO.getId().getNodo());
			if (listaColasBBDD != null && !listaColasBBDD.isEmpty()) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar las solictudes en cola para el proceso: " + proceso + ", error: ", e);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existeColaTramiteProceso(String proceso, BigDecimal idTramite, String nodo) {
		try {
			ProcesoVO procesoVO = null;
			if (DESA.equals(gestorPropiedades.valorPropertie(ENTORNO))) {
				procesoVO = servicioProcesos.getProcesoPorProcesoYNodo(proceso, nodo);
			} else {
				procesoVO = servicioProcesos.getProceso(proceso);
			}
			List<ColaVO> listaColasBBDD = colaDao.getColasCreadasPorProcesoIdTramite(proceso, procesoVO.getId().getNodo(), idTramite);
			if (listaColasBBDD != null && !listaColasBBDD.isEmpty()) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar las solictudes en cola para el proceso: " + proceso + " y el idTramite: "
					+ idTramite + ", error: ", e);
		}
		return Boolean.TRUE;
	}

	@Override
	@Transactional
	public ResultadoBean crearSolicitud(Long idTramite, String proceso, String nodo, String tipoTramite, BigDecimal idUsuario, BigDecimal idContrato, String xmlEnviar) throws OegamExcepcion {
		ResultadoBean resultBean = new ResultadoBean(Boolean.FALSE);
		String entornoProceso = gestorPropiedades.valorPropertie(ENTORNO);
		ProcesoVO procesoVO = null;
		if (DESA.equals(entornoProceso)) {
			procesoVO = servicioProcesos.getProcesoPorProcesoYNodo(proceso, nodo);
		} else {
			procesoVO = servicioProcesos.getProceso(proceso);
		}
		if (procesoVO != null && procesoVO.getId() != null) {
			resultBean.setIdCola(crearCola(idTramite, proceso, procesoVO.getId().getNodo(), tipoTramite, idUsuario, idContrato, xmlEnviar));
		} else {
			if (entornoProceso.equals(DESA)) {
				log.error("No existe datos para ese nodo '" + nodo + "' y ese proceso '" + proceso + "'");
			} else {
				log.error("No existe datos para ese proceso '" + proceso + "'");
			}
			throw new OegamExcepcion(EnumError.error_00001, "No existe datos para ese nodo y ese proceso");
		}
		return resultBean;
	}

	private Long crearCola(Long idTramite, String proceso, String nodo, String tipoTramite, BigDecimal idUsuario, BigDecimal idContrato, String xmlEnviar) throws OegamExcepcion {
		try {
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
			colaVO.setXmlEnviar(xmlEnviar);
			if (idContrato != null) {
				colaVO.setIdContrato(idContrato);
			}
			colaVO.setCola(getHiloCola(colaVO));
			colaDao.guardar(colaVO);
			return colaVO.getIdEnvio();
		} catch (HibernateException e) {
			log.error("Error al encolar", e);
			throw new TransactionalException(e);
		}
	}

	private String getHiloCola(ColaVO colaVO) throws OegamExcepcion {
		String hilo = colaDao.getHilo(colaVO);
		if (hilo == null) {
			throw new OegamExcepcion("Debe de crear la cola para el proceso " + colaVO.getProceso() + " para poder encolar una nueva solicitud");
		}
		return hilo;
	}

	@Override
	public ResultadoBean crearSolicitud(String proceso, String xmlEnviar, String tipoTramite, BigDecimal idTramite,
			Long idUsuario, BigDecimal idContrato) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			String entornoProceso = gestorPropiedades.valorPropertie(ENTORNO);
			ProcesoVO procesoVO = null;
			if (DESA.equals(entornoProceso)) {
				procesoVO = servicioPersistenciaProcesos.getProcesoPorProcesoYNodo(proceso, gestorPropiedades.valorPropertie(HOST));
			} else {
				procesoVO = servicioPersistenciaProcesos.getProceso(proceso);
			}
			if (procesoVO != null) {
				Boolean existeColaProcesoTramite = Boolean.FALSE;
				if (idTramite != null) {
					existeColaProcesoTramite = servicioPersistenciaCola.existeColaTramiteProceso(idTramite, proceso);
				}
				if (!existeColaProcesoTramite) {
					ColaVO cola = rellenarCola(proceso, xmlEnviar, tipoTramite, idTramite, idUsuario, idContrato, procesoVO.getId().getNodo());
					servicioPersistenciaCola.guardarCola(cola);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El tramite que esta intentando encolar ya existe por lo que debera esperar a que se ejecute dicha solicitud.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del proceso para poder encolar la solicitud.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de crear la solicitud para el proceso: " + proceso + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de crear la solicitud para el proceso.");
		}
		return resultado;
	}

	private ColaVO rellenarCola(String proceso, String xmlEnviar, String tipoTramite, BigDecimal idTramite, Long idUsuario, BigDecimal idContrato, String nodo) {
		ColaVO colaVO = new ColaVO();
		colaVO.setProceso(proceso);
		colaVO.setNodo(nodo);
		colaVO.setFechaHora(new Date());
		colaVO.setEstado(BigDecimal.ONE);
		colaVO.setnIntento(BigDecimal.ZERO);
		colaVO.setTipoTramite(tipoTramite);
		if (idTramite != null) {
			colaVO.setIdTramite(idTramite);
		}
		if (idUsuario != null) {
			colaVO.setIdUsuario(new BigDecimal(idUsuario));
		}
		if (idContrato != null) {
			colaVO.setIdContrato(idContrato);
		}
		colaVO.setXmlEnviar(xmlEnviar);
		colaVO.setCola(servicioPersistenciaCola.getHilo(nodo, proceso));
		return colaVO;
	}
}