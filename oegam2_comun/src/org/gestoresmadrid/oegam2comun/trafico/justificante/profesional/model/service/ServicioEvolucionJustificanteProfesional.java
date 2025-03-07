package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;

public interface ServicioEvolucionJustificanteProfesional extends Serializable {

	void guardar(EvolucionJustifProfesionalesVO evolucion);
}
