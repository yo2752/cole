package org.gestoresmadrid.oegamComun.proceso.service.impl;

import java.util.List;

import org.gestoresmadrid.core.proceso.model.dao.ProcesoDao;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoPK;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.oegamComun.proceso.service.ServicioComunProcesos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunProcesosImpl implements ServicioComunProcesos {

	private static final long serialVersionUID = -1738243596296214719L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunProcesosImpl.class);

	@Autowired
	ProcesoDao procesoDao;

	@Override
	@Transactional(readOnly = true)
	public ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo) {
		ProcesoVO procesoVO = new ProcesoVO();
		ProcesoPK id = new ProcesoPK();
		id.setProceso(proceso);
		if (nodo != null && !nodo.isEmpty()) {
			id.setNodo(nodo);
		}

		procesoVO.setId(id);
		List<ProcesoVO> listaProceso = procesoDao.buscar(procesoVO);
		if (listaProceso != null && !listaProceso.isEmpty()) {
			return listaProceso.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ProcesoVO getProceso(String proceso) {
		try {
			if (proceso != null && !proceso.isEmpty()) {
				return procesoDao.getProcesoVO(proceso);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el proceso, error: ", e);
		}
		return null;
	}
}
