package org.gestoresmadrid.oegamComun.trafico.service.impl;

import org.gestoresmadrid.core.trafico.model.dao.EvolucionTramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaEvoTramiteTrafico;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaEvoTramiteTraficoImpl implements ServicioPersistenciaEvoTramiteTrafico{

	private static final long serialVersionUID = 5286430523120521274L;

	@Autowired
	EvolucionTramiteTraficoDao evolucionTramiteTraficoDao;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	@Override
	@Transactional
	public void guardar(EvolucionTramiteTraficoVO evolucionTramiteTraficoVO) {
		evolucionTramiteTraficoVO.getId().setFechaCambio(utilesFecha.getFechaActualDesfaseBBDD());
		evolucionTramiteTraficoDao.guardar(evolucionTramiteTraficoVO);
	}
}
