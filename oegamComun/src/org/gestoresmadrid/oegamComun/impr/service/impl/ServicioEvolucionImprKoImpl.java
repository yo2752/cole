package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.impr.model.dao.EvolucionImprKoDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionImprKoVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioEvolucionImprKo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEvolucionImprKoImpl implements ServicioEvolucionImprKo{

	private static final long serialVersionUID = -1879225614568797611L;
	
	@Autowired
	EvolucionImprKoDao evolucionImprKoDao;

	@Override
	@Transactional
	public void guardarEvolucion(EvolucionImprKoVO evolucion) {
		evolucion.setFecha(new Date());
		evolucionImprKoDao.guardar(evolucion);
	}

}
