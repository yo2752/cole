package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.impr.model.dao.EvolucionImprDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionImprVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioEvolucionGestionarImpr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEvolucionGestionarImprImpl implements ServicioEvolucionGestionarImpr{

	private static final long serialVersionUID = 8997684042213756389L;

	@Autowired
	EvolucionImprDao evolucionImprDao;

	@Override
	@Transactional
	public void guardarEvolucion(EvolucionImprVO evolucion) {
		evolucion.setFecha(new Date());
		evolucionImprDao.guardar(evolucion);
	}

}