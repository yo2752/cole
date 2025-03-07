package org.gestoresmadrid.oegamComun.cola.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.cola.model.dao.ColaDao;
import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioPersistenciaCola;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaColaImpl implements ServicioPersistenciaCola {

	private static final long serialVersionUID = 1357269263132476220L;

	private static final Logger log = LoggerFactory.getLogger(ServicioPersistenciaColaImpl.class);

	@Autowired
	private ColaDao colaDao;

	@Override
	@Transactional(readOnly = true)
	public ColaVO getColaPorIdEnvio(Long idEnvio) {
		return colaDao.getCola(idEnvio);
	}

	@Override
	@Transactional(readOnly = true)
	public ColaVO getColaIdTramite(BigDecimal idTramite, String proceso) {
		return colaDao.getColaPorIdTramite(idTramite, proceso);
	}

	@Override
	@Transactional
	public void eliminarCola(ColaVO colaVO) {
		colaDao.borrar(colaVO);
	}

	@Override
	@Transactional
	public void actualizar(ColaVO colaVO) {
		colaDao.actualizar(colaVO);
	}

	@Override
	@Transactional
	public void guardarCola(ColaVO cola) {
		colaDao.guardar(cola);
	}

	@Override
	@Transactional
	public Serializable guardarColaReturnId(ColaVO cola) {
		return (Serializable) colaDao.guardar(cola);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existeColaTramiteProceso(BigDecimal idTramite, String proceso) {
		Boolean existeCola = Boolean.FALSE;
		try {
			List<ColaVO> listaColas = colaDao.existeColaTramiteProceso(idTramite, proceso);
			if (listaColas != null && !listaColas.isEmpty()) {
				existeCola = Boolean.TRUE;
			}
		} catch (Exception ex) {
			log.error("Error al comprobar si existe alguna cola para el proceso y el tramite: ", ex);
			existeCola = Boolean.FALSE;
		}
		return existeCola;
	}

	@Override
	@Transactional(readOnly = true)
	public String getHilo(String nodo, String proceso) {
		return colaDao.getHilo(nodo, proceso);
	}

	@Override
	@Transactional(readOnly = true)
	public ColaVO getColaSolicitudProceso(String proceso, String cola, String nodo) {
		return colaDao.getColaSolicitudProceso(proceso, cola, nodo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ColaVO> getColasActivasProceso(String proceso, String nodo) {
		return colaDao.getColasActivasProceso(proceso, nodo);
	}

	@Override
	@Transactional(readOnly = true)
	public ColaVO getColaPrincipal(String proceso, String cola, String nodo) {
		return colaDao.getColaPrincipal(proceso, cola, nodo);
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerMaxCola() {
		return colaDao.obtenerMaxCola();
	}

	@Override
	public Long getNumColasProceso(String proceso, String nodo, Long idContrato) {
		// TODO Auto-generated method stub
		return null;
	}
}
