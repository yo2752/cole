package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.impl;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.EvolucionJustifProfesionalesDao;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioEvolucionJustificanteProfesional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEvolucionJustificanteProfesionalImpl implements ServicioEvolucionJustificanteProfesional {

	private static final long serialVersionUID = -7524437915817365774L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEvolucionJustificanteProfesionalImpl.class);

	@Autowired
	private EvolucionJustifProfesionalesDao evolucionJustificanteProfDao;

	@Override
	public void guardar(EvolucionJustifProfesionalesVO evolucion) {
		try {
			evolucionJustificanteProfDao.guardarOActualizar(evolucion);
		} catch (Exception e) {
			log.error("Error al guardar la evolución del justificante profesional: " + evolucion.getId().getNumExpediente(), e);
		}
	}

	public EvolucionJustifProfesionalesDao getEvolucionJustificanteProfDao() {
		return evolucionJustificanteProfDao;
	}

	public void setEvolucionJustificanteProfDao(EvolucionJustifProfesionalesDao evolucionJustificanteProfDao) {
		this.evolucionJustificanteProfDao = evolucionJustificanteProfDao;
	}
}