package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import hibernate.entities.trafico.JustificanteProf;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;

public interface ServicioEvolucionJustifProInt  extends Serializable{

	public EvolucionJustifProfesionalesVO convertirBeantoEntity(JustificanteProf justificanteProfesional, BigDecimal estado);

	public void guardarEvolucion(EvolucionJustifProfesionalesVO evolucionJustificanteProfVO);

}
