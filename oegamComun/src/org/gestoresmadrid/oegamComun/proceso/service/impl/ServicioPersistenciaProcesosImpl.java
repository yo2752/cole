package org.gestoresmadrid.oegamComun.proceso.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.proceso.model.dao.ProcesoDao;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.oegamComun.proceso.service.ServicioPersistenciaProcesos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaProcesosImpl implements ServicioPersistenciaProcesos {

	private static final long serialVersionUID = 524135304434604156L;

	@Autowired
	private ProcesoDao procesoDao;

	@Override
	@Transactional(readOnly = true)
	public ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo) {
		return procesoDao.getProcesoPorProcesoYNodo(proceso, nodo);
	}

	@Override
	@Transactional(readOnly = true)
	public ProcesoVO getProceso(String proceso) {
		return procesoDao.getProcesoN(proceso);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProcesoVO> getListaProcesos() {
		return procesoDao.getListaProcesos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProcesoVO> getListaProcesosOrdenados(String nodo) {
		return procesoDao.getListaProcesosOrdenados(nodo);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getIntentosMaximos(String nombreProceso, String nodo) {
		return procesoDao.getIntentosMaximos(nombreProceso, nodo);
	}

	@Override
	@Transactional
	public void actualizarProceso(ProcesoVO procesoVO) {
		procesoDao.actualizar(procesoVO);
	}
}
